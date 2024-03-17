package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEIronGolem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderIronGolem.class)
public abstract class RenderIronGolemTrans extends RenderLiving {

   public RenderIronGolemTrans(ModelBase par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   @Inject(method = "rotateIronGolemCorpse", at = @At("HEAD"))
   private void injectApplyBoosted(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4, CallbackInfo ci){
      if (((ITEIronGolem) par1EntityIronGolem).isBoosted()) {
         GL11.glScalef(1.3571428F, 1.2068965F, 1.3571428F);
      }
   }
}
