package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EnchantmentProtection.class)
public class EnchantmentProtectionLiar extends Enchantment{
    @Stealing
    protected EnchantmentProtectionLiar(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    @Stealing
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }

    @Override
    @Stealing
    public boolean canEnchantItem(Item item) {
        return false;
    }

    @Override
    @Stealing
    public String getNameSuffix() {
        return null;
    }
}
