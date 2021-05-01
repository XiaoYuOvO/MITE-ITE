package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityTrans {
   @Shadow @Final public AxisAlignedBB boundingBox;
   @Shadow public World worldObj;
   private int netherrackWalkTime = 0;

   @Shadow
   protected void entityInit() {
   }

   @Overwrite
   public boolean isInFire() {
      if (this.worldObj.isTheNether() && (Configs.GameMechanics.Nether.NETHERRACK_DAMAGE.get()) && !(ReflectHelper.dyCast(this) instanceof EntityZombieLord) && this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.005D, 0.001D), Block.netherrack.blockID, -1)) {
         ++this.netherrackWalkTime;
         if (this.netherrackWalkTime > 20) {
            this.netherrackWalkTime = 0;
            return true;
         }
      }

      return (this.worldObj.isUnderworld() && this.boundingBox.minY <= 3.0D || this.worldObj.isTheNether() && (this.boundingBox.minY <= 1.0D || this.boundingBox.maxY >= 123.0D)) && this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.001D, 0.001D), Block.mantleOrCore.blockID, -1) || this.worldObj.isBoundingBoxBurning(this.boundingBox.contract(0.001D, 0.001D, 0.001D), false);
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound var1) {
   }
}
