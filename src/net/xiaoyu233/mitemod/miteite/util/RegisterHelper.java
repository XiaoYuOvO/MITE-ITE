package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.CraftingManager;
import net.minecraft.ShapedRecipes;
import net.minecraft.ShapelessRecipes;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableRecipes;
import net.xiaoyu233.mitemod.miteite.trans.item.recipe.CraftingManagerInvoker;

import java.util.ArrayList;

@SuppressWarnings("RedundantCast")
public class RegisterHelper {
    public static ArrayList<RecipesArgs> shapedRecipes = new ArrayList<>();
    public static ArrayList<RecipesArgs> shapelessRecipe = new ArrayList<>();
    public static void registerAllItems(){
        Items.registerItems();
        Blocks.registerBlocks();
        Achievements.registerAchievements();
    }
    public static void registerAllRecipes(CraftingManager crafters){
        RecipeRegister recipeRegister = new RecipeRegister();
        Blocks.registerRecipes(recipeRegister);
        Items.registerRecipes(recipeRegister);
        ForgingTableRecipes.registerAll();

        RecipesArgs recipesArgs;
        for (RecipesArgs shapedRecipe : shapedRecipes) {
            recipesArgs = shapedRecipe;
            ShapedRecipes shapedRecipes = ((CraftingManagerInvoker) crafters).addRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
            if (recipesArgs.isExtendsNBT()){
                shapedRecipes.func_92100_c();
            }
        }
        for (RecipesArgs args : shapelessRecipe) {
            recipesArgs = args;
            ShapelessRecipes shapelessRecipes = ((CraftingManagerInvoker) crafters).addShapelessRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
            if (recipesArgs.isExtendsNBT()){
                shapelessRecipes.propagateTagCompound();
            }
        }
    }
}
