package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.Enchantment;
import net.xiaoyu233.mitemod.miteite.api.ITEEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public class EnchantmentTrans implements ITEEnchantment{

   @Inject(method = "<clinit>",at = @At("RETURN"))
   private static void injectClinit(CallbackInfo callbackInfo){

   }
   @Redirect(method = "getMinEnchantmentLevelsCost(I)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/Enchantment;getNumLevels()I"))
   public int redirectGetVibraniumLevels(Enchantment instance) {
      return instance.getNumLevelsForVibranium();
   }

   @Unique
   public float enchantIndividualChance(int enchantmentLevel){
      return 0;
   }

   @Unique
   public boolean enchantIndividually(){
      return false;
   }

   @Shadow
   public int getNumLevels() {
      return 5;
   }

   @Unique
   public int getNumLevelsForVibranium() {
      return this.getNumLevels();
   }

}
