package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.EntityLiving;

public interface ITEArrowAttack {
    default EntityLiving getEntityHost(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
