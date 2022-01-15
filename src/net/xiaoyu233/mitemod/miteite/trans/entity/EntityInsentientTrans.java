package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Mixin(EntityInsentient.class)
public abstract class EntityInsentientTrans extends EntityLiving {
   @Shadow
   public float[] equipmentDropChances;
   @Shadow
   public boolean picked_up_a_held_item;
   public boolean[] picked_up_a_held_item_array;
   private final Map<EntityPlayer,Integer> playerSteppedCountMap = new HashMap<>();
   @Shadow
   protected float defaultPitch;
   @Shadow
   protected int numTicksToChaseTarget;

   public EntityInsentientTrans(World par1World) {
      super(par1World);
   }

   public boolean canBeDisarmed() {
      return true;
   }

   @Shadow
   public boolean canNeverPickUpItem(Item item_on_ground) {
      return false;
   }

   @Shadow
   public boolean canWearItem(ItemStack item_stack_on_ground) {
      return false;
   }

   @Shadow
   public boolean getAlwaysRenderNameTag() {
      return false;
   }

   @Shadow
   public void setAlwaysRenderNameTag(boolean par1) {
   }

   @Shadow
   public String getCustomNameTag() {
      return "";
   }

   @Shadow
   public void setCustomNameTag(String par1Str) {
   }

   public float[] getEquipmentDropChances() {
      return this.equipmentDropChances;
   }

   @Shadow
   public ItemStack getEquipmentInSlot(int var5) {
      return null;
   }

   @Shadow
   public ItemStack getHeldItemStack() {
      return null;
   }

   @Shadow
   public void setHeldItemStack(ItemStack itemStack) {
   }

   @Shadow
   public ItemStack[] getInventory() {
      return new ItemStack[0];
   }

   @Shadow
   public List getItemsWithinPickupDistance() {
      return null;
   }

   @Shadow
   public final EntityLiving getTarget() {
      return null;
   }

   @Shadow
   public ItemStack[] getWornItems() {
      return new ItemStack[0];
   }

   @Shadow
   public boolean hasEquipment() {
      return false;
   }

   public float getWeaponDamageBoost(){
      return 1.0f;
   }

   @Inject(method = "applyEntityAttributes",at = @At("RETURN"))
   private void injectEntityAttributes(CallbackInfo c){
      this.picked_up_a_held_item_array = new boolean[5];
   }

   @Inject(method = "onMeleeAttacked",at = @At("HEAD"))
   private void injectMeleeAttacked(EntityLiving attacker, EntityDamageResult result,CallbackInfo c) {
      if (result != null && attacker != null && result.entityLostHealth()) {
         ItemStack stack = attacker.getHeldItemStack();
         if (stack != null) {
            Item item = stack.getItem();
            if (attacker instanceof EntityPlayer && (item instanceof ItemSword || item instanceof ItemWarHammer || item instanceof ItemAxe)) {
               int amp = 1;
               if (item instanceof ItemAxe && dyCast(this) instanceof EntityEarthElemental){
                  amp = 10;
               }
               if (!item.isMaxToolLevel(stack)) {
                  if (item.isWeapon(item) || amp != 1) {
                     item.addExpForTool(stack, (EntityPlayer) attacker, (int) Math.min(this.getMaxHealth(), result.getAmountOfHealthLost() * amp));
                  } else {
                     item.addExpForTool(stack, (EntityPlayer) attacker, 1);
                  }
               }

               float slowMdfLvl = ToolModifierTypes.SLOWDOWN_MODIFIER.getModifierValue(stack.getTagCompound());
               if (result.entity instanceof EntityLiving && slowMdfLvl >= 1.0F) {
                  ((EntityLiving)result.entity).addPotionEffect(new MobEffect(2, (int)(slowMdfLvl * 20.0F), (int)(slowMdfLvl / 2.0F)));
               }
            }
         }
      }

      super.onMeleeAttacked(attacker, result);
   }

   @Inject(method = "attackEntityFrom",at = @At(value = "INVOKE",target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;",shift = At.Shift.BEFORE))
   private void injectAttackEntityFrom(Damage damage, CallbackInfoReturnable<EntityDamageResult> callbackInfo){
      double max = Configs.GameMechanics.STEPPED_PLAYER_BASE_DAMAGE_MAX.get();
      if (max != 0.0D) {
         Entity responsibleEntity = damage.getSource().getResponsibleEntity();
         ItemStack itemAttackedWith = damage.getItemAttackedWith();
         if (responsibleEntity instanceof EntityPlayer && itemAttackedWith != null && itemAttackedWith.getItem() instanceof ItemTool) {
            EntityPlayer player = (EntityPlayer) responsibleEntity;
            if (this.playerSteppedCountMap.containsKey(responsibleEntity)) {
               Integer time = this.playerSteppedCountMap.get(responsibleEntity);
               damage.setAmount((float) (damage.getAmount() +
                       //Increase per lvl: enchantment + player base
                       (time * EnchantmentManager.getEnchantmentLevel(Enchantments.CONQUEROR,itemAttackedWith) * Configs.Item.Enchantment.CONQUEROR_DAMAGE_BOOST_PER_LVL.get()) +
                       (Math.min(max, time * Math.max(0,player.getExperienceLevel()) * Configs.GameMechanics.STEPPED_PLAYER_DAMAGE_INCREASE_PER_LVL.get()))));
               this.playerSteppedCountMap.put(player, time + 1);
            } else {
               this.playerSteppedCountMap.put(player, 1);
            }
         }
      }
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "tryPickUpItems",
           at = @At(value = "FIELD",
                   target = "Lnet/minecraft/EntityInsentient;persistenceRequired:Z",
                   shift = At.Shift.BEFORE))
   private void injectPickUpItems(CallbackInfo ci, List entity_items, Iterator iterator, EntityItem entity_item, ItemStack item_stack_on_ground, Item item_on_ground, int var5, boolean pickup, ItemStack current_item_stack, boolean set_dead){
      if (entity_item.canBePickUpByPlayer()){
         this.picked_up_a_held_item_array[var5] = true;
      }
      this.picked_up_a_held_item = true;
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callbackInfo){
      if (par1NBTTagCompound.hasKey("picked_up_held_items")) {
         if (this.picked_up_a_held_item_array == null) {
            this.picked_up_a_held_item_array = new boolean[5];
         }

         byte[] picked_up_held_items = par1NBTTagCompound.getByteArray("picked_up_held_items");
         for (int i = 0, jLength = picked_up_held_items.length; i < jLength; i++) {
            if (picked_up_held_items[i] == 1) {
               this.picked_up_a_held_item_array[i] = true;
            }
         }
      }

      if (par1NBTTagCompound.hasKey("PlayerSteppedCountMap")) {
         NBTTagList attackCountMap = par1NBTTagCompound.getTagList("PlayerSteppedCountMap");
         int count = attackCountMap.tagCount();

         for(int i = 0; i < count; ++i) {
            NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
            Entity attacker = this.getWorldServer().getEntityByID(a.getInteger("Attacker"));
            if (attacker instanceof EntityPlayer) {
               this.playerSteppedCountMap.put((EntityPlayer) attacker, a.getInteger("Count"));
            }
         }
      }
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo c){
      byte[] picked_up_held_items = new byte[this.picked_up_a_held_item_array.length];
      boolean[] pickedUpAHeldItemArray = this.picked_up_a_held_item_array;

      for(int i = 0; i < pickedUpAHeldItemArray.length; ++i) {
         if (pickedUpAHeldItemArray[i]) {
            picked_up_held_items[i] = 1;
         }
      }
      NBTTagList nbtTagList = new NBTTagList();
      for (Map.Entry<EntityPlayer, Integer> integerEntry : this.playerSteppedCountMap.entrySet()) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("Attacker", ( integerEntry).getKey().entityId);
         compound.setInteger("Count", (integerEntry).getValue());
         nbtTagList.appendTag(compound);
      }

      par1NBTTagCompound.setTag("PlayerSteppedCountMap", nbtTagList);

      par1NBTTagCompound.setByteArray("picked_up_held_items", picked_up_held_items);
   }

   @Accessor("came_from_spawner")
   public abstract boolean isCame_from_spawner();

   public boolean isDead() {
      return this.isDead;
   }

   @Shadow
   public boolean isWearing(ItemStack itemStack) {
      return false;
   }

   @Shadow
   public void setCanPickUpLoot(boolean par1) {
   }

   @Shadow
   public void setCurrentItemOrArmor(int var5, ItemStack setStackSize) {
   }

   @Shadow
   public boolean setWornItem(int i, ItemStack itemStack) {
      return false;
   }
}
