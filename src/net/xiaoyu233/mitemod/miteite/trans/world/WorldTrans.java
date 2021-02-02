package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.AxisAlignedBB;
import net.minecraft.Block;
import net.minecraft.World;
import net.minecraft.WorldData;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.Set;

import static net.minecraft.World.isDaytime;

@Transform(World.class)
public class WorldTrans {
    @Link
    protected Set G;
    @Link
    public static final int DIMENSION_ID_UNDERWORLD;
    @Link
    public static final int DIMENSION_ID_NETHER;
    @Link
    public static final int DIMENSION_ID_OVERWORLD;
    @Link
    public static final int DIMENSION_ID_THE_END;
    @Link
    public static final int Y_OFFSET_FOR_UNDERWORLD;
    @Link
    public WorldData x;
    static {
        DIMENSION_ID_UNDERWORLD = -2;
        DIMENSION_ID_NETHER = -1;
        DIMENSION_ID_OVERWORLD = 0;
        DIMENSION_ID_THE_END = 1;
        Y_OFFSET_FOR_UNDERWORLD = 120;
    }



    public static boolean isBloodMoon(long unadjustedTick, boolean exclusivelyAtNight) {
        if (exclusivelyAtNight && isDaytime(unadjustedTick)) {
            return false;
        } else {
            return isBloodMoonDay(unadjustedTick) && !isBlueMoon(unadjustedTick, exclusivelyAtNight);
        }
    }
    public static boolean isHarvestMoon(long unadjustedTick, boolean exclusivelyAtNight) {
        return (!exclusivelyAtNight || !isDaytime(unadjustedTick)) && isBloodMoon(unadjustedTick + 72000L,
                exclusivelyAtNight);
    }

    public final boolean isHarvestMoon(boolean exclusivelyAtNight) {
        return isHarvestMoon(this.I(), exclusivelyAtNight);
    }

    public static boolean isBloodMoonDay(long unadjustedTick){
        long day = unadjustedTick / 24000L + 1L;
        return (day < 128)? day % 32L == 0L : day % 16L == 0;
    }

    private static boolean isBlueMoon(long unadjustedTick, boolean exclusivelyAtNight) {
        if (exclusivelyAtNight && isDaytime(unadjustedTick)) {
            return false;
        } else {
            return (unadjustedTick / 24000L + 1L) == 128L;
        }
    }

    public final boolean isBloodMoon(boolean exclusivelyAtNight) {
        if (!this.isOverworld()) {
            return false;
        } else if (exclusivelyAtNight && this.v()) {
            return false;
        } else {
            return isBloodMoonDay(this.I()) && !this.isBlueMoon(exclusivelyAtNight);
        }
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
                    if (block != null && block.isSolid(this.h(x, y, z))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Marker
    public Block getBlock(int x, int y, int z) {
        return null;
    }

    //getMetadata
    @Marker
    public final int h(int x, int y, int z) {
        return 0;
    }

    @Marker
    private boolean v() {
        return false;
    }

    @Marker
    private boolean isBlueMoon(boolean exclusively_at_night) {
        return false;
    }

    @Marker
    private long I() {
        return 0;
    }

    @Marker
    private boolean isOverworld() {
        return false;
    }
}
