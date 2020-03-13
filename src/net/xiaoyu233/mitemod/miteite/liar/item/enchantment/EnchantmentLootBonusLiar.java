package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EnchantmentLootBonus.class)
public class EnchantmentLootBonusLiar extends Enchantment {
    @Stealing
    protected EnchantmentLootBonusLiar(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    @Stealing
    public String getNameSuffix() {
        return null;
    }

    @Override
    @Stealing
    public boolean canEnchantItem(Item item) {
        return false;
    }

    @Override
    @Stealing
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }

    public int getNumLevelsForVibranium() {
        return 5;
    }
}
