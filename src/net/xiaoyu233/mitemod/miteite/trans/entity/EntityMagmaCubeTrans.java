package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityMagmaCube.class)
public abstract class EntityMagmaCubeTrans extends EntityCubic {
    public EntityMagmaCubeTrans(World par1World) {
        super(par1World);
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.20000000298023224D);
        this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + 30D);
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        if (target != null && target.isEntityAlive() && rand.nextFloat() < Configs.Entities.NETHER_MOD_ATTACK_FIRE_CHANCE.get()){
            target.setFire(100);
        }
        return super.attackEntityAsMob(target);
    }
}
