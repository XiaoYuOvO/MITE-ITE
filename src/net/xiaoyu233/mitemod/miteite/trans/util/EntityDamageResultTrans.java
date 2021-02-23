package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EntityDamageResult;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityDamageResult.class)
public class EntityDamageResultTrans {
    @Link
    private boolean entity_was_destroyed;

    public void setEntity_was_destroyed(boolean entity_was_destroyed) {
        this.entity_was_destroyed = entity_was_destroyed;
    }
}
