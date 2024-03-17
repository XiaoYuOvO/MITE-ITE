package net.xiaoyu233.mitemod.miteite.api;

public interface ITEStack {
    default int getForgingGrade(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default double getEnhanceFactor(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default int getEmergencyCooldown(){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default void setEmergencyCooldown(int cooldown){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default void setForgingGrade(int grade){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default void fixNBT(){
        throw new IllegalStateException("Should be implemented in mixin");
    };

    default void setIsArtifact(boolean isArtifact){
        throw new IllegalStateException("Should be implemented in mixin");
    };
}
