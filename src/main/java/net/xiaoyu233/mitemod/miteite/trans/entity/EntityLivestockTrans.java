package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivestock.class)
public abstract class EntityLivestockTrans extends EntityAnimal{
   @Unique
   private int illnessToDeathCounter;
   
   public EntityLivestockTrans(World par1World) {
      super(par1World);
   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityAgeable) {
      return null;
   }

   @Inject(method = "onLivingUpdate",at = @At(value = "FIELD",shift = At.Shift.AFTER,target = "Lnet/minecraft/EntityLivestock;ticksExisted:I",ordinal = 0))
   private void injectIllnessToDeath(CallbackInfo c){
      if (!this.isWell()) {
         ++this.illnessToDeathCounter;
         if (this.illnessToDeathCounter == Configs.Entities.Animals.ANIMAL_ILL_TO_DEATH_TIME.get()) {
            this.setHealth(0.0F);
            this.getWorld().playSoundAtEntity(this, this.getDeathSound(), 1.0F, 1.0F);
         }
      } else if (this.illnessToDeathCounter > 0) {
         --this.illnessToDeathCounter;
      }
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound compound,CallbackInfo callbackInfo){
      this.illnessToDeathCounter = compound.getInteger("death_counter");
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound compound,CallbackInfo c){
      compound.setInteger("death_counter", this.illnessToDeathCounter);
   }

   @Shadow
   public boolean isWell() {
      return false;
   }
}
