package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityBlaze;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityBlaze.class)
public class EntityBlazeTrans extends EntityMonster {
    @Marker
    public EntityBlazeTrans(World par1World) {
        super(par1World);
    }

    @Override
    protected void az() {
        super.az();
        this.a(GenericAttributes.e).a(6.0D);
        this.a(GenericAttributes.a).a(40.0D);
    }
}
