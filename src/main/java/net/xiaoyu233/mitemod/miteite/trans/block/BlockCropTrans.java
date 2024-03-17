package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockCrops;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCrops.class)
public class BlockCropTrans {
   @Inject(method = "fertilize", at = @At("HEAD"), cancellable = true)
   public void injectUseManure(World world, int x, int y, int z, ItemStack item_stack, CallbackInfoReturnable<Boolean> cir) {
      Item item = item_stack.getItem();
      if (item != Item.manure) {
         int metadata = world.getBlockMetadata(x, y, z);
         cir.setReturnValue(this.isBlighted(metadata) && world.setBlockMetadataWithNotify(x, y, z, this.setBlighted(metadata, false), 2));
      }
   }

   @Shadow
   public boolean isBlighted(int metadata) {
      return false;
   }

   @Shadow
   public int setBlighted(int metadata, boolean b) {
      return 0;
   }
}
