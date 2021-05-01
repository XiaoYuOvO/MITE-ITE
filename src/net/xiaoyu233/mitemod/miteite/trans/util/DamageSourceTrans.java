package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.DamageSource;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DamageSource.class)
public class DamageSourceTrans {
   @Shadow
   private boolean has_fire_aspect;

   public DamageSource setFireAspectP(boolean has_fire_aspect) {
      this.has_fire_aspect = has_fire_aspect;
      return ReflectHelper.dyCast(this);
   }
}
