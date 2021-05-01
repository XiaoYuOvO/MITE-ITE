package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
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
public class EntityLivingTrans extends Entity {
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
            float modifierValue = ToolModifierTypes.BEHEADING_MODIFIER.getModifierValue(heldItemStack.getTagCompound());
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
                     headItemStack = new ItemStack(Item.skull, 1, 0);
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
//   @Overwrite
//   public void onEntityUpdate() {
//      this.prevSwingProgress = this.swingProgress;
//      super.onEntityUpdate();
//      this.q.theProfiler.startSection("livingEntityBaseTick");
//      if (this.onServer() && this.T()) {
//         if (ReflectHelper.dyCast(this) instanceof EntitySilverfish) {
//            if (this.isSilverfishInsideDamagingOpaqueBlock()) {
//               this.attackEntityFrom(new Damage(DamageSource.inWall, 1.0F));
//            }
//         } else if (this.U()) {
//
//         }
//      }
//
//      if (!this.canCatchFire() || this.q.isRemote) {
//         this.B();
//      }
//
//      boolean var1 = ReflectHelper.dyCast(this) instanceof EntityPlayer && ((EntityPlayer)ReflectHelper.dyCast(EntityPlayer.class, this)).bG.disableDamage;
//      if (!this.breathesAir()) {
//         var1 = true;
//      }
//
//      if (this.T() && this.a(Material.water)) {
//         if (ReflectHelper.dyCast(this) instanceof EntityChicken && this.isChild() && this.q.getBlockId(MathHelper.floor_double(this.u), MathHelper.floor_double(this.v + (double)this.P), MathHelper.floor_double(this.w)) == 0) {
//            this.g(300);
//            var1 = true;
//         }
//
//         if (!this.canBreatheUnderwater() && !this.isPotionActive(MobEffectList.waterBreathing.id) && !var1) {
//            this.g(this.decreaseAirSupply(this.al()));
//            if (this.al() <= -20) {
//               this.g(0);
//
//               for(int var2 = 0; var2 < 8; ++var2) {
//                  float var3 = this.rand.nextFloat() - this.rand.nextFloat();
//                  float var4 = this.rand.nextFloat() - this.rand.nextFloat();
//                  float var5 = this.rand.nextFloat() - this.rand.nextFloat();
//                  this.q.spawnParticle(EnumParticle.bubble, this.u + (double)var3, this.v + (double)var4, this.w + (double)var5, this.x, this.y, this.z);
//               }
//
//               if (this.onServer()) {
//                  this.attackEntityFrom(new Damage(DamageSource.drown, 2.0F));
//               }
//            }
//         }
//
//         this.B();
//         if (!this.q.isRemote && this.ag() && this.o instanceof EntityLiving) {
//            this.a((Entity)null);
//         }
//      } else if (this.isEntityPlayer()) {
//         this.g(MathHelper.clamp_int(this.al() + 10, 0, 300));
//      } else {
//         this.g(300);
//      }
//
//      this.prevCameraPitch = this.cameraPitch;
//      if (this.attackTime > 0) {
//         --this.attackTime;
//      }
//
//      if (this.hurtTime > 0) {
//         --this.hurtTime;
//      }
//
//      if (this.af > 0) {
//         --this.af;
//      }
//
//      if (this.knockback_resistant_ticks > 0) {
//         --this.knockback_resistant_ticks;
//      }
//
//      if (this.getHealth() <= 0.0F) {
//         this.onDeathUpdate();
//      }
//
//      if (this.recentlyHit > 0) {
//         --this.recentlyHit;
//      } else {
//         this.aS = null;
//      }
//
//      if (this.lastAttackTarget != null && !this.lastAttackTarget.isEntityAlive()) {
//         this.lastAttackTarget = null;
//      }
//
//      if (this.entityLivingToAttack != null && !this.entityLivingToAttack.isEntityAlive()) {
//         this.setRevengeTarget((EntityLiving)null);
//      }
//
//      this.updatePotionEffects();
//      this.prevRenderYawOffset = this.renderYawOffset;
//      this.prevRotationYawHead = this.rotationYawHead;
//      this.C = this.A;
//      this.D = this.B;
//      this.q.theProfiler.endSection();
//   }

   @Redirect(method = "onEntityUpdate",at = @At(ordinal = 1,value = "INVOKE",target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult injectModifyPlayerInWallDamage(EntityLiving caller,Damage damage){
      if (ReflectHelper.dyCast(this) instanceof EntityPlayer) {
        return this.attackEntityFrom(new Damage(DamageSource.inWall, (Configs.GameMechanics.IN_WALL_DAMAGE_FOR_PLAYER.get()).floatValue()));
      }else {
        return this.attackEntityFrom(new Damage(DamageSource.inWall, 1.0f));
      }
   }

//   @Overwrite
//   public void onDeath(DamageSource par1DamageSource) {
//      Entity var2 = par1DamageSource.getResponsibleEntity();
//      EntityLiving var3 = this.func_94060_bK();
//      if (this.scoreValue >= 0 && var3 != null) {
//         var3.addToPlayerScore(this, this.scoreValue);
//      }
//
//      if (var2 != null) {
//         var2.onKillEntity((EntityLiving)ReflectHelper.dyCast(EntityLiving.class, this));
//      }
//
//      if (!this.q.isRemote) {
//         int var4 = 0;
//         if (var2 instanceof EntityPlayer) {
//            var4 = EnchantmentManager.getLootingModifier((EntityLiving)var2);
//
//         }
//
//         if (!this.isChild() && this.q.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
//            if (!this.has_taken_massive_fall_damage || this.rand.nextFloat() < 0.1F) {
//               this.dropFewItems(this.recentlyHit > 0, par1DamageSource);
//            }
//
//            this.dropContainedItems();
//            this.dropEquipment(this.recentlyHit > 0, var4);
//         }
//      }
//
//      this.q.setEntityState(this, EnumEntityState.dead);
//   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
   }

   public void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result) {
      this.tryDamageArmor(damage_source, amount, result);
   }

//   @Overwrite
//   public EntityDamageResult attackEntityFrom(Damage damage) {
//      EntityDamageResult result = super.attackEntityFrom(damage);
//      if (result != null && !result.entityWasDestroyed()) {
//         if (this.getHealth() <= 0.0F) {
//            return null;
//         } else if (damage.isFireDamage() && this.isPotionActive(MobEffectList.fireResistance)) {
//            return null;
//         } else {
//            if (damage.isAnvil() || damage.isFallingBlock()) {
//               ItemStack head_armor = this.getHelmet();
//               if (head_armor != null) {
//                  result.applyArmorDamageResult(head_armor.tryDamageItem(damage.getSource(), (int)(damage.getAmount() * 4.0F + this.rand.nextFloat() * damage.getAmount() * 2.0F), (EntityLiving)ReflectHelper.dyCast(this)));
//                  damage.scaleAmount(0.75F);
//               }
//            }
//
//            if (damage.isNil()) {
//               return result;
//            } else {
//               if (damage.isLessThanHalfAHeart()) {
//                  damage.setAmount(1.0F);
//               }
//
//               boolean treat_as_completely_new_attack = this.af * 2 <= this.maxHurtResistantTime;
//               this.attackEntityFromHelper(damage, result);
//               //Injectable HERE
//
//               //End of injection
//               boolean make_death_sound = result.entityWasDestroyed();
//               boolean make_hurt_sound = !make_death_sound && (result.entityWasKnockedBack() || result.entityLostHealth());
//               if (result.entityWasNegativelyAffected()) {
//                  if (treat_as_completely_new_attack) {
//                     boolean refresh_red_tint = result.entityLostHealth();
//                     if (refresh_red_tint && damage.isSunlight() && (ReflectHelper.dyCast(this) instanceof EntityShadow || ReflectHelper.dyCast(this) instanceof EntityNightwing)) {
//                        refresh_red_tint = false;
//                     }
//
//                     this.q.setEntityState(this, refresh_red_tint ? EnumEntityState.hurt_with_red_tint_refreshed : EnumEntityState.hurt_without_red_tint_refreshed);
//                     if (make_death_sound) {
//                        this.makeSound(this.getDeathSound());
//                     } else if (make_hurt_sound) {
//                        this.makeSound(this.getHurtSound());
//                     }
//                  }
//
//                  if (!damage.isDrowning()) {
//                     this.K();
//                  }
//
//                  this.limbSwingAmount = 1.5F;
//                  this.attackedAtYaw = 0.0F;
//                  Entity responsible_entity = damage.getSource().getResponsibleEntity();
//                  if (responsible_entity == null) {
//                     this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
//                  } else {
//                     this.refreshDespawnCounter(-1200);
//                     responsible_entity.refreshDespawnCounter(-1200);
//                     if (responsible_entity instanceof EntityLiving) {
//                        this.setRevengeTarget((EntityLiving)responsible_entity);
//                        this.setLastHarmingEntity(responsible_entity);
//                        this.considerFleeing();
//                     }
//
//                     if (!(responsible_entity instanceof EntityPlayer)) {
//                        if (responsible_entity instanceof EntityWolf) {
//                           EntityWolf var5 = (EntityWolf)responsible_entity;
//                           if (var5.isTamed()) {
//                              this.recentlyHit = 100;
//                              this.aS = null;
//                           }
//                        }
//                     } else {
//                        if (!(damage.getSource().getImmediateEntity() instanceof EntitySnowball) || this.canTakeDamageFromPlayerThrownSnowballs()) {
//                           this.recentlyHit = 100;
//                        }
//
//                        this.aS = (EntityPlayer)responsible_entity;
//                        this.refreshDespawnCounter(-9600);
//                     }
//                  }
//
//                  if (result.entityWasDestroyed()) {
//                     this.onDeath(damage.getSource());
//                  }
//               }
//
//               return result;
//            }
//         }
//      } else {
//         return result;
//      }
//   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
