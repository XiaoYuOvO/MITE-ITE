package net.xiaoyu233.mitemod.miteite.api;

public interface ITEMaterial {
    default int getMinHarvestLevel(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default float getDurability(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default String getName(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
}
