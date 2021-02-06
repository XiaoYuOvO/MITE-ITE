package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import org.lwjgl.opengl.GL11;

@Transform(bht.class)
public class RenderIronGolemTrans extends bhe {
    @Marker
    public RenderIronGolemTrans(bbo par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    protected void a(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4) {
        if (par1EntityIronGolem.v().a(par1EntityIronGolem.DATA_OBJ_IS_BOOSTED) != 0){
            GL11.glScalef(1.3571428571428571428571428571429f,1.2068965517241379310344827586207f,1.3571428571428571428571428571429f);
        }
        super.a(par1EntityIronGolem, par2, par3, par4);
        if ((double)par1EntityIronGolem.aG >= 0.01D) {
            float var5 = 13.0F;
            float var6 = par1EntityIronGolem.aH - par1EntityIronGolem.aG * (1.0F - par4) + 6.0F;
            float var7 = (Math.abs(var6 % var5 - var5 * 0.5F) - var5 * 0.25F) / (var5 * 0.25F);
            GL11.glRotatef(6.5F * var7, 0.0F, 0.0F, 1.0F);
        }

    }

    @Override
    @Marker
    protected void setTextures() {

    }

    @Override
    @Marker
    protected bjo a(Entity entity) {
        return null;
    }
}
