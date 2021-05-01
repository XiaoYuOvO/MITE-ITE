package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.ItemStack;

public class RecipeRegister{
    public void registerShapedRecipe(ItemStack out, boolean include_in_lowest_crafting_difficulty_determination, Object... input){
        RegisterHelper.shapedRecipes.add(new RecipesArgs(out, include_in_lowest_crafting_difficulty_determination, input));
    }

    public void registerShapelessRecipe(ItemStack out, boolean include_in_lowest_crafting_difficulty_determination, Object... input){
        RegisterHelper.shapelessRecipe.add(new RecipesArgs(out, include_in_lowest_crafting_difficulty_determination, input));
    }
}

