package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityMagmaCube.class)
public abstract class EntityMagmaCubeTrans extends EntityCubic {
    public EntityMagmaCubeTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
    protected void applyEntityAttributes(CallbackInfo ci) {
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.20000000298023224D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(this.getMaxHealth() + 30D);
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        if (target != null && target.isEntityAlive() && rand.nextFloat() < Configs.Entities.NETHER_MOD_ATTACK_FIRE_CHANCE.get()){
            target.setFire(100);
        }
        return super.attackEntityAsMob(target);
    }
}
