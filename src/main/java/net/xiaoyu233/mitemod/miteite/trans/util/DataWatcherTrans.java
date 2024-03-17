package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.DataWatcher;
import net.minecraft.WatchableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataWatcher.class)
//Fix some class cast crashing problem
public abstract class DataWatcherTrans {
    @Shadow
    protected abstract WatchableObject getWatchedObject(int par1);
    /**
     * @author XiaoYu233
     * @reason Fix some class cast crashing problem
     */
    @Overwrite
    public byte getWatchableObjectByte(int par1) {
        return ((Number)this.getWatchedObject(par1).getObject()).byteValue();
    }

    /**
     * @author XiaoYu233
     * @reason Fix some class cast crashing problem
     */
    @Overwrite
    public short getWatchableObjectShort(int par1) {
        return ((Number)this.getWatchedObject(par1).getObject()).shortValue();
    }

    /**
     * @author XiaoYu233
     * @reason Fix some class cast crashing problem
     */
    @Overwrite
    public int getWatchableObjectInt(int par1) {
        return ((Number)this.getWatchedObject(par1).getObject()).intValue();
    }

    /**
     * @author XiaoYu233
     * @reason Fix some class cast crashing problem
     */
    @Overwrite
    public float getWatchableObjectFloat(int par1) {
        return ((Number)this.getWatchedObject(par1).getObject()).floatValue();
    }

    /**
     * @author XiaoYu233
     * @reason Fix some class cast crashing problem
     */
    @Overwrite
    public String getWatchableObjectString(int par1) {
        return this.getWatchedObject(par1).getObject().toString();
    }

}
