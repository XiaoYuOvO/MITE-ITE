package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMonster {
    @Marker
    public EntityEndermanTrans(World par1World) {
        super(par1World);
    }

    public EntityDamageResult attackEntityAsMob(Entity target) {
        this.q.setEntityState(this, EnumEntityState.golem_throw);
        EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.a(this), (float)( this.getEntityAttributeValue(GenericAttributes.e))));
        if (result != null) {
            if (result.entityWasKnockedBack()) {
                target.y += 0.6D;
            }
//            this.a("mob.irongolem.throw", 1.0F, 1.0F);
        }
        return result;
    }

    @Override
    protected void az() {
        super.az();
        this.a(GenericAttributes.a).a(80.0D);
        this.a(GenericAttributes.d).a(0.3F);
        this.a(GenericAttributes.e).a(20.0D);
    }
}
