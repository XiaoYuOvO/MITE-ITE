package net.xiaoyu233.mitemod.miteite.liar.block;

import net.minecraft.BlockCrops;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(BlockCrops.class)
public class BlockCropLiar {
    public boolean fertilize(World world, int x, int y, int z, ItemStack item_stack) {
        Item item = item_stack.b();
        if (item != Item.aY && item != Item.manure) {
            return false;
        } else {
            int metadata = world.h(x, y, z);
            return this.isBlighted(metadata) && world.b(x, y, z, this.setBlighted(metadata, false), 2);
        }
    }

    @Stealing
    private int setBlighted(int metadata, boolean b) {
        return 0;
    }

    @Stealing
    private boolean isBlighted(int metadata) {
        return false;
    }
}
