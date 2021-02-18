package net.xiaoyu233.mitemod.miteite.item.recipe;

import net.minecraft.ItemStack;

public class DowngradeFeedback implements IFaultFeedback{
    private final int level;
    private DowngradeFeedback(int level){
        this.level = level;
    }

    public static DowngradeFeedback of(int level){
        return new DowngradeFeedback(level);
    }

    @Override
    public ItemStack accept(ItemStack itemStack) {
        itemStack.setForgingGrade(Math.max(itemStack.getForgingGrade() - this.level,0));
        return itemStack;
    }

    @Override
    public String getName() {
        return "forging.failFeedback.downgrade";
    }

    @Override
    public int getData() {
        return this.level;
    }
}
