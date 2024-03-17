package net.xiaoyu233.mitemod.miteite.trans.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SpawnerAnimals.class)
public class SpawnerCreatureTrans {
   @ModifyExpressionValue(method = "trySpawningHostileMobs", at = @At(value = "CONSTANT", args = "intValue=256"))
   private int modifyMaxHostileFraction(int constant, WorldServer world, boolean deep_only){
      return world.isBloodMoon(false) ? (int) Configs.GameMechanics.MobSpawning.BLOOD_MOON_MAX_HOSTILE_FRACTION.get().doubleValue() : constant;
   }

   @ModifyVariable(method = "trySpawningHostileMobs", name = "creature_limit", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/EnumCreatureType;getMaxNumberOfCreature()I", shift = At.Shift.AFTER))
   private int modifyCreatureLimit(int creatureLimit, WorldServer world, boolean deep_only){
      return (int) (creatureLimit + Configs.GameMechanics.MobSpawning.MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY.get() * (double)world.getDayOfOverworld());
   }
}
