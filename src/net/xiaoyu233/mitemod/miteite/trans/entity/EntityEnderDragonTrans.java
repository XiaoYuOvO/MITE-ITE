package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
   private int weaknessCountdown;

   public EntityEnderDragonTrans(World par1World) {
      super(par1World);
   }


   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(1250D);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if(par1NBTTagCompound.hasKey("WeaknessCountDown")){
         this.weaknessCountdown = par1NBTTagCompound.getInteger("WeaknessCountDown");
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("WeaknessCountDown",this.weaknessCountdown);
   }

   @Inject(method = "onLivingUpdate",at = @At("HEAD"))
   private void injectUpdate(CallbackInfo callback){
      if (this.onServer() && this.getHealth() < this.getMaxHealth() / 2){
         if (this.weaknessCountdown > 0){
            this.weaknessCountdown--;
         }else {
            for (Object playerEntity : this.worldObj.playerEntities) {
               ((EntityPlayer) playerEntity).addPotionEffect(new MobEffect(MobEffectList.weakness.id, 600,0));
            }
            this.weaknessCountdown = 1200;
         }
      }
   }

   @Overwrite
   private void attackEntitiesInList(List par1List) {
      for (Object value : par1List) {
         Entity var3 = (Entity) value;
         if (var3 instanceof EntityLiving) {
            if (var3 instanceof EntityPlayer && Configs.Entities.ENDER_DRAGON_ATTACK_SLOWNESS.get()){
               ((EntityPlayer) var3).addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, 15 * 20,0));
            }
            var3.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this),this.getHealth() < 150.0F ? 28F : 20.0F));
         }
      }

   }

   @Overwrite
   public EntityDamageResult attackEntityFromPart(EntityComplexPart par1EntityDragonPart, Damage damage) {
      if (par1EntityDragonPart != this.dragonPartHead && damage.getAmount() > 1.0F) {
         damage.scaleAmount(0.2F, 0.5F);
      }

      Entity responsibleEntityP = damage.getResponsibleEntityP();
      if (responsibleEntityP instanceof EntityPlayer && rand.nextFloat() < Configs.Entities.ENDER_DRAGON_HURT_WITHER_CHANCE.get()){
         ((EntityPlayer) responsibleEntityP).addPotionEffect(new MobEffect(MobEffectList.wither.id,20 * 10,0));
      }

      if (damage.isArrowDamage() && this.getHealth() < this.getMaxHealth() * Configs.Entities.ENDER_DRAGON_IMMUNE_TO_ARROW_HEALTH_PERCENT.get()){
         damage.setAmount(0);
      }
      if (this.getHealth() < 150.0F) {
         damage.scaleAmount(0.8f);
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

   @Redirect(method = "onLivingUpdate",
           slice = @Slice(
                   from = @At(value = "FIELD",target = "Lnet/minecraft/EntityLiving;hurtTime:I",opcode = Opcodes.GETFIELD),
                   to = @At(value = "INVOKE",target = "Lnet/minecraft/EntityEnderDragon;attackEntitiesInList(Ljava/util/List;)V")),
           at = @At(value = "INVOKE",target = "Lnet/minecraft/AxisAlignedBB;expand(DDD)Lnet/minecraft/AxisAlignedBB;"))
   private AxisAlignedBB redirectExpandAttackRange(AxisAlignedBB caller,double par1, double par3, double par5){
      return caller.expand(par1, par3, par5).expand(1.2d,1.2d,1.2d);
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
      if (this.getRNG().nextInt(2) == 0 && !super.worldObj.playerEntities.isEmpty() || this.getHealth() < 150.0F) {
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
