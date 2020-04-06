package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EnchantmentProtection.class)
public class EnchantmentProtectionTrans extends Enchantment{
    @Marker
    protected EnchantmentProtectionTrans(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevelsForVibranium() {
        return 5;
    }

    @Override
    @Marker
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }

    @Override
    @Marker
    public boolean canEnchantItem(Item item) {
        return false;
    }

    @Override
    @Marker
    public String getNameSuffix() {
        return null;
    }
}
