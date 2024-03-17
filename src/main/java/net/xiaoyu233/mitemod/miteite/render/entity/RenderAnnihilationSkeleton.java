package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.*;

public class RenderAnnihilationSkeleton extends RenderBiped {
    public RenderAnnihilationSkeleton() {
        super(new ModelSkeleton(), 0.5F);
    }

    protected boolean forceGlowOverride() {
        return true;
    }

    @Override
    protected void setTextures() {
        this.setTexture(0, "textures/entity/skeleton/annihilation_skeleton");
    }

    protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving) {
        return this.textures[0];
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.func_110856_a((EntitySkeleton)par1Entity);
    }
}
