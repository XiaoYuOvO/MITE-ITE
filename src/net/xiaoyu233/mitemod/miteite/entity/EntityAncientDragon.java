package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;

public class EntityAncientDragon extends EntityMonster {
    public EntityAncientDragon(World par1World) {
        super(par1World);
        this.a(6.0f,10.0f);
    }

    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 16d);
        this.setEntityAttribute(GenericAttributes.a, 600.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.23000000417232513D);
        this.setEntityAttribute(GenericAttributes.e, 15.0D);
    }
}
