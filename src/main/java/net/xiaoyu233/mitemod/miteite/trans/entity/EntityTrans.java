package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityTrans {
   @Shadow public abstract boolean canCatchFire();

   @Shadow @Final public AxisAlignedBB boundingBox;
   @Shadow public World worldObj;
   @Unique
   private int netherrackWalkTime = 0;

   @Inject(method = "isInFire", at = @At("HEAD"), cancellable = true)
   public void injectNetherFire(CallbackInfoReturnable<Boolean> cir) {
      if (this.worldObj.isTheNether() &&
              (Configs.GameMechanics.Nether.NETHERRACK_DAMAGE.get()) &&
              Configs.GameMechanics.Nether.NETHERRACK_DAMAGE_LIMIT_DAY.get() <= this.worldObj.getDayOfOverworld() &&
              this.canCatchFire() &&
              this.worldObj.doesBoundingBoxContainBlock(this.boundingBox.expand(0.001D, 0.005D, 0.001D), Block.netherrack.blockID, -1)) {
         ++this.netherrackWalkTime;
         if (this.netherrackWalkTime > 20) {
            this.netherrackWalkTime = 0;
            cir.setReturnValue(true);
         }
      }
   }
}
