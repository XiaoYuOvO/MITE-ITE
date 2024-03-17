package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntitySlime;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntitySlime.class)
public abstract class EntitySlimeTrans extends EntityCubicTrans {
    public EntitySlimeTrans(World par1World) {
        super(par1World);
    }

    @Override
    protected float getAttackDamageBonus() {
        return Configs.Entities.SLIME_ATTACK_DAMAGE_BONUS.get();
    }

    @ModifyConstant(method = "getAttackStrengthMultiplierForType", constant = @Constant(intValue = 1))
    public int getAttackStrengthMultiplierForType(int constant) {
        return Configs.Entities.SLIME_ATTACK_DAMAGE_MULTIPLIER.get();
    }
}
