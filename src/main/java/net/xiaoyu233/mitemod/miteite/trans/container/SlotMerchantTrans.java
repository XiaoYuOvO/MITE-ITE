package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotMerchantResult.class)
public class SlotMerchantTrans extends Slot {
   public SlotMerchantTrans(IInventory inventory, int slot_index, int display_x, int display_y) {
      super(inventory, slot_index, display_x, display_y);
   }

   @Inject(method = "onPickupFromSlot", at = @At("HEAD"), cancellable = true)
   private void injectPreventClientSideTrading(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, CallbackInfo ci){
      if (par1EntityPlayer.worldObj.isRemote){
         ci.cancel();
      }
   }
}
