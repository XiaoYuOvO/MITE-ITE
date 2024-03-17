package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.Enchantment;
import net.minecraft.EnchantmentHelper;
import net.minecraft.ItemStack;

public interface ITELivingEntity extends ITELivingBase {
    boolean[] getPickedUpAHeldItemArray();
    boolean canBeDisarmed();

    float getWeaponDamageBoost();
    default float getDisarmingChance(ItemStack itemStack){
        return EnchantmentHelper.getEnchantmentLevelFraction(Enchantment.disarming, itemStack) * 0.4f;
    }
}
