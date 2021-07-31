package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityVampireBat.class)
public class EntityVampireBatTrans extends EntityBat {
   @Shadow
   private int attack_cooldown;
   @Shadow
   private int feed_cooldown;
   private boolean spawnedByWitch;
   private EntityWanderingWitch spawnerWitch;

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound nbt,CallbackInfo callbackInfo){
      if (nbt.hasKey("SpawnedByWitch")){
         this.spawnedByWitch = nbt.getBoolean("SpawnedByWitch");
         Entity entityByID = this.worldObj.getAsWorldServer().getEntityByID(nbt.getInteger("WitchSpawner"));
         this.spawnerWitch = (EntityWanderingWitch) entityByID;
      }
   }

   @Override
   public void onDeath(DamageSource par1DamageSource) {
      super.onDeath(par1DamageSource);
      if (this.spawnerWitch != null && this.spawnerWitch.isEntityAlive()){
         this.spawnerWitch.onAllyBatsDeath();
      }
   }

   @Override
   public int getExperienceValue() {
      if (this.spawnedByWitch){
         return 0;
      }
      return super.getExperienceValue();
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound nbt, CallbackInfo callbackInfo){
      if (this.spawnedByWitch){
         nbt.setBoolean("SpawnedByWitch", true);
         nbt.setInteger("WitchSpawner",this.spawnerWitch.entityId);
      }
   }

   public void setSpawnedByWitch(boolean spawnedByWitch,EntityWanderingWitch witch) {
      this.spawnedByWitch = spawnedByWitch;
      this.spawnerWitch = witch;
   }

   @Override
   public void addPotionEffect(MobEffect par1PotionEffect) {
      if (!this.spawnedByWitch){
         super.addPotionEffect(par1PotionEffect);
      }
   }

   @Override
   public float getNaturalDefense(DamageSource damage_source) {
      if (spawnedByWitch){
         return damage_source.hasMagicAspect() && damage_source.isIndirect() ? Integer.MAX_VALUE : super.getNaturalDefense(damage_source);
      }
      return super.getNaturalDefense(damage_source);
   }

   public EntityVampireBatTrans(World par1World) {
      super(par1World);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "collideWithEntity",
           at = @At(value = "INVOKE",
               shift = At.Shift.AFTER,
               target = "Lnet/minecraft/EntityLiving;heal(FLnet/minecraft/EnumEntityFX;)V"))
   private void injectAttack(Entity targeEntity,CallbackInfo c,EntityDamageResult result){
      if (this.rand.nextBoolean() && (Configs.Entities.BAT_POISON_ATTACK.get())) {
         result.entity.getAsEntityLivingBase().addPotionEffect(new MobEffect(MobEffectList.poison.id, 480, 0));
      }
   }

//   @Override
//   protected void collideWithEntity(Entity entity) {
//      super.collideWithEntity(entity);
//      if (this.attack_cooldown <= 0 && entity == this.getAttackTarget()) {
//         if (this.boundingBox.copy().scaleXZ(0.5D).intersectsWith(entity.boundingBox)) {
//            EntityDamageResult result = EntityMonster.attackEntityAsMob(this, entity);
//            if (result != null && result.entityLostHealth()) {
//               this.heal(result.getAmountOfHealthLost(), EnumEntityFX.vampiric_gain);
//
//
//               if (entity instanceof EntityOcelot) {
//                  EntityOcelot ocelot = (EntityOcelot)entity;
//                  if (ocelot.getHealth() > 0.0F && ocelot.getTarget() == null) {
//                     ocelot.setTarget(this);
//                  }
//               }
//
//               if (!(ReflectHelper.dyCast(this) instanceof EntityGiantVampireBat) && this.aN() >= this.aT()) {
//                  this.feed_cooldown = 1200;
//               }
//            }
//         }
//
//         this.attack_cooldown = 20;
//      }
//
//   }
}
