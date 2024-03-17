package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.RenderWitch;

public class RenderWanderingWitch extends RenderWitch {
    protected void setTextures() {
        this.setTexture(0, "textures/entity/wandering_witch");
    }

    protected boolean forceGlowOverride() {
        return true;
    }
}