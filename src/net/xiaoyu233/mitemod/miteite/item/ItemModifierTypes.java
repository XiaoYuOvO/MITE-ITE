package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.NBTTagCompound;

public interface ItemModifierTypes<T extends ItemModifierTypes<?>> {
    float getModifierValue(NBTTagCompound itemTag);
    int getModifierLevel(NBTTagCompound itemTag);

    String getNbtName();

}
