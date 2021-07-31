package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;

import javax.annotation.Nonnull;

public interface IUpgradableItem {
    int getExpReqForLevel(int i, boolean weapon);
    boolean isMaxToolLevel(ItemStack itemStack);
    int getMaxToolLevel(ItemStack itemStack);
    default float getEquipmentExpBounce(@Nonnull ItemStack itemStack){
        return (0.05f + itemStack.getForgingGrade() * 0.05f) * itemStack.getForgingGrade() / 2;
    }

    void addExpForTool(ItemStack stack, EntityPlayer player, int exp);
}
