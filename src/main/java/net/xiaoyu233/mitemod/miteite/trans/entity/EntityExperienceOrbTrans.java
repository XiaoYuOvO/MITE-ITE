package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityXPOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityXPOrb.class)
public class EntityExperienceOrbTrans {
   @Inject(method = "getXPSplit", at = @At("HEAD"), cancellable = true)
   private static void injectModifyMaxClamp(int par0, CallbackInfoReturnable<Integer> cir) {
      if (par0 >= 256000) {
         cir.setReturnValue(256000);
      } else if (par0 >= 128000) {
         cir.setReturnValue(128000);
      } else if (par0 >= 64000) {
         cir.setReturnValue(64000);
      } else if (par0 >= 32000) {
         cir.setReturnValue(32000);
      } else if (par0 >= 16000) {
         cir.setReturnValue(16000);
      } else if (par0 >= 8000) {
         cir.setReturnValue(8000);
      } else if (par0 >= 4000) {
         cir.setReturnValue(4000);
      }
   }
}
