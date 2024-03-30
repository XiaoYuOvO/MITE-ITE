package net.xiaoyu233.mitemod.miteite.api;

public interface ITECart {
    default boolean isInPortal(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
