package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.Minecraft;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(Enchantment.class)
public class EnchantmentTrans {
    @Link
    private int difficulty;

    public int getNumLevelsForVibranium(){
        return this.getNumLevels();
    }
    @Marker
    public int getNumLevels() {
        return 5;
    }


    public int getMinEnchantmentLevelsCost(int level) {
        if (level < 1) {
            Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level < 1 for " + this);
            return 0;
        } else {
            if (!this.hasLevels()) {
                if (level != 3) {
                    Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level must be 3 for " + this);
                    return 0;
                }
            } else if (level > this.getNumLevelsForVibranium()) {
                Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level too high for " + this);
                return 0;
            }

            return Math.max(this.difficulty - 10, 0) + this.difficulty * (level - 1) + 1;
        }
    }

    @Marker
    private boolean hasLevels() {
        return false;
    }
}
