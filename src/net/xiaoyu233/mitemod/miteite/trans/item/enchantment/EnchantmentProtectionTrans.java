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

    @Marker
    public boolean canReduceDamage(DamageSource damage_source) {
        return false;
    }

    public boolean canReduceDamageP(DamageSource damage_source){
        return this.canReduceDamage(damage_source);
    }

    public static float getTotalProtectionOfEnchantments(ItemStack[] armors, DamageSource damage_source, EntityLiving owner) {
        EnchantmentProtection[] protection_enchantments = new EnchantmentProtection[]{Enchantment.e, Enchantment.f, Enchantment.g, Enchantment.h};
        int max_enchantment_level = protection_enchantments[1].getNumLevels();
        float total_protection = 0.0F;

        for (EnchantmentProtection protection_enchantment : protection_enchantments) {
            if (protection_enchantment.getNumLevels() != max_enchantment_level) {
                Minecraft.setErrorMessage("getTotalDefenseOfEnchantments: " + protection_enchantment + " has a different number of levels!");
            }

            if (protection_enchantment.canReduceDamageP(damage_source)) {
                for (ItemStack item_stack : armors) {
                    if (item_stack != null) {
                        float enchantment_level_fraction = item_stack.getEnchantmentLevelFraction(protection_enchantment);
                        if (enchantment_level_fraction > 0.0F) {
                            Item item = item_stack.b();
                            if (item instanceof ItemArmor) {
                                ItemArmor armor = item_stack.getItemAsArmor();
                                if (protection_enchantment == Enchantment.f) {
                                    return 15.0F * enchantment_level_fraction * armor.getDamageFactor(item_stack, owner);
                                }

                                total_protection += armor.getProtectionAfterDamageFactor(item_stack, owner) * enchantment_level_fraction;
                            } else {
                                Minecraft.setErrorMessage("getTotalProtectionOfEnchantments: don't know how to handle enchanted items that aren't armor");
                            }
                        }
                    }
                }
            }
        }

        return total_protection;
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
