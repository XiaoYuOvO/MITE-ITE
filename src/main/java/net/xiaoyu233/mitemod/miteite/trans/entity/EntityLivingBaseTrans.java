package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.api.ITELivingBase;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseTrans extends Entity implements ITELivingBase {
   public EntityLivingBaseTrans(World par1World) {
      super(par1World);
   }

   @Override
   public void checkForAfterDamage(Damage damage, EntityDamageResult result) {
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "attackEntityFrom",at = @At(value = "INVOKE",shift = At.Shift.AFTER,target = "Lnet/minecraft/EntityLivingBase;attackEntityFromHelper(Lnet/minecraft/Damage;Lnet/minecraft/EntityDamageResult;)Lnet/minecraft/EntityDamageResult;"))
   private void injectAfterDamageCallback(Damage damage, CallbackInfoReturnable<EntityDamageResult> c, EntityDamageResult result){
      this.checkForAfterDamage(damage, result);
   }

   @Unique
   @Override
   public boolean canBeTargetTo(EntityLivingBase from){
      return true;
   }

   @Override
   public void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result) {
      this.tryDamageArmor(damage_source, amount, result);
   }

   @Shadow
   protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "onDeath",at = @At(value = "INVOKE_ASSIGN",target = "Lnet/minecraft/EnchantmentHelper;getLootingModifier(Lnet/minecraft/EntityLivingBase;)I",shift = At.Shift.AFTER))
   private void injectDropHead(DamageSource par1DamageSource, CallbackInfo ci, Entity var2, EntityLivingBase var3, int var4){
      if (var2 instanceof EntityPlayer) {
         ItemStack heldItemStack = ((EntityPlayer) var2).getHeldItemStack();
         if (heldItemStack != null) {
            float modifierValue = EnchantmentHelper.getEnchantmentLevel(Enchantments.BEHEADING,heldItemStack) * Configs.Item.Enchantment.BEHEADING_CHANCE_BOOST_PER_LVL.get();
            if (modifierValue > 0.0F) {
               boolean dropHead = (float) this.rand.nextInt(100) < modifierValue * 100.0F;
               if (dropHead) {
                  ItemStack headItemStack = MITE_ITEPlugin$getHeadStack();
                  if (headItemStack != null) {
                     this.dropItemStack(headItemStack);
                  }
               }
            }
         }
      }
   }

   @Unique
   @Nullable
   private ItemStack MITE_ITEPlugin$getHeadStack() {
      EntityLivingBase thisLiving = ReflectHelper.dyCast(this);
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
      return headItemStack;
   }

   @SuppressWarnings("UnresolvedMixinReference")// Not so clever :(
   @Redirect(
           method = {"onEntityUpdate()V"},
           at = @At(
                   ordinal = 1,
                   value = "INVOKE",
                   target = "Lnet/minecraft/EntityLivingBase;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"
           )
   )
   private EntityDamageResult injectModifyPlayerInWallDamage(EntityLivingBase caller, Damage damage) {
      return ReflectHelper.dyCast(this) instanceof EntityPlayer
              ? this.attackEntityFrom(new Damage(DamageSource.inWall, Configs.GameMechanics.IN_WALL_DAMAGE_FOR_PLAYER.get().floatValue()))
              : this.attackEntityFrom(new Damage(DamageSource.inWall, 1.0F));
   }
}
