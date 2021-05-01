package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(bht.class)
public class RenderIronGolemTrans extends bhe {

   public RenderIronGolemTrans(bbo par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   @Overwrite
   protected void a(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4) {
      if (par1EntityIronGolem.getDataWatcher().getWatchableObjectByte(par1EntityIronGolem.DATA_OBJ_IS_BOOSTED) != 0) {
         GL11.glScalef(1.3571428F, 1.2068965F, 1.3571428F);
      }

      super.a(par1EntityIronGolem, par2, par3, par4);
      if ((double)par1EntityIronGolem.limbSwingAmount >= 0.01D) {
         float var5 = 13.0F;
         float var6 = par1EntityIronGolem.limbSwing - par1EntityIronGolem.limbSwingAmount * (1.0F - par4) + 6.0F;
         float var7 = (Math.abs(var6 % var5 - var5 * 0.5F) - var5 * 0.25F) / (var5 * 0.25F);
         GL11.glRotatef(6.5F * var7, 0.0F, 0.0F, 1.0F);
      }

   }

   @Shadow
   protected bjo a(Entity entity) {
      return null;
   }

   @Shadow
   protected void setTextures() {
   }
}
