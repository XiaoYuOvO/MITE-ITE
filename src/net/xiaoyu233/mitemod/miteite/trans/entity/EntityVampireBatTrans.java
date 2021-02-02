package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

public class EntityVampireBatTrans extends EntityBat{
    @Link
    private int attack_cooldown;
    @Link
    private int feed_cooldown;
    @Marker
    public EntityVampireBatTrans(World par1World) {
        super(par1World);
    }

    protected void n(Entity entity) {
        super.n(entity);
        if (this.attack_cooldown <= 0 && entity == this.m()) {
            if (this.E.c().scaleXZ(0.5D).b(entity.E)) {
                EntityDamageResult result = EntityMonster.attackEntityAsMob(this, entity);
                if (result != null && result.entityLostHealth()) {
                    this.heal(result.getAmountOfHealthLost(), EnumEntityFX.vampiric_gain);
                    if (this.ab.nextBoolean() && MITEITEMod.CONFIG.get(Config.ConfigEntry.BAT_POISON_ATTACK)){
                        result.entity.getAsEntityLivingBase().c(new MobEffect(MobEffectList.u.H, 480, 0));
                    }
                    if (entity instanceof EntityOcelot) {
                        EntityOcelot ocelot = (EntityOcelot)entity;
                        if (ocelot.aN() > 0.0F && ocelot.getTarget() == null) {
                            ocelot.setTarget(this);
                        }
                    }

                    if (!(dyCast(this) instanceof EntityGiantVampireBat) && this.aN() >= this.aT()) {
                        this.feed_cooldown = 1200;
                    }
                }
            }

            this.attack_cooldown = 20;
        }

    }
}
