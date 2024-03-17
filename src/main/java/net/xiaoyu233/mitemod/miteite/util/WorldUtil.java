package net.xiaoyu233.mitemod.miteite.util;

public class WorldUtil {
    public static boolean isBloodMoonDay(long unadjustedTick) {
        long day = unadjustedTick / 24000L + 1L;
        return day < 128L ? day % 32L == 0L : day % 16L == 0L;
    }
}
