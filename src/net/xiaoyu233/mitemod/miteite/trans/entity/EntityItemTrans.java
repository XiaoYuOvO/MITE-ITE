package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItem.class)
public abstract class EntityItemTrans extends Entity {
   private boolean isExploded;

   public EntityItemTrans(World par1World) {
      super(par1World);
   }

   @Shadow
   public EntityItem applyExplosionMotion(Explosion explosion) {
      return null;
   }

   @Shadow
   protected abstract boolean canTriggerWalking();

   @Shadow
   protected void entityInit() {
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

   @Redirect(method = "handleExplosion",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityItem;tryRemoveFromWorldUniques()V"))
   private void injectUpdateExploded(EntityItem caller){
      this.isExploded = true;
      this.tryRemoveFromWorldUniques();
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   public void tryRemoveFromWorldUniques() {
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
