package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.entity.EntityLargeFireballNB;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityGhast.class)
public class EntityGhastTrans extends EntityFlying implements IMob {
   @Shadow
   private int explosionStrength;

   public EntityGhastTrans(World world) {
      super(world);
   }

   @Inject(method = "applyEntityAttributes",at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      this.explosionStrength = 6;
      super.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(15.0D);
   }
   @Shadow
   public boolean canSpawnInShallowWater() {
      return false;
   }

   @Inject(method = "getCanSpawnHere", at = @At("HEAD"), cancellable = true)
   public void getCanSpawnHere(boolean perform_light_check, CallbackInfoReturnable<Boolean> cir) {
      if (this.getWorld().isOverworld()) {
         cir.setReturnValue(this.getWorld().isBloodMoon24HourPeriod()
                 && this.getWorld().getDayOfOverworld() >= Configs.GameMechanics.MobSpawning.GHAST_SPAWN_LIMIT_DAY.get()
                 && !this.getWorld().anySolidBlockIn(this.boundingBox.addCoord(0.0, 3.0, 0.0))
                 && this.worldObj.getEntitiesWithinAABB(EntityGhast.class, this.boundingBox.expand(64.0, 64.0, 64.0)).isEmpty());
      } else {
         cir.setReturnValue(super.getCanSpawnHere(perform_light_check));
      }
   }

   @ModifyConstant(method = "updateEntityActionState", constant = @Constant(intValue = 10))
   private int modifyAttackInterval(int constant){
      return 8;
   }

   @ModifyConstant(method = "updateEntityActionState", constant = @Constant(intValue = 20))
   private int modifyAttackInterval2(int constant){
      return 15;
   }

   @ModifyConstant(method = "updateEntityActionState", constant = @Constant(intValue = -40))
   private int modifyAttackInterval3(int constant){
      return -35;
   }

   @Redirect(method = "updateEntityActionState", at = @At(value = "NEW", target = "(Lnet/minecraft/World;Lnet/minecraft/EntityLivingBase;Lnet/minecraft/Vec3;F)Lnet/minecraft/EntityLargeFireball;"))
   private EntityLargeFireball redirectNoExplodingBall(World world, EntityLivingBase shooter, Vec3 target, float initial_distance){
      EntityLargeFireballNB entityLargeFireballNB = new EntityLargeFireballNB(world, shooter, target, initial_distance);
      entityLargeFireballNB.accelerationX *= 1.15d;
      entityLargeFireballNB.accelerationY *= 1.15d;
      entityLargeFireballNB.accelerationZ *= 1.15d;
      if (this.worldObj.isOverworld()) {
         this.explosionStrength = Math.round((float)this.explosionStrength * (Configs.Entities.GHAST_OVERWORLD_BOOST.get()).floatValue());
      }
      return entityLargeFireballNB;
   }
}
