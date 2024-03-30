package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.EntityLivingBase;

public interface ITEWitch {
    default EntityLivingBase getSummon_wolf_target(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void setSummon_wolf_target(EntityLivingBase summon_wolf_target){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default int getSummon_wolf_countdown(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void setSummon_wolf_countdown(int summon_wolf_countdown){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default boolean isHas_summoned_wolves(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void setHas_summoned_wolves(boolean has_summoned_wolves){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default int getCurse_random_seed(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default int summonWolvesITE(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
