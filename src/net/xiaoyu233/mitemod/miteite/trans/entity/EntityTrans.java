package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityTrans {
   @Shadow public abstract boolean canCatchFire();

   @Shadow @Final public AxisAlignedBB boundingBox;
   @Shadow public World worldObj;
   private int netherrackWalkTime = 0;

   @Shadow
   protected void entityInit() {
   }

   @Overwrite
   public boolean isInFire() {
      if (this.worldObj.isTheNether() &&
              (Configs.GameMechanics.Nether.NETHERRACK_DAMAGE.get()) &&
              Configs.GameMechanics.Nether.NETHERRACK_DAMAGE_LIMIT_DAY.get() <= this.worldObj.getDayOfOverworld() &&
              this.canCatchFire() &&
              this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.005D, 0.001D), Block.netherrack.blockID, -1)) {
         ++this.netherrackWalkTime;
         if (this.netherrackWalkTime > 20) {
            this.netherrackWalkTime = 0;
            return true;
         }
      }

      return (this.worldObj.isUnderworld() && this.boundingBox.minY <= Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 1 || this.worldObj.isTheNether() && (this.boundingBox.minY <= 1.0D || this.boundingBox.maxY >= 123.0D)) && this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.001D, 0.001D), Block.mantleOrCore.blockID, -1) || this.worldObj.isBoundingBoxBurning(this.boundingBox.contract(0.001D, 0.001D, 0.001D), false);
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
