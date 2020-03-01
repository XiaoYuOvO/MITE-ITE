package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.EntityArachnid;
import net.minecraft.EntitySpider;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntitySpider.class)
public class EntitySpiderLiar extends EntityArachnid {

    @Stealing
    public EntitySpiderLiar(World par1World, float scaling) {
        super(par1World, scaling);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 32,0) : 0;
        this.setEntityAttribute(GenericAttributes.a, this.getEntityAttributeValue(GenericAttributes.a)*1.5 + day / 32D);
        this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e)*1.5 + day / 96D);
    }
}
