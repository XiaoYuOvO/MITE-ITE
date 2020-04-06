package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EnchantmentLootBonus.class)
public class EnchantmentLootBonusTrans extends Enchantment {
    @Marker
    protected EnchantmentLootBonusTrans(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    @Marker
    public String getNameSuffix() {
        return null;
    }

    @Override
    @Marker
    public boolean canEnchantItem(Item item) {
        return false;
    }

    @Override
    @Marker
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }

    public int getNumLevelsForVibranium() {
        return 5;
    }
}
