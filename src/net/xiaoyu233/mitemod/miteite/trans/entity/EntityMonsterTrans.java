package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MathUtil;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(EntityMonster.class)
public abstract class EntityMonsterTrans extends EntityInsentient implements IMonster {
   public boolean[] picked_up_a_held_item_array;
   private boolean attackCauseFire;
   private int fireParticleTick;
   public EntityMonsterTrans(World par1World) {
      super(par1World);
   }

   @Redirect(method = {"attackEntityAsMob(Lnet/minecraft/Entity;)Lnet/minecraft/EntityDamageResult;"},
            at = @At(value = "INVOKE",target = "Lnet/minecraft/AttributeInstance;getAttributeValue()D"))
   private double redirectEntityDamageGet(AttributeInstance caller){
      if (this.getHeldItem() instanceof ItemTool){
         return caller.getAttributeValue() * this.getWeaponDamageBoost();
      }
      return caller.getAttributeValue();
   }

   @Redirect(method = {"attackEntityAsMob(Lnet/minecraft/EntityInsentient;Lnet/minecraft/Entity;)Lnet/minecraft/EntityDamageResult;"},
           at = @At(value = "INVOKE",target = "Lnet/minecraft/AttributeInstance;getAttributeValue()D"))
   private static double redirectEntityDamageGetStatic(AttributeInstance caller,EntityInsentient attacker, Entity target){
      if (attacker != null && attacker.getHeldItem() instanceof ItemTool){
         return caller.getAttributeValue() * attacker.getWeaponDamageBoost();
      }
      return caller.getAttributeValue();
   }

   @Inject(method = "attackEntityFrom",at = @At("HEAD"))
   public void attackEntityFrom(Damage damage, CallbackInfoReturnable<EntityDamageResult> c) {
      if ((Configs.Entities.MOB_DEFENSE.get())
              && damage.getResponsibleEntityP() != null
              && this.getHeldItem() != null && this.rand.nextInt(10) > 8) {
         damage.scaleAmount(0.5F);
         if (Configs.Entities.MOB_DISARM_WHEN_DEFENCE.get()){
            this.tryDisarmTarget(damage.getResponsibleEntityP());
         }
         this.getWorld().playSoundAtEntity(this, "mob.irongolem.hit", 1.0F, 1.0F);
      }
   }

   @Override
   public void setTarget(EntityLiving target) {
      if (target == null || target.canBeTargetTo(this)) {
         super.setTarget(target);
      }
   }

   @Override
   public void setAttackTarget(EntityLiving par1EntityLivingBase) {
      if (par1EntityLivingBase == null || par1EntityLivingBase.canBeTargetTo(this)) {
         super.setAttackTarget(par1EntityLivingBase);
      }
   }

   @Override
   public void setRevengeTarget(EntityLiving par1EntityLivingBase) {
      if (par1EntityLivingBase == null || par1EntityLivingBase.canBeTargetTo(this)) {
         super.setRevengeTarget(par1EntityLivingBase);
      }
   }

   protected void dropEquipment(boolean recently_hit_by_player, int par2) {
      for(int var3 = 0; var3 < this.getInventory().length; ++var3) {
         ItemStack var4 = this.getEquipmentInSlot(var3);
         if (var4 != null && (!var4.isItemStackDamageable() || this.picked_up_a_held_item_array[var3] && var4.getRemainingDurability() > var4.getMaxDamage() / 4)) {
            this.dropItemStack(var4, 0.0F);
            this.getInventory()[var3]=null;
         }
      }

   }

   protected void addRandomArmor() {
      int hour = this.getWorld().getHourOfDay();
      int day = this.getWorld().getDayOfOverworld();
      Random rand;
      if (day > 32 && ((day % 2 == 0 || day > 64) && hour >= 18 || ((day - 1) % 2 == 0 || day > 64) && hour <= 6)) {
         this.addPotionEffect(new MobEffect(MobEffectList.moveSpeed.id, 999999, Math.min(this.getRNG().nextInt(Math.max((day - 32) / 96, 1)),4), true));
         rand = this.getRNG();
         if (rand.nextInt(5) == 0) {
            this.addPotionEffect(new MobEffect(MobEffectList.damageBoost.id, 999999, Math.min(this.getRNG().nextInt(Math.max((day - 32) / 128, 1)),3), true));
         }

         MonsterUtil.addDefaultArmor(day, this, false);
      } else if (day > 128) {
         rand = this.getRNG();
         if (rand.nextInt(4) < (day - 96) / 32) {
            this.addPotionEffect(new MobEffect(MobEffectList.moveSpeed.id, 999999, Math.min(this.getRNG().nextInt(Math.max((day - 32) / 96, 1)),4), true));
         }

         if (rand.nextInt(5) < (day - 96) / 32) {
            this.addPotionEffect(new MobEffect(MobEffectList.damageBoost.id, 999999, Math.min(this.getRNG().nextInt(Math.max((day - 32) / 128, 1)),3), true));
         }

         MonsterUtil.addDefaultArmor(day, this, false);
      }

   }

   protected void enchantEquipment(ItemStack item_stack) {
      int dayOfWorld = this.getWorld().getDayOfOverworld();
      if ((double) this.getRNG().nextFloat() <= 0.1f + (double) dayOfWorld / 64.0f / 10.0f) {
         MonsterUtil.addRandomEnchantment(this.getRNG(),
                 item_stack,
                 (int) (5.0F + (
                         this.worldObj.getDayOfOverworld() * 0.15f) +  (5 - this.rand.nextInt(10)) * this.rand.nextFloat()),
                 Math.min(2 + dayOfWorld/24,15),
                 Math.min(1 + dayOfWorld/72,4));
      }

   }

   protected void generateRandomParticles(EnumParticle particle) {
      for(int var2 = 0; var2 < 5; ++var2) {
         double var3 = this.rand.nextGaussian() * 0.02D;
         double var5 = this.rand.nextGaussian() * 0.02D;
         double var7 = this.rand.nextGaussian() * 0.02D;
         this.worldObj.spawnParticle(particle, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
      }

   }

   protected float getChanceOfCausingFire(){
      return 0;
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.picked_up_a_held_item_array = super.picked_up_a_held_item_array;

      try {
         this.setEntityAttribute(GenericAttributes.maxHealth, this.getEntityAttributeValue(GenericAttributes.maxHealth) * 2.0D);
         this.setEntityAttribute(GenericAttributes.attackDamage, this.getEntityAttributeValue(GenericAttributes.attackDamage) * 2.0D);
      } catch (NullPointerException var2) {
         this.setEntityAttribute(GenericAttributes.maxHealth, GenericAttributes.maxHealth.getDefaultValue() * 2.0D);
         this.setEntityAttribute(GenericAttributes.attackDamage, GenericAttributes.attackDamage.getDefaultValue() * 2.0D);
      }

      this.setEntityAttribute(GenericAttributes.followRange, 32.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage);
   }

   protected float getDisarmingChance(ItemStack itemStack){
      return EnchantmentManager.getEnchantmentLevelFraction(Enchantment.disarming, itemStack) * 0.4f;
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "attackEntityAsMob(Lnet/minecraft/Entity;)Lnet/minecraft/EntityDamageResult;",
           at = @At(value = "INVOKE",shift = At.Shift.BEFORE,
                   target = "Lnet/minecraft/EntityMonster;isFrenzied()Z"))
   private void injectCirtAttack(Entity target, CallbackInfoReturnable<EntityDamageResult> cir, ItemStack held_item, Damage damage){
      float critBouns = 0.0F;
      boolean critical = false;
      if (EnchantmentManager.hasEnchantment(this.getHeldItemStack(), Enchantments.CRIT)) {
         int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, this.getHeldItemStack());
         critical = this.rand.nextInt(10) < (Configs.Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL.get()) * critLevel;
         critBouns = (float)critLevel * (Configs.Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL.get())
                 .floatValue();
      }
      if (critical) {
         damage.scaleAmount(1.5F);
         damage.addAmount(critBouns);
      }
      if (this.attackCauseFire && target instanceof EntityPlayer && this.getHeldItem() instanceof ItemTool){
         target.setFire(8);
      }
   }

   @Inject(method = "attackEntityAsMob(Lnet/minecraft/Entity;)Lnet/minecraft/EntityDamageResult;",
          at = @At(value = "INVOKE",
                  target = "Lnet/minecraft/EnchantmentManager;getFireAspectModifier(Lnet/minecraft/EntityLiving;)I",
                  shift = At.Shift.BEFORE))
   private void injectDisarming(Entity target, CallbackInfoReturnable<EntityDamageResult> callback){
      if (Configs.Entities.MOB_DISARM_WHEN_ATTACK.get()) {
         this.tryDisarmTarget(target);
      }
   }

   @Shadow
   public boolean canSpawnInShallowWater() {
      return false;
   }

   public void dropContainedItems() {
   }

   @Inject(method = "onUpdate",at = @At("RETURN"))
   private void injectUpdate(CallbackInfo callback) {
      if (attackCauseFire){
         if (fireParticleTick == 0){
            this.generateRandomParticles(EnumParticle.flame);
            fireParticleTick = 20;
         }
      }
   }

   @Override
   public boolean isTargetWithinStrikingDistance(EntityLiving target) {
      if (!this.isAIEnabled()) {
         Minecraft.setErrorMessage("isTargetWithinStrikingDistance: doesn't handle old AI mobs yet");
         return false;
      } else if ((Object)(this) instanceof EntityAnimal) {
         double var2 = this.width * 1.75F * this.width * 1.75F + target.width;
         if (this.getHeldItemStack() != null) {
            var2 += this.getHeldItemStack().getItem().getReachBonus();
         }

         return this.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ) <= var2;
      } else {
         return this.getDistance(target.posX, this.posY, target.posZ) <= (double)this.getReach() && MathUtil.isInRange(target.boundingBox.minY,this.boundingBox.minY - 0.5f,this.boundingBox.maxY + 0.5f);
      }
   }

   @Override
   public float getReach() {
      if (!this.isAIEnabled()) {
         Minecraft.setErrorMessage("getReach: doesn't handle old AI mobs yet");
         return 0.0F;
      } else {
         return 1.2F + this.getHeldItemReachBonus() * 0.6F;
      }
   }

   @Inject(method = "onLivingUpdate",at = @At("RETURN"))
   public void onLivingUpdate(CallbackInfo callbackInfo) {
      if (this.fireParticleTick > 0){
         this.fireParticleTick--;
      }
   }

   @Override
   public GroupDataEntity onSpawnWithEgg(GroupDataEntity par1EntityLivingData) {
      if (Configs.Entities.MOB_ATTACK_CAUSE_FIRE.get() && this.getChanceOfCausingFire() > this.rand.nextFloat()){
         this.attackCauseFire = true;
      }
      return super.onSpawnWithEgg(par1EntityLivingData);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("AttackCauseFire")){
         this.attackCauseFire = par1NBTTagCompound.getBoolean("AttackCauseFire");
      }
   }

   protected void tryDisarmTarget(Entity target){
      if (this.onServer() && target instanceof EntityLiving) {
         EntityLiving entity_living_base = (EntityLiving)target;
         ItemStack item_stack_to_drop = entity_living_base.getHeldItemStack();
         if (item_stack_to_drop != null && this.rand.nextFloat() < this.getDisarmingChance(this.getHeldItemStack())) {
            if (entity_living_base instanceof EntityInsentient){
               EntityInsentient entity_living = (EntityInsentient)entity_living_base;
               if (entity_living.canBeDisarmed()) {
                  entity_living.dropItemStack(item_stack_to_drop, entity_living.height / 2.0F).delayBeforeCanPickup = 20;
                  entity_living.clearMatchingEquipmentSlot(item_stack_to_drop);
                  entity_living.ticks_disarmed = 40;
               }
            }else if (entity_living_base instanceof EntityPlayer){
               EntityPlayer player = (EntityPlayer) entity_living_base;
               if (!player.isBlocking()){
                  EntityItem entityItem = player.dropItemStack(item_stack_to_drop, player.height / 2.0F);
                  Vec3D lookVec = player.getLookVec();
                  entityItem.delayBeforeCanPickup = 20;
                  //Rotate 90 degrees,to the right side of the player
                  entityItem.addVelocity(-lookVec.zCoord * 0.4,0,lookVec.xCoord  * 0.4);
                  player.setHeldItemStack(null);
               }
            }
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setBoolean("AttackCauseFire",this.attackCauseFire);
   }
}
