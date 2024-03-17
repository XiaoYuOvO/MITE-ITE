package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.EntityAIArrowAttack;
import net.minecraft.EntityLiving;
import net.xiaoyu233.mitemod.miteite.api.ITEArrowAttack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAIArrowAttack.class)
public class PathfinderGoalArrowAttackTrans implements ITEArrowAttack {
    @Shadow @Final private EntityLiving entityHost;

    @Override
    public EntityLiving getEntityHost() {
        return entityHost;
    }
}
