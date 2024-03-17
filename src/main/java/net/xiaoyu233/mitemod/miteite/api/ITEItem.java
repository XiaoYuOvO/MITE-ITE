package net.xiaoyu233.mitemod.miteite.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;

import java.util.List;

public interface ITEItem extends IUpgradableItem{
    List<Material> getMaterials();
    default void addExpForTool(ItemStack stack, EntityPlayer player, int exp){};
    void setSugarContent(int sugarContent);
    int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes modifierType);
    default Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack){
        return HashMultimap.create();
    };
    int getCookTime();
    int getExpReqForLevel(int i, boolean weapon);
    float getMeleeDamageBonus(ItemStack stack);
    String getResourceLocationPrefix();
    int getToolLevel(ItemStack itemStack);
    boolean hasExpAndLevel();
    boolean isMaxToolLevel(ItemStack itemStack);
    boolean isWeapon(Item b);
    void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack);
    float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer user);
}
