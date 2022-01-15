package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityCubic.class)
public abstract class EntityCubicTrans extends EntityInsentient {
    public EntityCubicTrans(World par1World) {
        super(par1World);
    }

    protected float getAttackDamageBonus(){
        return 0.0f;
    }

    @Shadow public abstract int getAttackStrengthMultiplierForType();

    @Shadow public abstract int getExperienceValue();

    @Redirect(method = "attackEntityAsMob",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/Entity;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
    private EntityDamageResult redirectAttackEntity(Entity caller, Damage damage){
        return caller.attackEntityFrom(damage.addAmount(this.getAttackDamageBonus()));
    }
}
