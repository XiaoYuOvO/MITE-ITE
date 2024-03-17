package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.Damage;
import net.minecraft.DamageSource;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityLivingBase;

public interface ITELivingBase {
    default boolean canBeTargetTo(EntityLivingBase from){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default void checkForAfterDamage(Damage damage, EntityDamageResult result){
        throw new IllegalStateException("Should be implemented in mixin");
    };
    default void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result){
        throw new IllegalStateException("Should be implemented in mixin");
    };
}
