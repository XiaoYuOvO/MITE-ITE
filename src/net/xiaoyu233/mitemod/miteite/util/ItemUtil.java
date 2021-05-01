package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.DamageSource;
import net.minecraft.EntityLiving;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;

public class ItemUtil {
    public static float getProtectionForDamage(ItemStack armor, DamageSource damageSource, EntityLiving owner) {
        if (damageSource.isFireDamage()) {
            return ArmorModifierTypes.FIRE_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound);
        } else if (damageSource.isExplosion()) {
            return ArmorModifierTypes.EXPLOSION_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound);
        } else {
            return damageSource.isProjectile() ? ArmorModifierTypes.PROJECTILE_PROTECTION_MODIFIER.getModifierValue(armor.stackTagCompound) : 0.0F;
        }
    }
}
