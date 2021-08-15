package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
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
