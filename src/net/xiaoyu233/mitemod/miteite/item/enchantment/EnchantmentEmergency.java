package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentEmergency extends Enchantment {
    protected EnchantmentEmergency(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 1;
    }

    @Override
    public boolean enchantIndividually() {
        return true;
    }

    @Override
    public float enchantIndividualChance(int enchantmentLevel) {
        return enchantmentLevel < 50 ? 0 : 0.1f + 0.3f * (enchantmentLevel / 158f);
    }

    @Override
    public String getNameSuffix() {
        return "emergency";
    }

    @Override
    public boolean canEnchantItem(Item var1) {
        return var1 instanceof ItemArmor;
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab var1) {
        return var1 == CreativeModeTab.tabCombat;
    }
}
