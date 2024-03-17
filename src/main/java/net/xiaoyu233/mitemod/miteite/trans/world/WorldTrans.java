package net.xiaoyu233.mitemod.miteite.trans.world;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.AxisAlignedBB;
import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldInfo;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.xiaoyu233.mitemod.miteite.util.WorldUtil.isBloodMoonDay;

@Mixin(World.class)
public abstract class WorldTrans implements ITEWorld {

   @Shadow public abstract boolean isBlueMoon(boolean exclusively_at_night);

    @Shadow
    public static boolean isBlueMoon(long unadjusted_tick, boolean exclusively_at_night) {
        return false;
    }

    @Shadow public static int getDayOfWorld(long unadjusted_tick){
      return 0;
   }

   @Shadow
   protected WorldInfo worldInfo;

   @Unique
   public int getDayOfOverworld(){
      return getDayOfWorld(this.worldInfo.getWorldTotalTime(0));
   }

   @Override
   @Unique
   public boolean anySolidBlockIn(AxisAlignedBB bounding_box) {
      int min_x = bounding_box.getBlockCoordForMinX();
      int max_x = bounding_box.getBlockCoordForMaxX();
      int min_y = bounding_box.getBlockCoordForMinY();
      int max_y = bounding_box.getBlockCoordForMaxY();
      int min_z = bounding_box.getBlockCoordForMinZ();
      int max_z = bounding_box.getBlockCoordForMaxZ();

      for(int x = min_x; x <= max_x; ++x) {
         for(int y = min_y; y <= max_y; ++y) {
            for(int z = min_z; z <= max_z; ++z) {
               Block block = this.getBlock(x, y, z);
               if (block != null && block.isSolid(this.getBlockMetadata(x, y, z))) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @ModifyReturnValue(method = "isBloodMoon(JZ)Z", at = @At(value = "RETURN", ordinal = 1))
   private static boolean modifyBloodMoonTime(boolean original, long unadjusted_tick, boolean exclusively_at_night) {
      if (isBloodMoonDay(unadjusted_tick) && !isBlueMoon(unadjusted_tick, exclusively_at_night)) {
         return true;
      }
      return original;
   }

   @ModifyReturnValue(method = "isBlueMoon(JZ)Z", at = @At(value = "RETURN", ordinal = 1))
   private static boolean injectOneBlueMoon(boolean original, long unadjusted_tick, boolean exclusively_at_night) {
      if (unadjusted_tick / 24000L + 1L != 128L){
         return false;
      }
      return original;
   }

   @ModifyConstant(method = "isHarvestMoon(JZ)Z", constant = @Constant(longValue = 192000L))
   private static long modifyHarvestMoonTime(long constant) {
      return 72000L;
   }

   @Shadow
   public Block getBlock(int x, int y, int z) {
      return null;
   }

   @Shadow
   public final int getBlockMetadata(int x, int y, int z) {
      return 0;
   }

   @ModifyReturnValue(method = "isBloodMoon(Z)Z", at = @At(value = "RETURN", ordinal = 1))
   private boolean modifyBloodMoonTime(boolean original, boolean exclusively_at_night) {
      if (isBloodMoonDay(this.getTotalWorldTime()) && !this.isBlueMoon(exclusively_at_night)){
         return true;
      }
      return original;
   }


   @Shadow public abstract long getTotalWorldTime();

}
