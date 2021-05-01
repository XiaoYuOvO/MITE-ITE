package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(EntityEnderDragon.class)
public class EntityEnderDragonTrans extends EntityInsentient implements IComplex {
   @Shadow
   private EntityComplexPart dragonPartHead;
   @Shadow
   private boolean forceNewTarget;
   @Shadow
   private EntityEnderCrystal healingEnderCrystal;
   @Shadow
   private Entity target;
   @Shadow
   private double targetX;
   @Shadow
   private double targetY;
   @Shadow
   private double targetZ;

   public EntityEnderDragonTrans(World par1World) {
      super(par1World);
   }


   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(500.0D);
   }

   @Overwrite
   private void attackEntitiesInList(List par1List) {
      for (Object value : par1List) {
         Entity var3 = (Entity) value;
         if (var3 instanceof EntityLiving) {
            var3.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), 20.0F));
         }
      }

   }

   @Overwrite
   public EntityDamageResult attackEntityFromPart(EntityComplexPart par1EntityDragonPart, Damage damage) {
      if (par1EntityDragonPart != this.dragonPartHead && damage.getAmount() > 1.0F) {
         damage.scaleAmount(0.2F, 0.5F);
      }

      float var4 = super.rotationYaw * 3.1415927F / 180.0F;
      float var5 = MathHelper.sin(var4);
      float var6 = MathHelper.cos(var4);
      this.targetX = super.posX + (double)(var5 * 5.0F) + (double)((this.getRNG().nextFloat() - 0.5F) * 2.0F);
      this.targetY = super.posY + (double)(this.getRNG().nextFloat() * 3.0F) + 1.0D;
      this.targetZ = super.posZ - (double)(var6 * 5.0F) + (double)((this.getRNG().nextFloat() - 0.5F) * 2.0F);
      this.target = null;
      return !(damage.getSource().getResponsibleEntity() instanceof EntityPlayer) && !damage.isExplosion() ? null : this.func_82195_e(damage);
   }

   @Shadow
   public World func_82194_d() {
      return null;
   }

   @Shadow
   private EntityDamageResult func_82195_e(Damage damage) {
      return null;
   }

   @Overwrite
   private void setNewTarget() {
      this.forceNewTarget = false;
      if (this.getRNG().nextInt(2) == 0 && !super.worldObj.playerEntities.isEmpty() && this.getHealth() > 150.0F) {
         this.target = (Entity)super.worldObj.playerEntities.get(this.getRNG().nextInt(super.worldObj.playerEntities.size()));
      } else {
         boolean var1;
         do {
            this.targetX = 0.0D;
            this.targetY = 70.0F + this.getRNG().nextFloat() * 50.0F;
            this.targetZ = 0.0D;
            this.targetX += this.getRNG().nextFloat() * 120.0F - 60.0F;
            this.targetZ += this.getRNG().nextFloat() * 120.0F - 60.0F;
            double var2 = super.posX - this.targetX;
            double var4 = super.posY - this.targetY;
            double var6 = super.posZ - this.targetZ;
            var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
         } while(!var1);

         this.target = null;
      }

   }

   @Overwrite
   private void updateDragonEnderCrystal() {
      if (this.healingEnderCrystal != null) {
         if (this.healingEnderCrystal.isDead) {
            if (!super.worldObj.isRemote) {
               this.attackEntityFromPart(this.dragonPartHead, new Damage(DamageSource.setExplosionSource(null), 5.0F));
            }

            this.healingEnderCrystal = null;
         } else if (super.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.setHealth(super.getHealth() + 2.0F);
         }
      }

      if (this.getHealth() < 150.0F && super.ticksExisted % 30 == 0 && this.getHealth() < this.getMaxHealth()) {
         this.setHealth(super.getHealth() + 2.0F);
      }

      if (this.getRNG().nextInt(10) == 0) {
         float var1 = 48.0F;
         List var2 = super.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, super.boundingBox.expand(var1, var1, var1));
         EntityEnderCrystal var3 = null;
         double var4 = 1.7976931348623157E308D;

         for (Object value : var2) {
            EntityEnderCrystal var7 = (EntityEnderCrystal) value;
            double var8 = var7.getDistanceSqToEntity(this);
            if (var8 < var4) {
               var4 = var8;
               var3 = var7;
            }
         }

         this.healingEnderCrystal = var3;
      }

   }
}
