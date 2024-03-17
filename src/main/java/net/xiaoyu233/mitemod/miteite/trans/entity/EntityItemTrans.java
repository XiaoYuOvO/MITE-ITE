package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItem.class)
public abstract class EntityItemTrans extends Entity implements ITEItemEntity {
   @Unique
   private boolean isExploded;
   @Unique
   private boolean canBePickUpByPlayer;

   public EntityItemTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = {"<init>(Lnet/minecraft/World;)V","<init>(Lnet/minecraft/World;DDD)V","<init>(Lnet/minecraft/World;DDDLnet/minecraft/ItemStack;)V"},at = @At("RETURN"))
   protected void injectInit(CallbackInfo callbackInfo) {
      this.canBePickUpByPlayer = true;
   }

   @Inject(method = "canBePickedUpBy",
           at = @At(value = "HEAD"),
           cancellable = true)
   public void injectPlayerCannotPickup(EntityLivingBase entity_living_base,CallbackInfoReturnable<Boolean> callback){
      if (entity_living_base instanceof EntityPlayer) {
         if (!this.canBePickUpByPlayer){
            callback.setReturnValue(false);
            callback.cancel();
         }
      }
   }

   @Shadow
   public ItemStack getEntityItem() {
      return null;
   }

   @Inject(method = "handleExplosion",
           cancellable = true,
           at = @At(value = "INVOKE",
                   shift = At.Shift.AFTER,
                   target = "Lnet/minecraft/EntityItem;calcExplosionForce(FD)F"))
   private void injectCancelExplosionCopy(CallbackInfoReturnable<Boolean> callback){
      if (this.isExploded) {
         this.setDead();
         this.tryRemoveFromWorldUniques();
         callback.setReturnValue(true);
         callback.cancel();
      }
   }

   @Inject(method = "readEntityFromNBT",at = @At(value = "RETURN"))
   protected void readEntityFromNBT(NBTTagCompound var1, CallbackInfo callback) {
      if (var1.hasKey("CanBePickupByPlayer")){
         this.canBePickUpByPlayer = var1.getBoolean("CanBePickupByPlayer");
      }
   }

   @Redirect(method = "handleExplosion",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityItem;tryRemoveFromWorldUniques()V"))
   private void injectUpdateExploded(EntityItem caller){
      this.isExploded = true;
      this.tryRemoveFromWorldUniques();
   }

   @Unique
   public void setCanBePickUpByPlayer(boolean canBePickUpByPlayer) {
      this.canBePickUpByPlayer = canBePickUpByPlayer;
   }

   @Unique
   public boolean canBePickUpByPlayer() {
      return canBePickUpByPlayer;
   }

   @Shadow
   public void tryRemoveFromWorldUniques() {
   }

   @Inject(method = "writeEntityToNBT",at = @At(value = "RETURN"))
   protected void writeEntityToNBT(NBTTagCompound var1,CallbackInfo callback) {
      var1.setBoolean("CanBePickupByPlayer",this.canBePickUpByPlayer);
   }
}
