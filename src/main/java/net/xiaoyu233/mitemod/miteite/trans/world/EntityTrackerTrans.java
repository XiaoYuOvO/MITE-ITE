package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityRideMarker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTracker.class)
public class EntityTrackerTrans {

   @Shadow
   public void addEntityToTracker(Entity par1Entity, int par2, int par3, boolean par4) {
   }

   @Inject(method = "addEntityToTracker(Lnet/minecraft/Entity;)V", at = @At("RETURN"))
   public void injectAddRideMarker(Entity par1Entity, CallbackInfo ci) {
      if (par1Entity instanceof EntityRideMarker) {
         this.addEntityToTracker(par1Entity, 80, 3, true);
      }
   }
}
