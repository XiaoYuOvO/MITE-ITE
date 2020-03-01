package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EnchantmentThorns.class)
public class EnchantmentThornsLiar extends Enchantment{
    @Stealing
    protected EnchantmentThornsLiar(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
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

    @Override
    public int getNumLevels() {
        return 5;
    }
}
