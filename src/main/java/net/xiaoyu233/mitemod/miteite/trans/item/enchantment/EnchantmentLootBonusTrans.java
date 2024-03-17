package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EnchantmentLootBonus.class)
public abstract class EnchantmentLootBonusTrans extends Enchantment {
   protected EnchantmentLootBonusTrans(int id, EnumRarity rarity, int difficulty) {
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

   @SoftOverride
   public int getNumLevelsForVibranium() {
      return 5;
   }

   @Shadow
   public boolean isOnCreativeTab(CreativeTabs creativeModeTab) {
      return false;
   }
}
