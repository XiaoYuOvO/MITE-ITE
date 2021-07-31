package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Block;
import net.minecraft.BlockBreakInfo;
import net.minecraft.BlockCrops;
import net.minecraft.ItemShovel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemShovel.class)
public class ItemShovelTrans extends ItemToolTrans{
    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        Block block = blockBreakInfo.block;
        if (block instanceof BlockCrops){
            if (((BlockCrops) block).isMature(blockBreakInfo.getMetadata())) {
                return 8;
            }
        }
        if (this.materials_effective_against.contains(block)){
            return 3;
        }
        return 0;
    }
}
