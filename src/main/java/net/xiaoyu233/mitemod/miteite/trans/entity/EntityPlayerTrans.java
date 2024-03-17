package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.api.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.network.SPacketCraftingBoost;
import net.xiaoyu233.mitemod.miteite.network.SPacketOverlayMessage;
import net.xiaoyu233.mitemod.miteite.trans.util.EntityDamageResultAccessor;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerTrans extends EntityLivingBase implements ICommandSender, ITEPlayer, ITELivingEntity {

   @Shadow public abstract ItemStack getCurrentArmor(int par1);

   @Unique
   private final Map<Entity, Integer> attackCountMap = new HashMap<>();
   @Shadow public InventoryPlayer inventory;
   @Shadow
   public float vision_dimming;
   @Unique
   private float craftingBoostFactor;
   @Unique
   private int craftingBoostTimer;
   @Unique
   private BlockPos currentEffectedBeaconPos;
   @Unique
   private int inRainCounter;
   @Unique
   private int netherDebuffTime;
   @Unique
   private int underworldDebuffTime;
   @Unique
   private int underworldRandomTeleportTime;
   @Unique
   private volatile boolean waitForItemSync;
   @Unique
   private int defenseCooldown;

   public EntityPlayerTrans(World par1World, String par2Str) {
      super(par1World);
   }

   @Override
   public boolean canBeDisarmed() {
      return true;
   }

   @Override
   public boolean[] getPickedUpAHeldItemArray() {
      return new boolean[4];
   }

   @Override
   public float getWeaponDamageBoost() {
      return 1.0f;
   }

   @Unique
   private void activeEmergency(List<ItemStack> emergencyItemList) {
      this.addPotionEffect(new PotionEffect(11, 60, 1));
      this.setHealth(Configs.Item.Enchantment.EMERGENCY_HEALTH_RECOVER_AMOUNT.get(), true, this.getHealFX());
      this.entityFX(EnumEntityFX.smoke_and_steam);
      this.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
      this.makeSound("random.anvil_land", 0.4F, 0.4F);
      double reduce = (1 - (emergencyItemList.size() - 1) * Configs.Item.Enchantment.EMERGENCY_COOLDOWN_REDUCE_EVERY_ARMOR.get());
      for (ItemStack itemStack : emergencyItemList) {
         itemStack.setEmergencyCooldown((int) (Configs.Item.Enchantment.EMERGENCY_COOLDOWN.get() * reduce));
      }
   }

   @Unique
   protected void tryDisarmTarget(Entity target){
      if (this.onServer() && target instanceof EntityLivingBase) {
         EntityLivingBase entity_living_base = (EntityLivingBase)target;
         ItemStack item_stack_to_drop = entity_living_base.getHeldItemStack();
         if (item_stack_to_drop != null && this.rand.nextFloat() < this.getDisarmingChance(this.getHeldItemStack())) {
            if (entity_living_base instanceof EntityLiving){
               EntityLiving entity_living = (EntityLiving)entity_living_base;
               if (((ITELivingEntity) entity_living).canBeDisarmed()) {
                  EntityItem entityItem = entity_living.dropItemStack(item_stack_to_drop, entity_living.height / 2.0F);
                  //Only for natural generated weapons
                  if (!((ITELivingEntity) entity_living).getPickedUpAHeldItemArray()[0]) {
                     ((ITEItemEntity) entityItem).setCanBePickUpByPlayer(false);
                     //Only exist for half a minute
                     entityItem.age = 5400;
                  }
                  entity_living.clearMatchingEquipmentSlot(item_stack_to_drop);
                  entity_living.ticks_disarmed = 40;
               }
            }else if (entity_living_base instanceof EntityPlayer){
               EntityPlayer player = (EntityPlayer) entity_living_base;
               if (!player.isBlocking() && Configs.GameMechanics.PLAYER_DISARM_PLAYER.get()){
                  EntityItem entityItem = player.dropItemStack(item_stack_to_drop, player.height / 2.0F);
                  Vec3 lookVec = player.getLookVec();
                  entityItem.delayBeforeCanPickup = 20;
                  //Rotate 90 degrees,to the right side of the player
                  entityItem.addVelocity(-lookVec.zCoord * 0.4,0,lookVec.xCoord  * 0.4);
                  player.setHeldItemStack(null);
               }
            }
         }
      }
   }

   @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;willDeliverCriticalStrike()Z"))
   private boolean redirectCheckCriticalStrike(EntityPlayer instance){
      ItemStack heldItemStack = this.getHeldItemStack();
      if (EnchantmentHelper.hasEnchantment(heldItemStack, Enchantments.CRIT)) {
         int critLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.CRIT, heldItemStack);
         ITE$willCritOnLastAttack = instance.willDeliverCriticalStrike() || this.rand.nextInt(10) < (Configs.Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL.get()) * critLevel;
      }else{
         ITE$willCritOnLastAttack = instance.willDeliverCriticalStrike();
      }
      return ITE$willCritOnLastAttack;
   }

   /**
    * Temp local var conversion field
    * */
   @Unique
   private boolean ITE$willCritOnLastAttack = false;

   @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;calcRawMeleeDamageVs(Lnet/minecraft/Entity;ZZ)F"))
   private float modifyApplyITEBonusDamage(EntityPlayer instance, Entity target, boolean critical, boolean suspended_in_liquid){
      float damage = this.calcRawMeleeDamageVs(target, critical, this.isSuspendedInLiquid());
      float critBouns = 0.0F;
      ItemStack heldItemStack = this.getHeldItemStack();
      //Check for crit enchantment
      if (ITE$willCritOnLastAttack && EnchantmentHelper.hasEnchantment(heldItemStack, Enchantments.CRIT)) {
         int critLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.CRIT, heldItemStack);
         critBouns = (float)critLevel * (Configs.Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL.get()).floatValue();
      }

      //Check for indomitable modifier
      float indomitableAmp = 1;
      float healthPercent = this.getHealth() / this.getMaxHealth();
      if (healthPercent <= 0.5f){
         ItemStack chestplate = this.getCurrentArmor(1);
         if (chestplate != null){
            float value = ArmorModifierTypes.INDOMITABLE.getModifierValue(chestplate.getTagCompound());
            if (value != 0){
               indomitableAmp = this.getIndomitableAmp(healthPercent);
            }
         }
      }

      float demonHunterAmp = 1;
      if (!target.getWorld().isOverworld() && heldItemStack != null){
         demonHunterAmp += ToolModifierTypes.DEMON_POWER.getModifierValue(heldItemStack.getTagCompound());
      }
      return (critBouns + damage) * indomitableAmp * demonHunterAmp;
   }

   @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;onServer()Z", ordinal = 0))
   private boolean redirectCancelOldDisarming(EntityPlayer instance){
      return false;
   }

   @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;onServer()Z", ordinal = 0, shift = At.Shift.BEFORE))
   private void injectNewDisarming(Entity target, CallbackInfo ci){
      this.tryDisarmTarget(target);
   }

   @Unique
   private float getIndomitableAmp(float healthPercent){
      if (healthPercent <= 0.1f){
         return 2.0f;
      }else if (healthPercent <= 0.2f) {
         return 1.6f;
      }else if (healthPercent <= 0.35f){
         return 1.35f;
      }else if (healthPercent <= 0.5f){
         return 1.25f;
      }
      return 1.0f;
   }

   @Override
   public void addPotionEffect(PotionEffect par1PotionEffect) {
      ItemStack helmet = this.getHelmet();
      if (helmet != null &&
              //Bad only
              Potion.get(par1PotionEffect.getPotionID()).isBadEffect()
      ){
         par1PotionEffect.setDuration((int) (par1PotionEffect.getDuration() * (1 - ArmorModifierTypes.IMMUNITY.getModifierValue(helmet.getTagCompound()))));
      }
      super.addPotionEffect(par1PotionEffect);
   }

   @ModifyConstant(method = "getHealthLimit(I)I", constant = @Constant(intValue = 20))
   private static int modifyMaxHealthByLevel(int constant){
      return 40;
   }

   @Unique
   public boolean canDefense(){
      return this.defenseCooldown <= 0;
   }

   @Unique
   @SoftOverride //ITELivingBase
   public void checkForAfterDamage(Damage damage, EntityDamageResult result) {
      if (result.entityWasDestroyed()) {
         ItemStack[] var3 = this.getWornItems();
         List<ItemStack> readyEmergencyItemList = new ArrayList<>();
         for (ItemStack wornItem : var3) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               if (wornItem.getEmergencyCooldown() <= 0){
                  readyEmergencyItemList.add(wornItem);
               }
            }
         }
         if (!readyEmergencyItemList.isEmpty()){
            ((EntityDamageResultAccessor) result).setEntity_was_destroyed(false);
            this.activeEmergency(readyEmergencyItemList);
         }
      }

   }

   @Shadow
   public abstract float calcRawMeleeDamageVs(Entity var1, boolean var2, boolean var3);

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   public void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci) {
      this.underworldRandomTeleportTime = par1NBTTagCompound.getInteger("UnderWorldTeleportTime");
      this.underworldDebuffTime = par1NBTTagCompound.getInteger("UnderWorldDebuffTime");
      this.netherDebuffTime = par1NBTTagCompound.getInteger("NetherDebuffTime");
      this.inRainCounter = par1NBTTagCompound.getInteger("InRainCounter");
      this.vision_dimming = par1NBTTagCompound.getFloat("vision_dimming");
      if (par1NBTTagCompound.hasKey("AttackCountMap")) {
         NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackCountMap");
         int count = attackCountMap.tagCount();

         for(int i = 0; i < count; ++i) {
            NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
            Entity attacker = this.getWorldServer().getEntityByID(a.getInteger("Attacker"));
            if (attacker != null) {
               this.attackCountMap.put(attacker, a.getInteger("Count"));
            }
         }
      }
      if (par1NBTTagCompound.hasKey("DefenseCooldown")){
         this.setDefenseCooldown(par1NBTTagCompound.getInteger("DefenseCooldown"));
      }

   }

   @Inject(method = "checkForArmorAchievements", at = @At("RETURN"))
   private void checkForArmorAchievements(CallbackInfo ci) {
      boolean wearing_full_suit_vibranium_plate = true;
      for(int i = 0; i < 4; ++i) {
         if (this.inventory.armorInventory[i] != null && this.inventory.armorInventory[i].getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)this.inventory.armorInventory[i].getItem();
            Material material = armor.getArmorMaterial();
            if (material != Materials.vibranium) {
               wearing_full_suit_vibranium_plate = false;
               break;
            }
         } else {
            wearing_full_suit_vibranium_plate = false;
            break;
         }
      }
      if (wearing_full_suit_vibranium_plate) {
         this.triggerAchievement(Achievements.wearAllVibraniumPlateArmor);
      }
   }

   @Override
   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
   }

   @Override
   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
   }


   @Override
   public float getCraftingBoostFactor() {
      return this.craftingBoostFactor;
   }

   @Shadow
   public ItemStack getHeldItemStack() {
      return null;
   }

   @Shadow
   public void setHeldItemStack(ItemStack itemStack) {
   }

   @Redirect(method = "getReach(Lnet/minecraft/Block;I)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getReachBonus(Lnet/minecraft/Block;I)F"))
   public final float injectReachEnchantment(Item instance, Block block, int metadata) {
      int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EXTEND, this.getHeldItemStack());
      return instance.getReachBonus(block, metadata) + (float)enchantmentLevel * 0.25F;
   }

   @Redirect(method = "getReach(Lnet/minecraft/EnumEntityReachContext;Lnet/minecraft/Entity;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getReachBonus()F"))
   private float injectReachEnchantment(Item instance){
      return (float) (instance.getReachBonus() + EnchantmentHelper.getEnchantmentLevel(Enchantments.EXTEND, this.getHeldItemStack()) * 0.25);
   }

   @Redirect(method = "getReach(Lnet/minecraft/EnumEntityReachContext;Lnet/minecraft/Entity;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getReachBonus(Lnet/minecraft/Entity;)F"))
   private float injectReachEnchantment(Item instance, Entity entity){
      return (float) (instance.getReachBonus(entity) + EnchantmentHelper.getEnchantmentLevel(Enchantments.EXTEND, this.getHeldItemStack()) * 0.25);
   }

   @Shadow
   public ItemStack[] getWornItems() {
      return new ItemStack[0];
   }

   @Unique
   private void randomRuneTeleport(WorldServer world, int x, int y, int z, ServerPlayer player) {
      BlockPortal portal = Block.portal;
      ITEPortal itePortal = (ITEPortal) portal;
      itePortal.setPortalSeedOverride(this.rand.nextInt());
      itePortal.setRuneTypeOverride(Block.runestoneMithril);
      int[] runegate_destination_coords = portal.getRunegateDestinationCoords(world, x, y, z);
      player.runegate_destination_coords = runegate_destination_coords;
      player.playerNetServerHandler.sendPacketToPlayer(new Packet85SimpleSignal(EnumSignal.runegate_start));
      player.setPositionAndUpdate(runegate_destination_coords[0], runegate_destination_coords[1], runegate_destination_coords[2]);
   }

   @Redirect(
           method = {"getCurrentPlayerStrVsBlock(IIIZ)F"},
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/EnchantmentHelper;getAquaAffinityModifier(Lnet/minecraft/EntityLivingBase;)Z"
           )
   )
   private boolean redirectCheckAquaEnchantment(EntityLivingBase player){
      boolean aquaAffinityModifier = EnchantmentHelper.getAquaAffinityModifier(this);
      boolean waterLike = this.getHeldItemStack() != null && ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierLevel(this.getHeldItemStack().getTagCompound()) != 0;
      return aquaAffinityModifier || waterLike;
   }

   @Redirect(
           method = {"attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"},
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/EntityLivingBase;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"
           )
   )
   private EntityDamageResult redirectEntityAttack(EntityLivingBase caller,Damage damage){
      double progress = Math.min(Configs.GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_MAX.get(),(Configs.GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_BASE.get()) + this.getWorld()
              .getDayOfOverworld() / (float)Configs.GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_INCREASE_DAY.get());
      if (progress != 0.0D) {
         Entity responsibleEntity = damage.getSource().getResponsibleEntity();
         if (responsibleEntity != null && !(responsibleEntity instanceof EntityDragon || responsibleEntity instanceof EntityCubic)) {
            if (this.attackCountMap.containsKey(responsibleEntity)) {
               damage.setAmount(damage.getAmount() + (this.attackCountMap.get(responsibleEntity)));
               this.attackCountMap.put(responsibleEntity, this.attackCountMap.get(responsibleEntity) + 1);
            } else {
               this.attackCountMap.put(responsibleEntity, 1);
            }
         }
      }
      if (damage.getResponsibleEntityP() != null && this.getHeldItem() != null && this.rand.nextInt(10) > 8) {
            this.tryDisarmTarget(damage.getResponsibleEntityP());
      }
      EntityDamageResult entityDamageResult = super.attackEntityFrom(damage);
      if (entityDamageResult != null && (double)this.getHealthFraction() <= 0.3D && !entityDamageResult.entityWasDestroyed()) {
         ItemStack[] var5 = this.getWornItems();

         List<ItemStack> readyEmergencyItems = new ArrayList<>();
         for (ItemStack wornItem : var5) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               if (wornItem.getEmergencyCooldown() <= 0){
                  readyEmergencyItems.add(wornItem);
               }
            }
         }
         if (!readyEmergencyItems.isEmpty()){
            this.activeEmergency(readyEmergencyItems);
         }
      }
      return entityDamageResult;
   }

   @Inject(
           method = {"onLivingUpdate()V"},
           at = {@At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/EntityLivingBase;onLivingUpdate()V",
                   shift = At.Shift.AFTER
           )}
   )
   private void injectTick(CallbackInfo c){
      if (!this.worldObj.isRemote) {
         if (this.craftingBoostTimer > 0) {
            --this.craftingBoostTimer;
         } else if (this.craftingBoostTimer == 0) {
            this.craftingBoostFactor = 0.0F;
            this.sendPacket(new SPacketCraftingBoost((float)this.craftingBoostTimer));
            this.craftingBoostTimer = -1;
         }
         if (this.isInRain()) {
            if (this.inRainCounter < (Configs.GameMechanics.IN_RAIN_DEBUFF_TIME.get())) {
               ++this.inRainCounter;
            } else {
               this.addPotionEffect(new PotionEffect(18, 3600, 0));
            }
         } else if (this.inRainCounter > 0) {
            --this.inRainCounter;
         }

         int debuff_time;
         if (!this.worldObj.isUnderworld()) {
            if (this.worldObj.isTheNether()) {
               if ((Configs.GameMechanics.Nether.NETHER_DEBUFF.get())) {
                  debuff_time = (Configs.GameMechanics.Nether.NETHER_DEBUFF_TIME.get());
                  if (this.netherDebuffTime > debuff_time) {
                     this.addPotionEffect(new PotionEffect(4, 3600, 1));
                  } else if (this.netherDebuffTime == debuff_time) {
                     this.sendPacket(new SPacketOverlayMessage("§n---你在下界中感到疲惫---", EnumChatFormatting.DARK_RED.rgb, 200));
                  }
               }
            }  else if (this.netherDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地狱的疲惫中恢复---", EnumChatFormatting.DARK_GREEN.rgb, 200));
            }
            if (this.netherDebuffTime > 0) {
               --this.netherDebuffTime;
            }

            if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }

            if (this.underworldDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地底世界的疲惫中恢复---", EnumChatFormatting.DARK_GREEN.rgb, 200));
            }
            if (this.underworldDebuffTime > 0) {
               --this.underworldDebuffTime;
            }
         }
         else {
            if (Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF.get()) {
               ++this.underworldDebuffTime;
               debuff_time = Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD1.get();
               int period2 = Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD2.get();
               if (this.underworldDebuffTime > debuff_time && this.underworldDebuffTime < period2) {
                  if (this.underworldDebuffTime == debuff_time + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到有些疲惫---", EnumChatFormatting.GRAY.rgb, 400));
                  }

                  this.addPotionEffect(new PotionEffect(2, 1200, 0));
               } else if (this.underworldDebuffTime > period2) {
                  if (this.underworldDebuffTime == period2 + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到更加疲惫---", EnumChatFormatting.YELLOW.rgb, 400));
                  }

                  this.addPotionEffect(new PotionEffect(2, 2400, 1));
               }
            }

            if (Configs.GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT.get()) {
               double randomTeleportTime = Configs.GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT_TIME.get();
               ++this.underworldRandomTeleportTime;
               double timeToTeleport = randomTeleportTime - (double)this.underworldRandomTeleportTime;
               if (timeToTeleport == 1200.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于一分钟后被随机传送,请做好准备!!!---", EnumChatFormatting.YELLOW.rgb, 400));
               }

               if (timeToTeleport == 6000.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于五分钟后被随机传送,请做好准备!!!---", EnumChatFormatting.DARK_AQUA.rgb, 400));
               }

               if (timeToTeleport <= 200.0D && this.underworldRandomTeleportTime % 20 == 0) {
                  this.sendPacket(new SPacketOverlayMessage("§l§n!!!你将于" + (int)timeToTeleport / 20 + "秒后被随机传送!!!", EnumChatFormatting.RED.rgb, 200));
               }

               if (this.underworldRandomTeleportTime > randomTeleportTime) {
                  if (ReflectHelper.dyCast(EntityPlayer.class, this) instanceof EntityPlayer) {
                     this.randomRuneTeleport(this.worldObj.getAsWorldServer(), this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ(), ReflectHelper.dyCast(this));
                  }

                  this.underworldRandomTeleportTime = 0;
               }
            } else if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }
         }

         //To avoid slot locking due to emergency cooldown
         if (ticksExisted % 2 == 0){
            for (ItemStack wornItem : this.getWornItems()) {
               if (wornItem != null){
                  int emergencyCooldown = wornItem.getEmergencyCooldown();
                  if (emergencyCooldown > 0){
                     wornItem.setEmergencyCooldown(emergencyCooldown - 2);
                  }
               }
            }
         }

         if (this.defenseCooldown > 0){
            this.defenseCooldown--;
         }else {
            this.setDefenseCooldown(0);
         }
      }
   }

   @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
   public void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callback) {
      par1NBTTagCompound.setInteger("UnderWorldTeleportTime", this.underworldRandomTeleportTime);
      par1NBTTagCompound.setInteger("UnderWorldDebuffTime", this.underworldDebuffTime);
      par1NBTTagCompound.setInteger("NetherDebuffTime", this.netherDebuffTime);
      par1NBTTagCompound.setInteger("InRainCounter", this.inRainCounter);
      par1NBTTagCompound.setFloat("vision_dimming", this.vision_dimming);
      NBTTagList nbtTagList = new NBTTagList();
      for (Entry<Entity, Integer> integerEntry : this.attackCountMap.entrySet()) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("Attacker", ( integerEntry).getKey().entityId);
         compound.setInteger("Count", (integerEntry).getValue());
         nbtTagList.appendTag(compound);
      }

      par1NBTTagCompound.setTag("AttackCountMap", nbtTagList);
      par1NBTTagCompound.setInteger("DefenseCooldown",this.defenseCooldown);
   }

   @Redirect(method = "getCurrentPlayerStrVsBlock",at = @At(value = "INVOKE",target = "Lnet/minecraft/Item;getStrVsBlock(Lnet/minecraft/Block;I)F"))
   private float redirectGetStrVsBlock(Item caller,Block block,int metadata){
      ItemStack heldItemStack = this.getHeldItemStack();
      return heldItemStack.getItem().getStrVsBlock(block, metadata, heldItemStack, net.xiaoyu233.fml.util.ReflectHelper.dyCast(this));
   }

   @Unique
   @Override
   public void itemsSynced() {
      this.waitForItemSync = false;
   }

   @Override
   public boolean knockBack(Entity attacker, float amount) {
      ItemStack[] itemStacks = this.getWornItems();
      int i = 0;

      for(int itemStacksLength = itemStacks.length; i < itemStacksLength; ++i) {
         ItemStack stack = itemStacks[i];
         if (stack != null) {
            amount *= Math.max(1.0F - ArmorModifierTypes.STEADY_MODIFIER.getModifierValue(stack.stackTagCompound), 0.0F);
         }
      }

      return super.knockBack(attacker, amount);
   }

   @Override
   @Unique
   public void setDefenseCooldown(int time){
      this.defenseCooldown = time;
   }

   @Shadow
   public void sendPacket(Packet packet) {
   }

   @Override
   @Unique
   public void setCraftingBoostFactor(float craftingBoostFactor, @Nullable BlockPos currentEffectedBeaconPos) {
      if (!this.worldObj.isRemote) {
         float result = 0.0F;
         if (currentEffectedBeaconPos != null) {
            if (currentEffectedBeaconPos.equals(this.currentEffectedBeaconPos)) {
               result = craftingBoostFactor;
            } else {
               result = Math.max(craftingBoostFactor, this.craftingBoostFactor);
               if (result != this.craftingBoostFactor) {
                  this.currentEffectedBeaconPos = currentEffectedBeaconPos;
               }
            }
         }

         if (result != this.craftingBoostFactor) {
            this.craftingBoostFactor = result;
            this.sendPacket(new SPacketCraftingBoost(this.craftingBoostFactor));
         }
      } else {
         this.craftingBoostFactor = craftingBoostFactor;
      }

   }

   @Unique
   @Override
   public void setCraftingBoostTimer(int craftingBoostTimer) {
      this.craftingBoostTimer = craftingBoostTimer;
   }


//   public void syncItemsAndWait() {
//      this.sendPacket(new CPacketSyncItems());
//      this.waitForItemSync = true;
//
//      while(this.waitForItemSync) {
//      }
//
//   }

   @Shadow
   public abstract void triggerAchievement(StatBase par1StatBase);
}
