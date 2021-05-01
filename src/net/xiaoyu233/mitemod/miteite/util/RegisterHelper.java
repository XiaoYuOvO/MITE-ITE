package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.CraftingManager;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableRecipes;
import net.xiaoyu233.mitemod.miteite.trans.item.CraftingManagerInvoker;

import java.util.ArrayList;

@SuppressWarnings("RedundantCast")
public class RegisterHelper {
    public static ArrayList<RecipesArgs> shapedRecipes = new ArrayList<>();
    public static ArrayList<RecipesArgs> shapelessRecipe = new ArrayList<>();
    public static void registerAllItems(){
        Items.registerItems();
        Blocks.registerBlocks();
        Achievements.registerAchievements();
        Enchantments.registerEnchantments();
    }
    public static void registerAllRecipes(CraftingManager crafters){
        RecipeRegister recipeRegister = new RecipeRegister();
        Blocks.registerRecipes(recipeRegister);
        Items.registerRecipes(recipeRegister);
        ForgingTableRecipes.registerAll();

        RecipesArgs recipesArgs;
        for (RecipesArgs shapedRecipe : shapedRecipes) {
            recipesArgs = shapedRecipe;
            ((CraftingManagerInvoker)crafters).addRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
        }
        for (RecipesArgs args : shapelessRecipe) {
            recipesArgs = args;
            ((CraftingManagerInvoker)crafters).addShapelessRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
        }
    }
}
