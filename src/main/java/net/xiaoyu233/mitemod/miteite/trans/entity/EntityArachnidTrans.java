package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityArachnid;
import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArachnid.class)
public class EntityArachnidTrans extends EntityMob {
    public EntityArachnidTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
    protected void applyEntityAttributes(CallbackInfo ci) {
        this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 10.0D);
        this.setEntityAttribute(SharedMonsterAttributes.followRange, 28.0D);
        this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 1.0D);
        this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 6.0D);
    }
}
