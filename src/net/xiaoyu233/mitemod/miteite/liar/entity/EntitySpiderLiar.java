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
        this.setEntityAttribute(GenericAttributes.a, this.getEntityAttributeValue(GenericAttributes.a)*2);
        this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e)*2);
    }
}
