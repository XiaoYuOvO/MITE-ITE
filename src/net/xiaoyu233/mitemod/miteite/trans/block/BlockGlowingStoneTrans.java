package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLightStone.class)
public class BlockGlowingStoneTrans extends Block {
   public BlockGlowingStoneTrans(int par1, Material par2Material) {
      super(par1, par2Material, (new BlockConstants()).setNeverConnectsWithFence());
   }

   public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
      return false;
   }
}
