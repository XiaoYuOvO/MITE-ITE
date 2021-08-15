package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.AxisAlignedBB;
import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

import static net.xiaoyu233.mitemod.miteite.util.WorldUtil.isBloodMoonDay;

@Mixin(World.class)
public abstract class WorldTrans {
   @Shadow public static int getDayOfWorld(long unadjusted_tick){
      return 0;
   }

   public int getDayOfOverworld(){
      return getDayOfWorld(this.worldInfo.getWorldTotalTime(0));
   }

   @Shadow
   public WorldData worldInfo;
   @Shadow
   protected Set activeChunkSet;

   @Overwrite
   public static boolean isBloodMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      if (exclusivelyAtNight && World.isDaytime(unadjustedTick)) {
         return false;
      } else {
         return isBloodMoonDay(unadjustedTick) && !isBlueMoon(unadjustedTick, exclusivelyAtNight);
      }
   }

   @Overwrite
   public static boolean isBlueMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      if (exclusivelyAtNight && World.isDaytime(unadjustedTick)) {
         return false;
      } else {
         return unadjustedTick / 24000L + 1L == 128L;
      }
   }

   @Overwrite
   public static boolean isHarvestMoon(long unadjustedTick, boolean exclusivelyAtNight) {
      return (!exclusivelyAtNight || !World.isDaytime(unadjustedTick)) && isBloodMoon(unadjustedTick + 72000L, exclusivelyAtNight);
   }

   public final boolean anySolidBlockIn(AxisAlignedBB bounding_box) {
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

   @Shadow
   public Block getBlock(int x, int y, int z) {
      return null;
   }

   @Shadow
   public final int getBlockMetadata(int x, int y, int z) {
      return 0;
   }

//   @Overwrite
//   public long getTotalWorldTime() {
//      //Redirect to overworld
//      return this.worldInfo.getWorldTotalTime(0);
//   }

   @Overwrite
   public final boolean isBloodMoon(boolean exclusivelyAtNight) {
      if (!this.isOverworld()) {
         return false;
      } else if (exclusivelyAtNight && this.isDaytime()) {
         return false;
      } else {
         return isBloodMoonDay(this.getTotalWorldTime()) && !this.isBlueMoon(exclusivelyAtNight);
      }
   }

   @Shadow
   private boolean isBlueMoon(boolean exclusively_at_night) {
      return false;
   }

   @Shadow
   private boolean isDaytime() {
      return false;
   }

   @Shadow public abstract long getTotalWorldTime();

   @Overwrite
   public final boolean isHarvestMoon(boolean exclusivelyAtNight) {
      return isHarvestMoon(this.getTotalWorldTime(), exclusivelyAtNight);
   }

   @Shadow
   private boolean isOverworld() {
      return false;
   }
}
