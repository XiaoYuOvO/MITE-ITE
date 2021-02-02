package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.Damage;
import net.minecraft.DamageSource;
import net.minecraft.Entity;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(Damage.class)
public class DamageTrans {
    @Link
    private DamageSource source;
    public Entity getResponsibleEntityP() {
        return this.source.getResponsibleEntity();
    }
}
