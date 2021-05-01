package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(wa.class)
public class SlotMerchantTrans extends Slot {
   @Shadow
   private IMerchant theMerchant;
   @Shadow
   private InventoryMerchant theMerchantInventory;

   public SlotMerchantTrans(IInventory inventory, int slot_index, int display_x, int display_y) {
      super(inventory, slot_index, display_x, display_y);
   }

   @Shadow
   private boolean func_75230_a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack) {
      return false;
   }

   @Overwrite
   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
      if (!par1EntityPlayer.worldObj.isRemote) {
         this.onCrafting(par2ItemStack);
         MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
         if (var3 != null) {
            ItemStack var4 = this.theMerchantInventory.getStackInSlot(0);
            ItemStack var5 = this.theMerchantInventory.getStackInSlot(1);
            if (this.func_75230_a(var3, var4, var5) || this.func_75230_a(var3, var5, var4)) {
               this.theMerchant.useRecipe(var3);
               if (var4 != null && var4.stackSize <= 0) {
                  var4 = null;
               }

               if (var5 != null && var5.stackSize <= 0) {
                  var5 = null;
               }

               this.theMerchantInventory.setInventorySlotContents(0, var4);
               this.theMerchantInventory.setInventorySlotContents(1, var5);
            }
         }
      }

   }
}
