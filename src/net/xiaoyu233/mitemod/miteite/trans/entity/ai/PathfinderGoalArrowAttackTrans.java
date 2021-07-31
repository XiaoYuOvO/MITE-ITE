package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.EntityInsentient;
import net.minecraft.PathfinderGoalArrowAttack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PathfinderGoalArrowAttack.class)
public class PathfinderGoalArrowAttackTrans {
    @Shadow @Final private EntityInsentient entityHost;

    public EntityInsentient getEntityHost() {
        return entityHost;
    }
}
