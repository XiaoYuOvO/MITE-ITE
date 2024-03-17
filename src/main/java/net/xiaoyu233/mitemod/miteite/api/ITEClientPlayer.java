package net.xiaoyu233.mitemod.miteite.api;

public interface ITEClientPlayer {
    default int getPhytonutrients(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };

    default void setPhytonutrients(int phytonutrients){
        throw new IllegalStateException("Should be implemented in Mixin");
    };

    default int getProtein(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };

    default void setProtein(int protein){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
