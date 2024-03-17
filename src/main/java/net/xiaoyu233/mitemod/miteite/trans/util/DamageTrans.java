package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEDamage;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Damage.class)
public class DamageTrans implements ITEDamage {
   @Shadow
   private float amount;
   @Shadow
   private DamageSource source;

   @Inject(method = "applyTargetDefenseModifiers", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/EntityLivingBase;getTotalProtection(Lnet/minecraft/DamageSource;)F"))
   protected void applyTargetDefenseModifiers(EntityLivingBase target, EntityDamageResult result, CallbackInfoReturnable<Float> cir) {
      ItemStack[] wornItems = target.getWornItems();
      if (wornItems.length >= 4) {
         boolean allProtectionV = true;
         for (ItemStack armor : wornItems) {
            if (armor == null) {
               allProtectionV = false;
               break;
            } else {
               allProtectionV = armor.getEnchantmentLevel(Enchantment.protection) >= 5 && allProtectionV;
            }
         }
         if (allProtectionV) {
            this.amount = (float) ((double) this.amount - Math.min((double) this.amount * Configs.Item.Enchantment.ALL_PROTECTION_V_DEFENCE_FRACTION.get(), this.amount));
         }
      }
   }

   @Shadow
   private boolean bypassesMundaneArmor() {
      return false;
   }

   @Shadow
   private ItemStack getItemAttackedWith() {
      return null;
   }

   @Override
   public Entity getResponsibleEntityP() {
      return this.source.getResponsibleEntity();
   }

   @Shadow
   private DamageSource getSource() {
      return null;
   }

   @Shadow
   private boolean isAbsolute() {
      return false;
   }
}
