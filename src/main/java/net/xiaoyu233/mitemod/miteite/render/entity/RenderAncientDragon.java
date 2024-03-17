package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.Entity;
import net.minecraft.EntityLivingBase;
import net.minecraft.RenderLiving;
import net.minecraft.ResourceLocation;
import net.xiaoyu233.mitemod.miteite.entity.EntityAncientDragon;
import net.xiaoyu233.mitemod.miteite.render.entity.model.ModelAncientDragon;
import org.lwjgl.opengl.GL11;

public class RenderAncientDragon extends RenderLiving {
    public RenderAncientDragon() {
        super(new ModelAncientDragon(), 3.0F);
    }

    //TODO Check here
    protected void a(EntityAncientDragon par1EntityGiantZombie) {
        GL11.glScalef(13.5F, 13.5F, 13.5F);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.textures[0];
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.a((EntityAncientDragon)par1EntityLivingBase);
    }

    protected void setTextures() {
        this.setTexture(0, "textures/entity/ancient_dragon/main");
    }
}