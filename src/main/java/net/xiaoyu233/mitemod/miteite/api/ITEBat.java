package net.xiaoyu233.mitemod.miteite.api;

import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;

public interface ITEBat {
    default void setSpawnedByWitch(boolean spawnedByWitch, EntityWanderingWitch witch){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
