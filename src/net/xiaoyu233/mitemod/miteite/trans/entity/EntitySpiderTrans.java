package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityArachnid;
import net.minecraft.EntitySpider;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntitySpider.class)
public class EntitySpiderTrans extends EntityArachnid {

    @Marker
    public EntitySpiderTrans(World par1World, float scaling) {
        super(par1World, scaling);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 32,0) : 0;
        this.setEntityAttribute(GenericAttributes.a, this.getEntityAttributeValue(GenericAttributes.a)*1.5 + day / 28D);
        this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e)*1.5 + day / 90D);
        this.setEntityAttribute(GenericAttributes.b, 64d);
    }
}
