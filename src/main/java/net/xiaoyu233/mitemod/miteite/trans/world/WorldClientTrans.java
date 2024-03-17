package net.xiaoyu233.mitemod.miteite.trans.world;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldClient.class)
public abstract class WorldClientTrans extends World {
   @Unique
   private boolean pushTimeNextTick;

   public WorldClientTrans(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent, long world_creation_time, long total_world_time) {
      super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
   }

   @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldClient;setTotalWorldTime(J)V"))
   private boolean wrapTimeStep(WorldClient instance, long l){
      if (!Configs.GameMechanics.FIRST_DAY_LONGER_DAY_TIME.get() || this.getDayOfOverworld() != 1 || (long)this.getTimeOfDay() >= 12000L) {
         return true;
      } else if (this.pushTimeNextTick) {
         this.pushTimeNextTick = false;
         return true;
      } else {
         this.pushTimeNextTick = true;
         return false;
      }
   }
}
