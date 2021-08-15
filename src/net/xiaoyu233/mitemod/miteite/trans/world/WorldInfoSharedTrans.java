package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.WorldInfoShared;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldInfoShared.class)
public abstract class WorldInfoSharedTrans {
    @Shadow private long[] totalTime;

    @Shadow protected static int getWorldIndexForDimensionId(int dimension_id){
        return 0;
    };

//    @Overwrite
//    public void setTotalWorldTime(World world, long total_world_time) {
//        this.totalTime[getWorldIndexForDimensionId(0)] = total_world_time;
//        world.updateTickFlags();
//    }
}
