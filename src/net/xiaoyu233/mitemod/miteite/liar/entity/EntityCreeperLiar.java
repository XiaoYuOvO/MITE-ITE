package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.EntityCreeper;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityCreeper.class)
public class EntityCreeperLiar extends EntityMonster {

    @Stealing
    public EntityCreeperLiar(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        this.a(GenericAttributes.d).a(0.25D);
        this.setEntityAttribute(GenericAttributes.a,20);
    }
}
