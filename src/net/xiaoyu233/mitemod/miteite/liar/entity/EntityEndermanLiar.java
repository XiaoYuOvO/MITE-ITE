package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.EntityEnderman;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityEnderman.class)
public class EntityEndermanLiar extends EntityMonster {
    @Stealing
    public EntityEndermanLiar(World par1World) {
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
