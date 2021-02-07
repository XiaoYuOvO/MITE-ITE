package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityCreeper;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityCreeper.class)
public class EntityCreeperTrans extends EntityMonster {

    @Link
    private int br;
    @Link
    private float bs;

    @Marker
    public EntityCreeperTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 32,0) : 0;
        bs = day >= 128 ? (3 + 0.1f * (day-128) / 8) : 3f;
        this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)( day * 0.3),20) );
        this.a(GenericAttributes.d).a(0.25D);
        this.setEntityAttribute(GenericAttributes.a,18D + (day/14D));
        this.setEntityAttribute(GenericAttributes.b,64d);
    }

    public void setExplosionTime(int br){
        this.br = br;
    }

    public int getExplosionTime() {
        return br;
    }
}
