package net.xiaoyu233.mitemod.miteite.trans.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldServer.class)
public abstract class WorldServerTrans extends World {
   @Unique
   private boolean pushTimeNextTick;

   public WorldServerTrans(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent, long world_creation_time, long total_world_time) {
      super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
   }
   @Inject(method = "getSuitableCreature", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", shift = At.Shift.AFTER), cancellable = true)
   private void injectGhastOverworldSpawnDay(EnumCreatureType creature_type, int x, int y, int z, CallbackInfoReturnable<Class<?>> cir){
      if (!this.isBloodMoon(true) || (this.isOverworld() && this.getDayOfOverworld() < (Configs.GameMechanics.MobSpawning.GHAST_SPAWN_LIMIT_DAY.get()))){
         cir.setReturnValue(null);
      }
   }

   @ModifyReturnValue(method = "getSuitableCreature", at = @At(value = "RETURN", ordinal = 2))
   private Class<?> modifyReturnForMobSpawnRestriction(Class<?> original){
      boolean is_blood_moon_up = this.isBloodMoon(false);
       if (original == EntityGiantZombie.class) {
          return is_blood_moon_up ? original : null;
      }else if (original == EntityAncientBoneLord.class){
         return is_blood_moon_up ? original : null;
      }
       return original;
   }

   @WrapWithCondition(method = "tick",at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldServer;advanceTotalWorldTime(J)V"))
   private boolean wrapAdvanceWorldTime(WorldServer instance, long l){
      if (Configs.GameMechanics.FIRST_DAY_LONGER_DAY_TIME.get() && this.getDayOfOverworld() == 1 && this.getTotalWorldTime() < 12000L) {
         if (this.pushTimeNextTick) {
            this.pushTimeNextTick = false;
            return true;
         } else {
            this.pushTimeNextTick = true;
            return false;
         }
      }
      return true;
   }

}
