package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;

public class EntityAncientDragon extends EntityMob {
    public EntityAncientDragon(World par1World) {
        super(par1World);
        this.setSize(6.0F, 10.0F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(SharedMonsterAttributes.followRange, 16.0);
        this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 600.0);
        this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.23F);
        this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 15.0);
    }
}
