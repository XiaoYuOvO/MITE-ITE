package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMob {
   public EntityEndermanTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(100.0 + (double)day / 20.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(30.0 + (double)day / 20.0);
   }

   public EntityDamageResult attackEntityAsMob(Entity target) {
      this.worldObj.setEntityState(this, EnumEntityState.golem_throw);
      EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), (float)this.getEntityAttributeValue(SharedMonsterAttributes.attackDamage)));
      if (result != null && result.entityWasKnockedBack()) {
         target.motionY += 0.6;
      }

      return result;
   }
}