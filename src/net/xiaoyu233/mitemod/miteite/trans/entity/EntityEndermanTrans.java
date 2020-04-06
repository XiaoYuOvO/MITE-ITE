package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityEnderman;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMonster {
    @Marker
    public EntityEndermanTrans(World par1World) {
        super(par1World);
    }

    @Override
    protected void az() {
        super.az();
        this.a(GenericAttributes.a).a(80.0D);
        this.a(GenericAttributes.d).a(0.3F);
        this.a(GenericAttributes.e).a(20.0D);
    }
}
