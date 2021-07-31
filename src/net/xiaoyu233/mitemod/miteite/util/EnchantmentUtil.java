package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;

import java.util.*;

public class EnchantmentUtil {
    private static void removeEnchantmentsFromMapThatConflict(Map<Integer,EnchantmentInstance> map, ArrayList<EnchantmentInstance> enchantments, Item item) {
        for (EnchantmentInstance o : enchantments) {
            Enchantment enchantment = o.enchantmentobj;
            Iterator<? extends Integer> iterator = map.keySet().iterator();

            while (iterator.hasNext()) {
                int id = iterator.next();
                if (enchantment instanceof EnchantmentWeaponDamage) {
                    if (!((EnchantmentWeaponDamage) enchantment).isCompatibleWith(Enchantment.get(id), item)) {
                        iterator.remove();
                    }
                } else {
                    if (!enchantment.canApplyTogether(Enchantment.get(id))) {
                        iterator.remove();
                    }
                }
            }
        }

    }

    public static List<EnchantmentInstance> buildEnchantmentList(Random random, ItemStack item_stack, int enchantment_levels,int maxTotalLevel,int maxEnchantmentNum) {
        Item item = item_stack.getItem();
        int enchantability = item.getItemEnchantability();
        int maxSize = item.getHardestMetalMaterial() == Materials.vibranium ? 3 : 2;
        if (enchantability <= 0) {
            return null;
        } else {
            float randomness = 1.0F + (random.nextFloat() - 0.75F) * 0.5F;
            int adjusted_enchantment_levels = (int)((float)enchantment_levels * randomness);
            if (adjusted_enchantment_levels < 1) {
                adjusted_enchantment_levels = 1;
            }

            ArrayList<EnchantmentInstance> enchantments_for_item = new ArrayList<>();
            int totalLevels = 0;
            do {
                Map<Integer, EnchantmentInstance> all_possible_enchantments = mapEnchantmentData(adjusted_enchantment_levels, item_stack);
                if (all_possible_enchantments == null) {
                    break;
                }

                removeEnchantmentsFromMapThatConflict(all_possible_enchantments, enchantments_for_item, item);
                if (all_possible_enchantments.isEmpty()) {
                    break;
                }

                EnchantmentInstance enchantment_data = (EnchantmentInstance) WeightedRandom.getRandomItem(random, all_possible_enchantments.values());
                if (enchantment_data == null) {
                    break;
                }

                Enchantment enchantment = enchantment_data.enchantmentobj;

                if (!enchantment.enchantIndividually()){
                    if (enchantments_for_item.size() < 2 && all_possible_enchantments.size() > 1 && enchantment.hasLevels() && random.nextInt(2) == 0) {
                        enchantment_data.enchantmentLevel = random.nextInt(enchantment_data.enchantmentLevel) + 1;
                    }
                    enchantments_for_item.add(enchantment_data);
                    totalLevels += enchantment_data.enchantmentLevel;
                    adjusted_enchantment_levels -= enchantment.hasLevels() ? enchantment.getMinEnchantmentLevelsCost(enchantment_data.enchantmentLevel) : enchantment.getMinEnchantmentLevelsCost();
                    adjusted_enchantment_levels -= 5;
                }
            } while(adjusted_enchantment_levels >= 5 && totalLevels <= maxEnchantmentNum && enchantments_for_item.size() <= maxSize);

            List<EnchantmentInstance> enchantments_for_item_shuffled = new ArrayList<>();
            int n = enchantments_for_item.size();

            while(n > 0) {
                int index = random.nextInt(enchantments_for_item.size());
                EnchantmentInstance enchantment_data = enchantments_for_item.get(index);
                if (enchantment_data != null) {
                    enchantments_for_item_shuffled.add(enchantment_data);
                    enchantments_for_item.set(index, null);
                    --n;
                }
            }

            //build individualEnchantments
            List<Enchantment> added = new ArrayList<>();
            for (Enchantment individualEnchantment : Enchantments.individualEnchantments) {
                if (individualEnchantment.canEnchantItem(item)) {
                    int level = 0;
                    while (level < individualEnchantment.getNumLevels()){
                        if (individualEnchantment.enchantIndividualChance(enchantment_levels) < random.nextFloat()){
                            level++;
                        }
                    }
                    if (level > 0 && !added.contains(individualEnchantment)){
                        enchantments_for_item_shuffled.add(new EnchantmentInstance(individualEnchantment,level));
                        added.add(individualEnchantment);
                    }
                }
            }

            if (enchantments_for_item_shuffled.size() > maxEnchantmentNum){
                enchantments_for_item_shuffled = enchantments_for_item_shuffled.subList(0,maxEnchantmentNum - 1);
            }
            return enchantments_for_item_shuffled.size() == 0 ? null : enchantments_for_item_shuffled;
        }
    }

    private static Map<Integer, EnchantmentInstance> mapEnchantmentData(int enchantment_levels, ItemStack item_stack) {
        Item item = item_stack.getItem();
        boolean is_vibranium = item.getHardestMetalMaterial() == Materials.vibranium;
        boolean is_book = item == Item.book;
        HashMap<Integer, EnchantmentInstance> map = new HashMap<>();

        for(int i = 0; i < Enchantment.enchantmentsList.length; ++i) {
            Enchantment enchantment = Enchantment.get(i);
            if (enchantment != null && (is_book || enchantment.canEnchantItem(item))) {
                if (enchantment.hasLevels()) {
                    for(int level =
                        is_vibranium ? enchantment.getNumLevelsForVibranium() : enchantment.getNumLevels(); level > 0; --level) {
                        if (enchantment.getMinEnchantmentLevelsCost(level) <= enchantment_levels) {
                            map.put(enchantment.effectId, new EnchantmentInstance(enchantment, level));
                            break;
                        }
                    }
                } else if (enchantment.getMinEnchantmentLevelsCost() <= enchantment_levels) {
                    map.put(enchantment.effectId, new EnchantmentInstance(enchantment, 1));
                }
            }
        }

        return map.size() == 0 ? null : map;
    }
}
