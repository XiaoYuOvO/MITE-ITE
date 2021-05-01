package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentCrit extends Enchantment {
    protected EnchantmentCrit(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    public String getNameSuffix() {
        return "crit";
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return item instanceof ItemSword;
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab var1) {
        return var1 == CreativeModeTab.tabCombat;
    }
}
