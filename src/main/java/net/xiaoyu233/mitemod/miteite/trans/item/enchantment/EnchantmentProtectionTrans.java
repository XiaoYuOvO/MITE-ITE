package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.CreativeTabs;
import net.minecraft.EnchantmentProtection;
import net.minecraft.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EnchantmentProtection.class)
public class EnchantmentProtectionTrans extends EnchantmentTrans {

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
