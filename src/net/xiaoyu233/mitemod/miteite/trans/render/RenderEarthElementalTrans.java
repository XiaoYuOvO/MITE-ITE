package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEarthElemental.class)
public class RenderEarthElementalTrans extends bgu {
   public RenderEarthElementalTrans() {
      super(new ModelInvisibleStalker(), 0.5F);
   }

   protected bjo getTextures(EntityEarthElemental earth_elemental) {
      int type = earth_elemental.getType();
      if (type == EntityEarthElemental.CLAY_NORMAL) {
         return super.textures[8];
      } else {
         return type == EntityEarthElemental.CLAY_HARDENED ? super.textures[10] : super.textures[type == EntityEarthElemental.STONE_NORMAL ? 0 : (type == EntityEarthElemental.STONE_MAGMA ? 1 : (type == EntityEarthElemental.OBSIDIAN_NORMAL ? 2 : (type == EntityEarthElemental.OBSIDIAN_MAGMA ? 3 : (type == EntityEarthElemental.NETHERRACK_NORMAL ? 4 : (type == EntityEarthElemental.NETHERRACK_MAGMA ? 5 : (type == EntityEarthElemental.END_STONE_NORMAL ? 6 : (type == 5 ? 10 : 7)))))))];
      }
   }

   @Inject(method = "setTextures()V",at = @At("RETURN"))
   private void injectTexture(CallbackInfo c){
      this.setTexture(10, "plank", false);
   }

   @Intrinsic
   @Shadow
   private void setTexture(int index, String name, boolean magma) {
   }

   @Shadow
   protected void setTextures() {
   }
}
