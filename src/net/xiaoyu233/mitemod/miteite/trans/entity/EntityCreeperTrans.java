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
    @Marker
    public EntityCreeperTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 32,0) : 0;
        br = day >= 128 ? (45 + (day-128) / 8) : 45;
        this.a(GenericAttributes.d).a(0.25D);
        this.setEntityAttribute(GenericAttributes.a,16D + (day/16D));
    }
}
