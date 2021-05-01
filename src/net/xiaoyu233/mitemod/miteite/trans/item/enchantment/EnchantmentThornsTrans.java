package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.CreativeModeTab;
import net.minecraft.EnchantmentThorns;
import net.minecraft.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EnchantmentThorns.class)
public class EnchantmentThornsTrans extends EnchantmentTrans{
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
   public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
      return false;
   }
}
