package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.bhv;

public class RenderWanderingWitch extends bhv {
    protected void setTextures() {
        this.setTexture(0, "textures/entity/wandering_witch");
    }

    protected boolean forceGlowOverride(){
        return true;
    }
}
