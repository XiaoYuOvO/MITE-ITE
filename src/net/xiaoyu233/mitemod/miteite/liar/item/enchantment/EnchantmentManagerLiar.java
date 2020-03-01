package net.xiaoyu233.mitemod.miteite.liar.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

import java.util.*;

@Deceive(EnchantmentManager.class)
public class EnchantmentManagerLiar {
    private static void removeEnchantmentsFromMapThatConflict(Map map, ArrayList enchantments,Item item) {
        for(int i = 0; i < enchantments.size(); ++i) {
            EnchantmentInstance enchantment_data = (EnchantmentInstance)enchantments.get(i);
            Enchantment enchantment = enchantment_data.b;
            Iterator iterator = map.keySet().iterator();

            while(iterator.hasNext()) {
                int id = (Integer)iterator.next();
                if (enchantment instanceof EnchantmentWeaponDamage ? !((EnchantmentWeaponDamage) enchantment).isCompatibleWith(Enchantment.get(id),item) :  !enchantment.a(Enchantment.get(id))) {
                    iterator.remove();
                }
            }
        }

    }
    @Stealing
    private static Map b(int enchantment_levels, ItemStack item_stack) {
        return null;
    }
    public static List b(Random random, ItemStack item_stack, int enchantment_levels) {
        Item item = item_stack.b();
        int enchantability = item.c();
        int maxSize = (item.getHardestMetalMaterial() == Materials.vibranium ? 3 : 2);
        if (enchantability <= 0) {
            return null;
        } else {
            float randomness = 1.0F + (random.nextFloat() - 0.5F) * 0.5F;
            int adjusted_enchantment_levels = (int)((float)enchantment_levels * randomness);
            if (adjusted_enchantment_levels < 1) {
                adjusted_enchantment_levels = 1;
            }

            ArrayList enchantments_for_item = new ArrayList();

            while(adjusted_enchantment_levels > 0) {
                Map all_possible_enchantments = b(adjusted_enchantment_levels, item_stack);
                if (all_possible_enchantments == null) {
                    break;
                }

                removeEnchantmentsFromMapThatConflict(all_possible_enchantments, enchantments_for_item,item);
                if (all_possible_enchantments.isEmpty()) {
                    break;
                }

                EnchantmentInstance enchantment_data = (EnchantmentInstance) WeightedRandom.a(random, all_possible_enchantments.values());
                if (enchantment_data == null) {
                    break;
                }

                Enchantment enchantment = enchantment_data.b;
                if (enchantments_for_item.size() < 2 && all_possible_enchantments.size() > 1 && enchantment.hasLevels() && random.nextInt(2) == 0) {
                    enchantment_data.c = random.nextInt(enchantment_data.c) + 1;
                }

                enchantments_for_item.add(enchantment_data);
                adjusted_enchantment_levels = adjusted_enchantment_levels - (enchantment.hasLevels() ? enchantment.getMinEnchantmentLevelsCost(enchantment_data.c) : enchantment.getMinEnchantmentLevelsCost());
                adjusted_enchantment_levels = adjusted_enchantment_levels - 5;
                if (adjusted_enchantment_levels < 5 || enchantments_for_item.size() > maxSize) {
                    break;
                }
            }

            ArrayList enchantments_for_item_shuffled = new ArrayList();
            int n = enchantments_for_item.size();

            while(n > 0) {
                int index = random.nextInt(enchantments_for_item.size());
                EnchantmentInstance enchantment_data = (EnchantmentInstance)enchantments_for_item.get(index);
                if (enchantment_data != null) {
                    enchantments_for_item_shuffled.add(enchantment_data);
                    enchantments_for_item.set(index, null);
                    --n;
                }
            }

            return enchantments_for_item_shuffled.size() == 0 ? null : enchantments_for_item_shuffled;
        }
    }
}
