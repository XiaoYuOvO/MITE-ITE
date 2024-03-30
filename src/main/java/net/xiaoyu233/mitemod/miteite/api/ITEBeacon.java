package net.xiaoyu233.mitemod.miteite.api;

public interface ITEBeacon {
    default void clearAllPlayersBoost(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
