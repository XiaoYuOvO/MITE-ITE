package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityLiving.class)
public abstract class EntityLivingTrans extends Entity {
   @Shadow public abstract void e(float par1);

   @Shadow
   public int attackTime;
   @Shadow
   public float attackedAtYaw;
   @Shadow
   public float cameraPitch;
   @Shadow
   public int deathTime;
   @Shadow
   public int hurtTime;
   @Shadow
   public boolean isSwingInProgress;
   @Shadow
   public float limbSwingAmount;
   @Shadow
   public int maxHurtResistantTime;
   @Shadow
   public int maxHurtTime;
   @Shadow
   public float moveStrafing;
   @Shadow
   public double newPosY;
   @Shadow
   public float prevCameraPitch;
   @Shadow
   public float prevLimbSwingAmount;
   @Shadow
   public float prevRenderYawOffset;
   @Shadow
   public float prevRotationYawHead;
   @Shadow
   public float prevSwingProgress;
   @Shadow
   public float renderYawOffset;
   @Shadow
   public float rotationYawHead;
   @Shadow
   public float swingProgress;
   @Shadow
   protected boolean isJumping;
   @Shadow
   protected float lastDamage;
   @Shadow
   protected int recentlyHit;
   @Shadow
   protected int scoreValue;

   public EntityLivingTrans(World par1World) {
      super(par1World);
   }

   protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {
   }

   @Shadow
   protected void entityInit() {
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "attackEntityFrom",at = @At(value = "INVOKE",shift = At.Shift.AFTER,target = "Lnet/minecraft/EntityLiving;attackEntityFromHelper(Lnet/minecraft/Damage;Lnet/minecraft/EntityDamageResult;)Lnet/minecraft/EntityDamageResult;"))
   private void injectAfterDamageCallback(Damage damage, CallbackInfoReturnable<EntityDamageResult> c, EntityDamageResult result){
      this.checkForAfterDamage(damage, result);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "onDeath",at = @At(value = "INVOKE_ASSIGN",target = "Lnet/minecraft/EnchantmentManager;getLootingModifier(Lnet/minecraft/EntityLiving;)I",shift = At.Shift.AFTER))
   private void injectDropHead(DamageSource par1DamageSource, CallbackInfo ci, Entity var2, EntityLiving var3, int var4){
      if (var2 instanceof EntityPlayer) {
         ItemStack heldItemStack = ((EntityPlayer) var2).getHeldItemStack();
         if (heldItemStack != null) {
            float modifierValue = EnchantmentManager.getEnchantmentLevel(Enchantments.BEHEADING,heldItemStack) * Configs.Item.Enchantment.BEHEADING_CHANCE_BOOST_PER_LVL.get();
            if (modifierValue > 0.0F) {
               boolean dropHead = (float) this.rand.nextInt(100) < modifierValue * 100.0F;
               if (dropHead) {
                  EntityLiving thisLiving = ReflectHelper.dyCast(this);
                  ItemStack headItemStack = null;
                  if (thisLiving instanceof EntityCreeper) {
                     headItemStack = new ItemStack(Item.skull, 1, 4);
                  }

                  if (thisLiving instanceof EntityZombie) {
                     headItemStack = new ItemStack(Item.skull, 1, 2);
                  }

                  if (thisLiving instanceof EntitySkeleton) {
                     if (((EntitySkeleton) thisLiving).getSkeletonType() == 1){
                        headItemStack = new ItemStack(Item.skull, 1, 1);
                     }else {
                        headItemStack = new ItemStack(Item.skull, 1, 0);
                     }
                  }

                  if (thisLiving instanceof EntityPlayer) {
                     headItemStack = new ItemStack(Item.skull, 1, 3);
                  }

                  if (headItemStack != null) {
                     this.dropItemStack(headItemStack);
                  }
               }
            }
         }
      }
   }

   public boolean canBeTargetTo(EntityLiving from){
      return true;
   }

   @Redirect(method = "onEntityUpdate",at = @At(ordinal = 1,value = "INVOKE",target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult injectModifyPlayerInWallDamage(EntityLiving caller,Damage damage){
      if (ReflectHelper.dyCast(this) instanceof EntityPlayer) {
        return this.attackEntityFrom(new Damage(DamageSource.inWall, (Configs.GameMechanics.IN_WALL_DAMAGE_FOR_PLAYER.get()).floatValue()));
      }else {
        return this.attackEntityFrom(new Damage(DamageSource.inWall, 1.0f));
      }
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
   }

   public void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result) {
      this.tryDamageArmor(damage_source, amount, result);
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
