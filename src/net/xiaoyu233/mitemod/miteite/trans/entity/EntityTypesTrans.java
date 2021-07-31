package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.Entity;
import net.minecraft.EntityTypes;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;

@Mixin(EntityTypes.class)
public class EntityTypesTrans {
   @Shadow
   public static HashMap entityEggs;
   @Shadow
   public static List entries;

   @Shadow
   public static void addMapping(Class par0Class, String par1Str, int par2) {
   }

   @Shadow
   private static void addMapping(Class par0Class, String par1Str, int par2, int par3, int par4) {
   }

   @Shadow
   public static Entity createEntityByID(int par0, World par1World) {
      return null;
   }

   @Inject(method = "<clinit>",at = @At("RETURN"))
   private static void injectClinit(CallbackInfo ci){
      addMapping(EntityAncientDragon.class, "EntityTest", 201);
      addMapping(EntityRideMarker.class, "EntityMarker", 202);
      addMapping(EntityZombieLord.class, "EntityZombieLord", 203);
      addMapping(EntityZombiePigmanLord.class, "EntityZombiePigmanLord", 204);
      addMapping(EntityAnnihilationSkeleton.class, "EntityAnnihilationSkeleton", 205);
      addMapping(EntityWanderingWitch.class, "EntityWanderingWitch", 206);
   }
}
