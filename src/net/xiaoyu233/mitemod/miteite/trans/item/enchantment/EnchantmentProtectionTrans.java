package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.CreativeModeTab;
import net.minecraft.DamageSource;
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
   public boolean canReduceDamage(DamageSource damage_source) {
      return false;
   }

   public boolean canReduceDamageP(DamageSource damage_source) {
      return this.canReduceDamage(damage_source);
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
