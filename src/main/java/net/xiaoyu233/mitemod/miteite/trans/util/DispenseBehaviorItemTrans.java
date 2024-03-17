package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BehaviorDefaultDispenseItem.class)
public class DispenseBehaviorItemTrans {
   @Inject(method = "dispenseStack", at= @At("HEAD"), cancellable = true)
   protected void fixNegativeCountItem(IBlockSource par1IBlockSource, ItemStack par2ItemStack, CallbackInfoReturnable<ItemStack> cir) {
      if (par2ItemStack.stackSize <= 0) {
         cir.setReturnValue(par2ItemStack);
      }
   }
}
