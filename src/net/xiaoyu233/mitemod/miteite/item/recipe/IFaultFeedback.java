package net.xiaoyu233.mitemod.miteite.item.recipe;

import net.minecraft.ItemStack;

public interface IFaultFeedback {
    ItemStack accept(ItemStack itemStack);
    String getName();
    int getData();
}
