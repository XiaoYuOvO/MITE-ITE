package net.xiaoyu233.mitemod.miteite.api;

public interface ITEAnimal {
    default int getBreedExp(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
