package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentExtend extends Enchantment {
    protected EnchantmentExtend(int id, EnumRarity rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    public String getNameSuffix() {
        return "extend";
    }

    @Override
    public boolean canEnchantItem(Item var1) {
        return var1 instanceof ItemTool;
    }

    @Override
    public boolean isOnCreativeTab(CreativeTabs var1) {
        return var1 == CreativeTabs.tabTools;
    }
}
