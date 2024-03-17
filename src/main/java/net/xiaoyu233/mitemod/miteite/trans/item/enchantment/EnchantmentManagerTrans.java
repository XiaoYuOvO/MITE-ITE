package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEEnchantment;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

import static net.xiaoyu233.mitemod.miteite.util.EnchantmentUtil.removeEnchantmentsFromMapThatConflict;

@Mixin(EnchantmentHelper.class)
public class EnchantmentManagerTrans {
   //TODO Make inject?
   /**
    * @author
    * @reason
    */
   @Overwrite
   public static List<EnchantmentData> buildEnchantmentList(Random random, ItemStack item_stack, int enchantment_levels) {
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

         ArrayList<EnchantmentData> enchantments_for_item = new ArrayList<>();

         do {
            Map<Integer, EnchantmentData> all_possible_enchantments = mapEnchantmentData(adjusted_enchantment_levels, item_stack);
            if (all_possible_enchantments == null) {
               break;
            }

            removeEnchantmentsFromMapThatConflict(all_possible_enchantments, enchantments_for_item, item);
            if (all_possible_enchantments.isEmpty()) {
               break;
            }

            EnchantmentData enchantment_data = (EnchantmentData)WeightedRandom.getRandomItem(random, all_possible_enchantments.values());
            if (enchantment_data == null) {
               break;
            }

            Enchantment enchantment = enchantment_data.enchantmentobj;

            if (!enchantment.enchantIndividually()){ //Individual wrapper
               if (enchantments_for_item.size() < 2 && all_possible_enchantments.size() > 1 && enchantment.hasLevels() && random.nextInt(2) == 0) {
                  enchantment_data.enchantmentLevel = random.nextInt(enchantment_data.enchantmentLevel) + 1;
               }
               enchantments_for_item.add(enchantment_data);
               adjusted_enchantment_levels -= enchantment.hasLevels() ? enchantment.getMinEnchantmentLevelsCost(enchantment_data.enchantmentLevel) : enchantment.getMinEnchantmentLevelsCost();
               adjusted_enchantment_levels -= 5;
            }
         } while(adjusted_enchantment_levels >= 5 && enchantments_for_item.size() <= maxSize);

         ArrayList<EnchantmentData> enchantments_for_item_shuffled = new ArrayList<>();
         int n = enchantments_for_item.size();

         while(n > 0) {
            int index = random.nextInt(enchantments_for_item.size());
            EnchantmentData enchantment_data = enchantments_for_item.get(index);
            if (enchantment_data != null) {
               enchantments_for_item_shuffled.add(enchantment_data);
               enchantments_for_item.set(index, null);
               --n;
            }
         }

         //Here
         List<Enchantment> added = new ArrayList<>();
         for (Enchantment individualEnchantment : Enchantments.individualEnchantments) {
            if (individualEnchantment.canEnchantItem(item)) {
               int level = 0;
               while (level < individualEnchantment.getNumLevels()){
                  if (individualEnchantment.enchantIndividualChance(enchantment_levels) > random.nextFloat()){
                     level++;
                  }else {
                     break;
                  }
               }
               if (level > 0 && !added.contains(individualEnchantment)){
                  enchantments_for_item_shuffled.add(new EnchantmentData(individualEnchantment,level));
                  added.add(individualEnchantment);
               }
            }
         }
         //End Here
         return enchantments_for_item_shuffled.size() == 0 ? null : enchantments_for_item_shuffled;
      }
   }

   /**
    * @author
    * @reason
    */
   @Overwrite
   private static Map<Integer, EnchantmentData> mapEnchantmentData(int enchantment_levels, ItemStack item_stack) {
      Item item = item_stack.getItem();
      boolean is_vibranium = item.getHardestMetalMaterial() == Materials.vibranium;
      boolean is_book = item == Item.book;
      HashMap<Integer, EnchantmentData> map = new HashMap<>();

      for(int i = 0; i < Enchantment.enchantmentsList.length; ++i) {
         Enchantment enchantment = Enchantment.get(i);
         if (enchantment != null && (is_book || enchantment.canEnchantItem(item)) && !enchantment.enchantIndividually()) {
            if (enchantment.hasLevels()) {
               for(int level =
                   is_vibranium ? enchantment.getNumLevelsForVibranium() : enchantment.getNumLevels(); level > 0; --level) {
                  if (enchantment.getMinEnchantmentLevelsCost(level) <= enchantment_levels) {
                     map.put(enchantment.effectId, new EnchantmentData(enchantment, level));
                     break;
                  }
               }
            } else if (enchantment.getMinEnchantmentLevelsCost() <= enchantment_levels) {
               map.put(enchantment.effectId, new EnchantmentData(enchantment, 1));
            }
         }
      }

      return map.size() == 0 ? null : map;
   }
}
