package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(EntityInsentient.class)
public abstract class EntityInsentientTrans extends EntityLiving {
   @Shadow
   public float[] equipmentDropChances;
   @Shadow
   public boolean picked_up_a_held_item;
   public boolean[] picked_up_a_held_item_array;
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
            if (attacker instanceof EntityPlayer && (item instanceof ItemSword || item instanceof ItemWarHammer || item instanceof ItemBattleAxe)) {
               if (!item.isMaxToolLevel(stack)) {
                  item.addExpForTool(stack, (EntityPlayer)attacker, (int)Math.min(this.getMaxHealth(), result.getAmountOfHealthLost()));
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

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "tryPickUpItems",
           at = @At(value = "FIELD",
                   target = "Lnet/minecraft/EntityInsentient;persistenceRequired:Z",
                   shift = At.Shift.BEFORE))
   private void injectPickUpItems(CallbackInfo ci, List entity_items, Iterator iterator, EntityItem entity_item, ItemStack item_stack_on_ground, Item item_on_ground, int var5, boolean pickup, ItemStack current_item_stack, boolean set_dead){
      this.picked_up_a_held_item_array[var5] = true;
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
