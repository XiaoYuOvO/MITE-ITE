package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.ShapedRecipes;
import net.minecraft.ShapelessRecipes;
import net.minecraft.aah;

import java.util.Comparator;

public class RecipeSorter implements Comparator<aah> {
    @Override
    public int compare(aah var1, aah var2) {
        if (var1 instanceof ShapelessRecipes && var2 instanceof ShapedRecipes) {
            return 1;
        } else if (var2 instanceof ShapelessRecipes && var1 instanceof ShapedRecipes) {
            return -1;
        } else if (var2.a() < var1.a()) {
            return -1;
        } else {
            return var2.a() > var1.a() ? 1 : 0;
        }
    }
}