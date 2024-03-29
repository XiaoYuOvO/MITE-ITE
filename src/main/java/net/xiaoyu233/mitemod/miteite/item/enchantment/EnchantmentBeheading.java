package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentBeheading extends Enchantment {
    protected EnchantmentBeheading(int id, EnumRarity rarity, int difficulty) {
        super(id, rarity, difficulty);
    }


    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    public String getNameSuffix() {
        return "beheading";
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return item instanceof ItemAxe;
    }

    @Override
    public boolean isOnCreativeTab(CreativeTabs creativeModeTab) {
        return creativeModeTab == CreativeTabs.tabCombat;
    }
}
