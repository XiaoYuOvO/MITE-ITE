package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public class ItemUtil {
    public static float getProtectionForDamage(ItemStack armor, DamageSource damageSource, EntityLivingBase owner) {
        if (damageSource.isFireDamage()) {
            return ArmorModifierTypes.FIRE_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound);
        } else if (damageSource.isExplosion()) {
            return ArmorModifierTypes.EXPLOSION_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound);
        } else {
            return damageSource.isProjectile() ? ArmorModifierTypes.PROJECTILE_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound) : 0.0F;
        }
    }

    public static boolean isDamageEnchantmentCompatibleWith(Enchantment thisEnchantment, Enchantment enchantment, Item item) {
        return item.getHardestMetalMaterial() == Materials.vibranium ? enchantment != thisEnchantment : !(enchantment instanceof EnchantmentDamage);
    }
}
