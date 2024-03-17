package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.Entity;

public interface ITEDamage {
    default Entity getResponsibleEntityP(){
        throw new IllegalStateException("Should be implemented by Mixin");
    };
}
