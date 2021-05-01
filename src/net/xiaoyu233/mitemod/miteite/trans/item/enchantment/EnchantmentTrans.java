package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
