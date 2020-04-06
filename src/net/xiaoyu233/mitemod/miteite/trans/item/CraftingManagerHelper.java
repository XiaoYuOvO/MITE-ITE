package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.RecipeSorter;
import net.xiaoyu233.mitemod.miteite.util.RecipesArgs;
import net.xiaoyu233.mitemod.miteite.util.RecipesHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("ConstantConditions")
@Transform(CraftingManager.class)
public class CraftingManagerHelper{
    @Link
    private static CraftingManager a;
    @Link
    private static final ArrayList<RecipesArgs> shapedRecipes;
    @Link
    private static final ArrayList<RecipesArgs> shapelessRecipe;
    @Link
    private static Method addShapeless;
    @Link
    private static Method addShaped;
    static
    {
        shapedRecipes = new ArrayList<>();
        shapelessRecipe = new ArrayList<>();
        Items.registerItems();
        Blocks.registerBlocks();
        Blocks.registerRecipes();
        Items.registerRecipes();
        Achievements.registerAchievements();
        try {
            a = CraftingManager.class.getDeclaredConstructor().newInstance();
            addShaped = CraftingManager.class.getDeclaredMethod("addRecipe", ItemStack.class, boolean.class, Object[].class);
            addShapeless = CraftingManager.class.getDeclaredMethod("addShapelessRecipe", ItemStack.class, boolean.class, Object[].class);
            for (RecipesArgs shapedRecipe : shapedRecipes) {
                addShaped.invoke(a,shapedRecipe.result,shapedRecipe.include_in_lowest_crafting_difficulty_determination,shapedRecipe.inputs);
            }
            for (RecipesArgs recipesArgs : shapelessRecipe) {
                addShapeless.invoke(a,recipesArgs.result,recipesArgs.include_in_lowest_crafting_difficulty_determination,recipesArgs.inputs);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        Collections.sort(a.b(), new RecipeSorter());
        Item.verifyThatAllItemsHaveMaterialsDefined();

        for(int i = 0; i < Item.g.length; ++i) {
            Item item = Item.g[i];
            if (item != null) {
                int num_subtypes = item.getNumSubtypes();
                if (num_subtypes == 0) {
                    RecipesHelper.checkRecipe(item, 0,null);
                } else {
                    for(int subtype = 0; subtype < num_subtypes; ++subtype) {
                        RecipesHelper.checkRecipe(item, subtype,null);
                    }
                }
            }
        }

    }
    private void checkRecipe(Item item, int subtype_or_0) {}


    public static void registerShapelessRecipe(ItemStack out,boolean include_in_lowest_crafting_difficulty_determination,Object... input){
        shapelessRecipe.add(new RecipesArgs(out,include_in_lowest_crafting_difficulty_determination,input));
    }

    public static void registerShapedRecipe(ItemStack out,boolean include_in_lowest_crafting_difficulty_determination,Object... input){
        shapedRecipes.add(new RecipesArgs(out,include_in_lowest_crafting_difficulty_determination,input));
    }

    @Marker
    public aah addShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
        return null;
    }

    @Marker
    private ShapedRecipes a(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
        return null;
    }

}
