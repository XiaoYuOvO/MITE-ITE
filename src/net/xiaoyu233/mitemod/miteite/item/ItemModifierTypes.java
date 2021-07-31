package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;

public interface ItemModifierTypes {
    static boolean hasModifier(ItemStack stack, ArmorModifierTypes modifierType) {
        NBTTagCompound itemTag = stack.stackTagCompound;
        return itemTag != null && itemTag.hasKey("modifiers") && itemTag.getCompoundTag("modifiers").hasKey(modifierType.nbtName);
    }

    float getModifierValue(NBTTagCompound itemTag);
    int getModifierLevel(NBTTagCompound itemTag);
    float getWeight();
    String getNbtName();
    boolean canApplyTo(ItemStack itemStack);
    int getMaxLevel();
}
