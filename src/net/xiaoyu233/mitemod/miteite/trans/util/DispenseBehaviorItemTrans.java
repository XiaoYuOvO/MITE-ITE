package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(DispenseBehaviorItem.class)
public class DispenseBehaviorItemTrans {
    protected ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack) {
        if(par2ItemStack.b > 0){
            EnumFacing var3 = BlockDispenser.l_(par1IBlockSource.h());
            IPosition var4 = BlockDispenser.a(par1IBlockSource);
            ItemStack var5 = par2ItemStack.a(1);
            DispenseBehaviorItem.a(par1IBlockSource.k(), var5, 6, var3, var4);
        }
        return par2ItemStack;
    }
}
