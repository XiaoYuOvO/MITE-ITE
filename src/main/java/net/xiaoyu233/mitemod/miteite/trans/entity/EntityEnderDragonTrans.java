package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntityDragon.class)
public abstract class EntityEnderDragonTrans extends EntityLiving implements IEntityMultiPart {
   @Shadow
   private Entity target;
   @Unique
   private int weaknessCountdown;

   public EntityEnderDragonTrans(World par1World) {
      super(par1World);
   }


   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1250D);
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
               ((EntityPlayer) playerEntity).addPotionEffect(new PotionEffect(Potion.weakness.id, 600,0));
            }
            this.weaknessCountdown = 1200;
         }
      }
   }

   @Inject(method = "attackEntitiesInList", at = @At(value = "RETURN"))
   private void applyWeaknessOnAttack(List<Entity> par1List, CallbackInfo ci) {
      for (Entity entity : par1List) {
         if (entity instanceof EntityPlayer && Configs.Entities.ENDER_DRAGON_ATTACK_SLOWNESS.get()){
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 15 * 20,0));
         }
      }
   }

   @ModifyConstant(method = "attackEntitiesInList", constant = @Constant(floatValue = 15F))
   private float modifyCrushDamage(float damage){
      return this.getHealth() < 150.0F ? 28F : 20.0F;
   }

   @Inject(method = "attackEntityFromPart",
           at = @At(value = "FIELD",
                   target = "Lnet/minecraft/EntityDragon;rotationYaw:F",
                   shift = At.Shift.BEFORE),
           require = 1)
   private void injectAttackEffect(EntityDragonPart par1EntityDragonPart, Damage damage, CallbackInfoReturnable<EntityDamageResult> cir){
      Entity responsibleEntityP = damage.getResponsibleEntityP();
      if (responsibleEntityP instanceof EntityPlayer && this.rand.nextFloat() < Configs.Entities.ENDER_DRAGON_HURT_WITHER_CHANCE.get()) {
         ((EntityPlayer)responsibleEntityP).addPotionEffect(new PotionEffect(Potion.wither.id, 200, 0));
      }

      if (damage.isArrowDamage() && this.getHealth() < this.getMaxHealth() * Configs.Entities.ENDER_DRAGON_IMMUNE_TO_ARROW_HEALTH_PERCENT.get()) {
         damage.setAmount(0.0F);
      }

      if (this.getHealth() < 150.0F) {
         damage.scaleAmount(0.8F);
      }
   }

   @ModifyConstant(method = "attackEntityFromPart", constant = @Constant(floatValue = 0.25F))
   private float modifyDamageScale(float constant){
      return 0.2F;
   }

   @ModifyConstant(method = "attackEntityFromPart", constant = @Constant(floatValue = 1F))
   private float modifyDamageFloor(float constant){
      return 0.5F;
   }

   @Redirect(
           method = {"onLivingUpdate()V"},
           slice = @Slice(
                   from = @At(
                           value = "FIELD",
                           target = "Lnet/minecraft/EntityDragon;hurtTime:I",
                           opcode = 180
                   ),
                   to = @At(
                           value = "INVOKE",
                           target = "Lnet/minecraft/EntityDragon;attackEntitiesInList(Ljava/util/List;)V"
                   )
           ),
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/AxisAlignedBB;expand(DDD)Lnet/minecraft/AxisAlignedBB;"
           ),
           require = 3
   )
   private AxisAlignedBB redirectExpandAttackRange(AxisAlignedBB caller, double par1, double par3, double par5) {
      return caller.expand(par1, par3, par5).expand(1.2, 1.2, 1.2);
   }

   @Inject(method = "setNewTarget", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityDragon;forceNewTarget:Z", shift = At.Shift.AFTER), require = 1)
   private void injectSelectTarget(CallbackInfo ci) {
      if (this.getHealth() < 150.0F) {
         this.target = (Entity) super.worldObj.playerEntities.get(this.getRNG()
                 .nextInt(super.worldObj.playerEntities.size()));
      }
   }

   @ModifyConstant(method = "updateDragonEnderCrystal", constant = @Constant(floatValue = 32F))
   private float modifyRegenerationRadius(float org){
      return 48F;
   }

   @ModifyConstant(method = "updateDragonEnderCrystal", constant = @Constant(floatValue = 1F))
   private float modifyRegenerationSpeed(float org){
      return 2F;
   }

   @ModifyConstant(method = "updateDragonEnderCrystal", constant = @Constant(floatValue = 10F))
   private float modifyExplodeDamage(float org){
      return 5F;
   }

}
