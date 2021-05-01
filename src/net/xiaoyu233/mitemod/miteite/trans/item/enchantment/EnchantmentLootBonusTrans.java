package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentLootBonus.class)
public class EnchantmentLootBonusTrans extends Enchantment {
   protected EnchantmentLootBonusTrans(int id, yq rarity, int difficulty) {
      super(id, rarity, difficulty);
   }

   @Shadow
   public boolean canEnchantItem(Item item) {
      return false;
   }

   @Shadow
   public String getNameSuffix() {
      return null;
   }

   public int getNumLevelsForVibranium() {
      return 5;
   }

   @Shadow
   public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
      return false;
   }
}
