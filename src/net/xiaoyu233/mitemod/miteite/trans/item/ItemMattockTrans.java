package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.BlockBreakInfo;
import net.minecraft.ItemMattock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(ItemMattock.class)
public class ItemMattockTrans extends ItemShovelTrans{
    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        if (this.materials_effective_against.contains(blockBreakInfo.block)){
            return 2;
        }
        return 0;
    }
}
