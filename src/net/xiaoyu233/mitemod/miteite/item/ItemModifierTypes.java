package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.NBTTagCompound;

public interface ItemModifierTypes {
    float getModifierValue(NBTTagCompound itemTag);
    int getModifierLevel(NBTTagCompound itemTag);

    String getNbtName();

}
