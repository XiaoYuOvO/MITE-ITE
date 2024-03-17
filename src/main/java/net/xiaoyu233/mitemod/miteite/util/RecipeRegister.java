package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.ItemStack;

public class RecipeRegister{
    public RecipesArgs registerShapedRecipe(ItemStack out, boolean include_in_lowest_crafting_difficulty_determination, Object... input){
        RecipesArgs e = new RecipesArgs(out, include_in_lowest_crafting_difficulty_determination, input);
        RegisterHelper.shapedRecipes.add(e);
        return e;
    }

    public RecipesArgs registerShapelessRecipe(ItemStack out, boolean include_in_lowest_crafting_difficulty_determination, Object... input){
        RecipesArgs e = new RecipesArgs(out, include_in_lowest_crafting_difficulty_determination, input);
        RegisterHelper.shapelessRecipe.add(e);
        return e;
    }
}

