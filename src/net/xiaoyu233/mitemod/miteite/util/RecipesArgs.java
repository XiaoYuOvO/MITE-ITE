package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.ItemStack;

public class RecipesArgs{
    public final ItemStack result;
    public final Object[] inputs;
    public final boolean include_in_lowest_crafting_difficulty_determination;
    public RecipesArgs(ItemStack result,boolean include_in_lowest_crafting_difficulty_determination,Object... inputs){
        this.result = result;
        this.include_in_lowest_crafting_difficulty_determination = include_in_lowest_crafting_difficulty_determination;
        this.inputs = inputs;
    }
}
