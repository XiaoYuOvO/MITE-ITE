package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.atv;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(Enchantment.class)
public class EnchantmentLiar {
    @Liar
    private int difficulty;

    public int getNumLevelsForVibranium(){
        return this.getNumLevels();
    }
    @Stealing
    public int getNumLevels() {
        return 5;
    }


    public int getMinEnchantmentLevelsCost(int level) {
        if (level < 1) {
            atv.setErrorMessage("getMinEnchantmentLevelsCost: level < 1 for " + this);
            return 0;
        } else {
            if (!this.hasLevels()) {
                if (level != 3) {
                    atv.setErrorMessage("getMinEnchantmentLevelsCost: level must be 3 for " + this);
                    return 0;
                }
            } else if (level > this.getNumLevelsForVibranium()) {
                atv.setErrorMessage("getMinEnchantmentLevelsCost: level too high for " + this);
                return 0;
            }

            return Math.max(this.difficulty - 10, 0) + this.difficulty * (level - 1) + 1;
        }
    }

    @Stealing
    private boolean hasLevels() {
        return false;
    }
}
