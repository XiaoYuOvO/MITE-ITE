package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityPigZombie.class)
public class EntityZombiePigmanTrans extends EntityZombie {
    @Link
    protected static IAttribute bp;
    @Marker
    public EntityZombiePigmanTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.setEntityAttribute(GenericAttributes.a, 30.0D + day / 16D);
        this.setEntityAttribute(GenericAttributes.b, 40.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.23F);
        this.setEntityAttribute(GenericAttributes.e, 5.0D + day / 48D);
        this.setEntityAttribute(EntityZombie.bp, super.ab.nextDouble() * (double)0.1F);
    }
}
