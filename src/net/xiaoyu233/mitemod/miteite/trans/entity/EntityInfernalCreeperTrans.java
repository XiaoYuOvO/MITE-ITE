package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.DamageSource;
import net.minecraft.EntityCreeper;
import net.minecraft.EntityInfernalCreeper;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityInfernalCreeper.class)
public class EntityInfernalCreeperTrans extends EntityCreeper {
    public EntityInfernalCreeperTrans(World world) {
        super(world);
        this.a(this.O * getScale(), this.P * getScale());
        if(MITEITEMod.CONFIG.get(Config.ConfigEntry.INFERNAL_CREEPER_BOOST)){
            this.bs *= 3.0F;
            int day = this.getWorld() != null ? this.getWorld().getDayOfWorld() : 0;
            this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)( day * 0.3),20) );
        }
    }

    public float getNaturalDefense(DamageSource damage_source) {
        if(MITEITEMod.CONFIG.get(Config.ConfigEntry.INFERNAL_CREEPER_BOOST)) {
            return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : 2.0F) + (this.getWorld() != null ? this.getWorld().getDayOfWorld() * 0.15f : 0);
        }else {
            return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : 2.0F);
        }
    }
}
