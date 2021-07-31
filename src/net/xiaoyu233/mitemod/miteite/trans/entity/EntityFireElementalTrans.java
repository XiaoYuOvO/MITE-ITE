package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityFireElemental;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityFireElemental.class)
public class EntityFireElementalTrans extends EntityMonster {
    public EntityFireElementalTrans(World par1World) {
        super(par1World);
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(GenericAttributes.followRange).setAttribute(40.0D);
        if (this.worldObj.getProvider().isHellWorld){
            this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(this.getMaxHealth() + 30D);
        }
        this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.25D);
        this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(5.0D);
    }
}
