package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityInvisibleStalker;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityInvisibleStalker.class)
public class EntityInvisibleStalkerTrans extends EntityMonster {
    @Marker
    public EntityInvisibleStalkerTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        this.a(GenericAttributes.b).a(40.0D);
        this.a(GenericAttributes.d).a(0.23000000417232513D);
        this.a(GenericAttributes.e).a(6.0D);
    }
}
