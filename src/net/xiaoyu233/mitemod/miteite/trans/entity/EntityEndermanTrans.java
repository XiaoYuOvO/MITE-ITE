package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMonster {
   public EntityEndermanTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(80.0D);
      this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.3D);
      this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(20.0D);
   }

   public EntityDamageResult attackEntityAsMob(Entity target) {
      this.worldObj.setEntityState(this, EnumEntityState.golem_throw);
      EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), (float)this.getEntityAttributeValue(GenericAttributes.attackDamage)));
      if (result != null && result.entityWasKnockedBack()) {
         target.motionY += 0.6D;
      }

      return result;
   }
}
