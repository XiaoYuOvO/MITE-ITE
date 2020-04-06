package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.Entity;
import net.minecraft.EntityLiving;
import net.minecraft.bhe;
import net.minecraft.bjo;
import net.xiaoyu233.mitemod.miteite.entity.EntityAncientDragon;
import net.xiaoyu233.mitemod.miteite.render.entity.model.ModelAncientDragon;
import org.lwjgl.opengl.GL11;

public class RenderAncientDragon extends bhe {

    public RenderAncientDragon() {
        super(new ModelAncientDragon(), 3.0f);
    }

    protected void a(EntityAncientDragon par1EntityGiantZombie) {
        GL11.glScalef(13.5f, 13.5f, 13.5f);
    }

    @Override
    protected bjo a(Entity entity) {
        return this.textures[0];
    }

    protected void a(EntityLiving par1EntityLivingBase, float par2) {
        this.a((EntityAncientDragon)par1EntityLivingBase);
    }

    @Override
    protected void setTextures() {
        this.setTexture(0,"textures/entity/ancient_dragon/main");
    }
}
