package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockCrops;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(BlockCrops.class)
public class BlockCropTrans {
    public boolean fertilize(World world, int x, int y, int z, ItemStack item_stack) {
        Item item = item_stack.b();
        if (item != Item.aY && item != Item.manure) {
            return false;
        } else {
            int metadata = world.h(x, y, z);
            return this.isBlighted(metadata) && world.b(x, y, z, this.setBlighted(metadata, false), 2);
        }
    }

    @Marker
    private int setBlighted(int metadata, boolean b) {
        return 0;
    }

    @Marker
    private boolean isBlighted(int metadata) {
        return false;
    }
}
