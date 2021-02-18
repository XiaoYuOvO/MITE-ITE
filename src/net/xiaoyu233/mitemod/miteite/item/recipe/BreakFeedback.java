package net.xiaoyu233.mitemod.miteite.item.recipe;

import net.minecraft.ItemStack;

public class BreakFeedback implements IFaultFeedback{
    @Override
    public ItemStack accept(ItemStack itemStack) {
        return null;
    }

    @Override
    public String getName() {
        return "forging.failFeedback.break";
    }

    @Override
    public int getData() {
        return 0;
    }
}
