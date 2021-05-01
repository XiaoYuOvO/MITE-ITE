package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockCrops;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockCrops.class)
public class BlockCropTrans {
   @Overwrite
   public boolean fertilize(World world, int x, int y, int z, ItemStack item_stack) {
      Item item = item_stack.getItem();
      if (item != Item.dyePowder && item != Item.manure) {
         return false;
      } else {
         int metadata = world.getBlockMetadata(x, y, z);
         return this.isBlighted(metadata) && world.setBlockMetadata(x, y, z, this.setBlighted(metadata, false), 2);
      }
   }

   @Shadow
   private boolean isBlighted(int metadata) {
      return false;
   }

   @Shadow
   private int setBlighted(int metadata, boolean b) {
      return 0;
   }
}
