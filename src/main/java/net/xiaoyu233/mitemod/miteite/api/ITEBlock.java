package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.ItemStack;

public interface ITEBlock {
    default String getItemDisplayName(ItemStack itemStack){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
