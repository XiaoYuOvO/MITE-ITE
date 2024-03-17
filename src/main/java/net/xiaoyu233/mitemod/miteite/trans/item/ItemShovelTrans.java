package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.BlockBreakInfo;
import net.minecraft.ItemShovel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemShovel.class)
public abstract class ItemShovelTrans extends ItemToolTrans{
    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        return 3;
    }
}
