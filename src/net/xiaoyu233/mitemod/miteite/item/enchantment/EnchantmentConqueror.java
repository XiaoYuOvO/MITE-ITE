package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.CreativeModeTab;
import net.minecraft.Enchantment;
import net.minecraft.Item;
import net.minecraft.yq;

public class EnchantmentConqueror extends Enchantment {
    protected EnchantmentConqueror(int id, yq rarity, int difficulty) {
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

    @Override
    public int getNumLevelsForVibranium() {
        return 7;
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return item.isWeapon(item);
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return creativeModeTab == CreativeModeTab.tabCombat;
    }
}
