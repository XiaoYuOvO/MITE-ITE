package net.xiaoyu233.mitemod.miteite.item;

import com.google.common.collect.Lists;
import net.minecraft.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModifierUtils {
    @Nullable
    public static <T extends ItemModifierTypes> T getModifierWithWeight(List<T> allCanBeApplied, Random rand){ ;
        int totalWeight = 0;
        for (T modifierTypes : allCanBeApplied) {
            totalWeight += modifierTypes.getWeight();
        }
        if (totalWeight > 0){
            int currentWeight = rand.nextInt(totalWeight);
            int x = 0;
            for(int j = allCanBeApplied.size(); x < j; ++x) {
                T t = allCanBeApplied.get(x);
                currentWeight -= t.getWeight();
                if (currentWeight < 0) {
                    return t;
                }
            }
        }

        return null;
    }

    public static ArrayList<ToolModifierTypes> getAllCanBeAppliedToolModifiers(ItemStack stack) {
        ArrayList<ToolModifierTypes> toolModifierTypes = Lists.newArrayList(ToolModifierTypes.values());
        toolModifierTypes.removeIf((modifierType) -> !modifierType.canApplyTo(stack));
        if (stack.stackTagCompound != null) {
            toolModifierTypes.removeIf((toolModifierTypes1) -> toolModifierTypes1.getModifierLevel(stack.stackTagCompound) >= toolModifierTypes1.getMaxLevel());
        }

        return toolModifierTypes;
    }

    public static ArrayList<ArmorModifierTypes> getAllCanBeAppliedArmorModifiers(ItemStack stack) {
        ArrayList<ArmorModifierTypes> armorModifierTypes = Lists.newArrayList(ArmorModifierTypes.values());
        armorModifierTypes.removeIf((modifierType) -> !modifierType.canApplyTo(stack));
        if (stack.stackTagCompound != null) {
            armorModifierTypes.removeIf((armorModifierTypes1) -> armorModifierTypes1.getModifierLevel(stack.stackTagCompound) >= armorModifierTypes1.getMaxLevel());
        }

        return armorModifierTypes;
    }



}
