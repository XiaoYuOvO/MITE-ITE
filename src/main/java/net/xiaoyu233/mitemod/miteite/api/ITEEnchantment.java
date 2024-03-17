package net.xiaoyu233.mitemod.miteite.api;

public interface ITEEnchantment {
    default int getNumLevelsForVibranium(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default boolean enchantIndividually(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default float enchantIndividualChance(int enchantmentLevel){
        throw new IllegalStateException("Should be implemented in mixin");
    };
}
