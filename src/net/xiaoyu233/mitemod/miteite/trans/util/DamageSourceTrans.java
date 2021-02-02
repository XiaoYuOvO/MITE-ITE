package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.DamageSource;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(DamageSource.class)
public class DamageSourceTrans {
    @Link
    private boolean has_fire_aspect;

    public DamageSource setFireAspectP(boolean has_fire_aspect) {
        this.has_fire_aspect = has_fire_aspect;
        return dyCast(this);
    }
}
