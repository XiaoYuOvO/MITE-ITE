package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EnchantmentWeaponDamage.class)
public class EnchantmentWeaponDamageXLiar extends Enchantment{
    @Stealing
    protected EnchantmentWeaponDamageXLiar(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }
    //    public int getMinEnchantmentLevelsCost(int level) {
//        if (level < 1) {
//            atv.setErrorMessage("getMinEnchantmentLevelsCost: level < 1 for " + this);
//            return 0;
//        } else {
//            if (!this.hasLevels()) {
//                if (level != 3) {
//                    atv.setErrorMessage("getMinEnchantmentLevelsCost: level must be 3 for " + this);
//                    return 0;
//                }
//            } else if (level > this.getNumLevels()) {
//                atv.setErrorMessage("getMinEnchantmentLevelsCost: level too high for " + this);
//                return 0;
//            }
//
//            return Math.max(this.difficulty - 10, 0) + this.difficulty * (level - 1) + 1;
//        }
//    }

    @Override
    @Stealing
    public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
        return false;
    }

    @Override
    public int getNumLevels() {
        return 7;
    }

    public boolean isCompatibleWith(Enchantment enchantment,Item item){
        return item.getHardestMetalMaterial() == Materials.vibranium ? !(enchantment == this) : !(enchantment instanceof EnchantmentWeaponDamage);
    }

    public boolean canEnchantItem(Item item) {
        if (this != Enchantment.l) {
            if (this == Enchantment.n) {
                return item instanceof ItemSword;
            } else if (this == Enchantment.m) {
                return item.getClass() == ItemWarHammer.class || (item.getHardestMetalMaterial() == Materials.vibranium && item.getClass() == ItemSword.class);
            } else {
                return false;
            }
        } else {
            return item instanceof ItemSword || item.getClass() == ItemBattleAxe.class || item instanceof ItemScythe;
        }
    }

    @Override
    @Stealing
    public String getNameSuffix() {
        return null;
    }
}
