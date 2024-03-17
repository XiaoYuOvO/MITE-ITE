package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.CreativeTabs;
import net.minecraft.Enchantment;
import net.minecraft.EnumRarity;
import net.minecraft.Item;

public class EnchantmentConqueror extends Enchantment {
    protected EnchantmentConqueror(int id, EnumRarity rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public String getNameSuffix() {
        return "conqueror";
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

//    @Override
    public int getNumLevelsForVibranium() {
        return 7;
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return item.isWeapon(item);
    }

    @Override
    public boolean isOnCreativeTab(CreativeTabs creativeModeTab) {
        return creativeModeTab == CreativeTabs.tabCombat;
    }

}
