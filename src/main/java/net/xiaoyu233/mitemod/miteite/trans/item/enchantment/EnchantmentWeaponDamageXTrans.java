package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentDamage.class)
public abstract class EnchantmentWeaponDamageXTrans extends Enchantment {
   protected EnchantmentWeaponDamageXTrans(int id, EnumRarity rarity, int difficulty) {
      super(id, rarity, difficulty);
   }

   @Inject(method = "canEnchantItem", at = @At("HEAD"), cancellable = true)
   public void injectVibraniumCheck(Item item, CallbackInfoReturnable<Boolean> cir) {
      if (this == Enchantment.smite && ((item.getHardestMetalMaterial() == Materials.vibranium) && (item.getClass() == ItemSword.class))){
         cir.setReturnValue(true);
      }
   }

   @Shadow
   public String getNameSuffix() {
      return null;
   }

   @SoftOverride
   public int getNumLevelsForVibranium() {
      return 7;
   }


   @Shadow
   public boolean isOnCreativeTab(CreativeTabs creativeModeTab) {
      return false;
   }
}
