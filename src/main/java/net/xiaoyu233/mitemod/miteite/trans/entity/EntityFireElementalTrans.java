package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityFireElemental;
import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.reload.transform.util.WorldAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFireElemental.class)
public class EntityFireElementalTrans extends EntityMob {
    public EntityFireElementalTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
    protected void applyEntityAttributes(CallbackInfo ci) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0);
        if (((WorldAccessor) this.worldObj).getProvider().isHellWorld) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute((double)this.getMaxHealth() + 30.0);
        }
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(5.0);
    }
}
