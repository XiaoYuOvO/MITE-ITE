package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.EnchantmentLootBonus;
import team.unknowndomain.liar.annotation.Deceive;

@Deceive(EnchantmentLootBonus.class)
public class EnchantmentLootBonusLiar {
    public int getNumLevels() {
        return 5;
    }
}
