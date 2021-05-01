package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAgeable;
import net.minecraft.EntityLivestock;
import net.minecraft.NBTTagCompound;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivestock.class)
public abstract class EntityLivestockTrans extends EntityAnimalTrans {
   private int illnessToDeathCounter;
   
   public EntityLivestockTrans(World par1World) {
      super(par1World);
   }
//   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
//      super.writeEntityToNBT(par1NBTTagCompound);
//      par1NBTTagCompound.setFloat("food", this.getFood());
//      par1NBTTagCompound.setFloat("water", this.getWater());
//      par1NBTTagCompound.setFloat("freedom", this.getFreedom());
//      par1NBTTagCompound.setInteger("production_counter", this.production_counter);
//      par1NBTTagCompound.setInteger("manure_countdown", this.manure_countdown);
//      par1NBTTagCompound.setBoolean("has_been_spooked_by_other_animal", this.has_been_spooked_by_other_animal);
//
//   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityAgeable) {
      return null;
   }

   @Shadow
   private float getFood() {
      return 0.0F;
   }

   @Shadow
   private void setFood(float food) {
   }

   @Shadow
   private float getFreedom() {
      return 0.0F;
   }

//   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
//      super.readEntityFromNBT(par1NBTTagCompound);
//      this.setFood(par1NBTTagCompound.getFloat("food"));
//      this.setWater(par1NBTTagCompound.getFloat("water"));
//      this.setFreedom(par1NBTTagCompound.getFloat("freedom"));
//      this.production_counter = par1NBTTagCompound.getInteger("production_counter");
//      this.manure_countdown = par1NBTTagCompound.getInteger("manure_countdown");
//      this.has_been_spooked_by_other_animal = par1NBTTagCompound.getBoolean("has_been_spooked_by_other_animal");
//
//   }

   @Shadow
   private void setFreedom(float freedom) {
   }

   @Shadow
   private float getWater() {
      return 0.0F;
   }

   @Shadow
   private void setWater(float water) {
   }

   @Inject(method = "onLivingUpdate",at = @At(value = "FIELD",shift = At.Shift.AFTER,target = "Lnet/minecraft/World;isRemote:Z",ordinal = 1))
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
//   @Overwrite
//   public void onLivingUpdate() {
//      super.onLivingUpdate();
//      if (!this.worldObj.isRemote) {
//         if (this.ticksExisted % 100 == 0) {
//            if (this.rand.nextInt(10) > 0 && this.updateWellness() && !this.isChild()) {
//               ++this.production_counter;
//            }
//
//            this.produceGoods();
//         }
//
//         if (!this.isChild()) {
//            if (!this.isDesperateForFood() && --this.manure_countdown <= 0) {
//               this.b(Item.manure.itemID, 1);
//               this.manure_countdown = this.manure_period / 2 + this.rand.nextInt(this.manure_period);
//            }
//
//            if (this.F) {
//               if (this.ac % 1000 == 0) {
//                  this.last_trampled_x = this.last_trampled_y = this.last_trampled_z = 0;
//               }
//
//               int x = MathHelper.floor_double(this.u);
//               int y = MathHelper.floor_double(this.v) - 1;
//               int z = MathHelper.floor_double(this.w);
//               if (x != this.last_trampled_x || y != this.last_trampled_y || z != this.last_trampled_z) {
//                  this.last_trampled_x = x;
//                  this.last_trampled_y = y;
//                  this.last_trampled_z = z;
//                  Block block = Block.blocksList[this.worldObj.getBlockId(x, y, z)];
//                  if (block != null) {
//                     block.onTrampledBy(this.worldObj, x, y, z, this);
//                  }
//               }
//            }
//         }
//
//         if (this.has_been_spooked_by_other_animal && this.worldObj.total_time % 4000L == 0L) {
//            this.has_been_spooked_by_other_animal = false;
//         }
//
//         if ((this.has_decided_to_flee || this.fleeing || this.isSpooked()) && this.getTicksExistedWithOffset() % 20 == 0) {
//            List list = this.worldObj.getEntitiesWithinAABB(EntityLivestock.class, this.E.expand(8.0D, 4.0D, 8.0D));
//
//            for (Object value : list) {
//               EntityLivestock livestock = (EntityLivestock) value;
//               if (livestock != ReflectHelper.dyCast(this) && !livestock.has_decided_to_flee && !livestock.fleeing && !livestock
//                       .isSpooked() && !livestock.has_been_spooked_by_other_animal && !livestock.isDead && livestock.canSeeEntity(this) && livestock
//                       .canPathTo(this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ(), 8)) {
//                  livestock.has_been_spooked_by_other_animal = true;
//               }
//            }
//         }
//      }
//
//   }

   @Shadow
   private boolean isWell() {
      return false;
   }


   @Shadow
   protected abstract void produceGoods();

}
