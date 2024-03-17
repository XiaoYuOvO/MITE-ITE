package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEWitch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityWitch.class)
public class EntityWitchTrans extends EntityMob implements ITEWitch {
    @Shadow private EntityLivingBase summon_wolf_target;
    @Shadow private int summon_wolf_countdown;
    @Shadow private boolean has_summoned_wolves;
    @Shadow private int curse_random_seed;

    public EntityWitchTrans(World par1World) {
        super(par1World);
    }

    @Shadow private int summonWolves(){return 0;}

    @Unique
    public int summonWolvesITE(){
        return this.summonWolves();
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityWitch;summonWolves()I"))
    public int onLivingUpdate(EntityWitch instance) {
        return summonWolvesITE();
    }

    public EntityLivingBase getSummon_wolf_target() {
        return summon_wolf_target;
    }

    public void setSummon_wolf_target(EntityLivingBase summon_wolf_target) {
        this.summon_wolf_target = summon_wolf_target;
    }

    public int getSummon_wolf_countdown() {
        return summon_wolf_countdown;
    }

    public void setSummon_wolf_countdown(int summon_wolf_countdown) {
        this.summon_wolf_countdown = summon_wolf_countdown;
    }

    public boolean isHas_summoned_wolves() {
        return has_summoned_wolves;
    }

    public void setHas_summoned_wolves(boolean has_summoned_wolves) {
        this.has_summoned_wolves = has_summoned_wolves;
    }

    public int getCurse_random_seed() {
        return curse_random_seed;
    }
}
