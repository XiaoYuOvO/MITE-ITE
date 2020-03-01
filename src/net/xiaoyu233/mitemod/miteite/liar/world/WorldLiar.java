package net.xiaoyu233.mitemod.miteite.liar.world;

import net.minecraft.World;
import net.minecraft.WorldData;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import java.util.Set;

import static net.minecraft.World.isDaytime;

@Deceive(World.class)
public class WorldLiar {
    @Liar
    protected Set G;
    @Liar
    public static final int DIMENSION_ID_UNDERWORLD;
    @Liar
    public static final int DIMENSION_ID_NETHER;
    @Liar
    public static final int DIMENSION_ID_OVERWORLD;
    @Liar
    public static final int DIMENSION_ID_THE_END;
    @Liar
    public static final int Y_OFFSET_FOR_UNDERWORLD;
    @Liar
    public WorldData x;
    static {
        DIMENSION_ID_UNDERWORLD = -2;
        DIMENSION_ID_NETHER = -1;
        DIMENSION_ID_OVERWORLD = 0;
        DIMENSION_ID_THE_END = 1;
        Y_OFFSET_FOR_UNDERWORLD = 120;
    }

    public Set getG() {
        return G;
    }

    public static final boolean isBloodMoon(long unadjusted_tick, boolean exclusively_at_night) {
        if (exclusively_at_night && isDaytime(unadjusted_tick)) {
            return false;
        } else {
            return isBloodMoonDay(unadjusted_tick) && !isBlueMoon(unadjusted_tick, exclusively_at_night);
        }
    }
    public static final boolean isHarvestMoon(long unadjusted_tick, boolean exclusively_at_night) {
        return (!exclusively_at_night || !isDaytime(unadjusted_tick)) && isBloodMoon(unadjusted_tick + 72000L,
                exclusively_at_night);
    }

    public final boolean isHarvestMoon(boolean exclusively_at_night) {
        return isHarvestMoon(this.I(), exclusively_at_night);
    }

    public static boolean isBloodMoonDay(long unadjusted_tick){
        long day = unadjusted_tick / 24000L + 1L;
        return (day < 128)? day % 32L == 0L : day % 16L == 0;
    }

    private static boolean isBlueMoon(long unadjusted_tick, boolean exclusively_at_night) {
        if (exclusively_at_night && isDaytime(unadjusted_tick)) {
            return false;
        } else {
            return (unadjusted_tick / 24000L + 1L) == 128L;
        }
    }

    public final boolean isBloodMoon(boolean exclusively_at_night) {
        if (!this.isOverworld()) {
            return false;
        } else if (exclusively_at_night && this.v()) {
            return false;
        } else {
            return isBloodMoonDay(this.I()) && !this.isBlueMoon(exclusively_at_night);
        }
    }

    @Stealing
    private boolean v() {
        return false;
    }

    @Stealing
    private boolean isBlueMoon(boolean exclusively_at_night) {
        return false;
    }

    @Stealing
    private long I() {
        return 0;
    }

    @Stealing
    private boolean isOverworld() {
        return false;
    }
}
