package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DispenseBehaviorItem.class)
public class DispenseBehaviorItemTrans {
   @Overwrite
   protected ItemStack dispenseStack(ISourceBlock par1IBlockSource, ItemStack par2ItemStack) {
      if (par2ItemStack.stackSize > 0) {
         EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
         IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
         ItemStack var5 = par2ItemStack.splitStack(1);
         DispenseBehaviorItem.doDispense(par1IBlockSource.getWorld(), var5, 6, var3, var4);
      }

      return par2ItemStack;
   }
}
