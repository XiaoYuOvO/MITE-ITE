package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.AxisAlignedBB;

public interface ITEWorld {
    default int getDayOfOverworld(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default boolean anySolidBlockIn(AxisAlignedBB bounding_box){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
