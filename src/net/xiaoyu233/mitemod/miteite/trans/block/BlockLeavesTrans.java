package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLeaves.class)
public class BlockLeavesTrans extends BlockTransparant {
   protected BlockLeavesTrans(int par1, Material par2Material, boolean par3) {
      super(par1, par2Material, par3);
   }

   public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
      return false;
   }
}
