package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.Minecraft;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public class EnchantmentTrans {
   @Shadow
   @Final
   public static Enchantment[] enchantmentsBookList;
   @Shadow
   @Final
   public static Enchantment[] enchantmentsList;
   @Shadow
   private int difficulty;

   @Inject(method = "<clinit>",at = @At("RETURN"))
   private static void injectClinit(CallbackInfo callbackInfo){
      Enchantments.registerEnchantments();
      for (Enchantment enchantment : enchantmentsList) {
         if (enchantment != null && !Enchantments.individualEnchantments.contains(enchantment)){
            if (enchantment.enchantIndividually()){
               Enchantments.individualEnchantments.add(enchantment);
            }
         }
      }
   }
   @Overwrite
   public int getMinEnchantmentLevelsCost(int level) {
      if (level < 1) {
         Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level < 1 for " + this);
         return 0;
      } else {
         if (!this.hasLevels()) {
            if (level != 3) {
               Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level must be 3 for " + this);
               return 0;
            }
         } else if (level > this.getNumLevelsForVibranium()) {
            Minecraft.setErrorMessage("getMinEnchantmentLevelsCost: level too high for " + this);
            return 0;
         }

         return Math.max(this.difficulty - 10, 0) + this.difficulty * (level - 1) + 1;
      }
   }

   public float enchantIndividualChance(int enchantmentLevel){
      return 0;
   }

   public boolean enchantIndividually(){
      return false;
   }

   @Shadow
   public int getNumLevels() {
      return 5;
   }

   public int getNumLevelsForVibranium() {
      return this.getNumLevels();
   }

   @Shadow
   private boolean hasLevels() {
      return false;
   }
}
