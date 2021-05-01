package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBeacon.class)
public class BlockBeaconTrans extends BlockContainer {
   protected BlockBeaconTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   public void breakBlock(World world, int x, int y, int z, int block_id, int metadata) {
      TileEntityBeacon beacon = (TileEntityBeacon)world.getBlockTileEntity(x, y, z);
      beacon.clearAllPlayersBoost();
      world.removeBlockTileEntity(x, y, z);
   }

   @Shadow
   public TileEntity createNewTileEntity(World world) {
      return null;
   }
}
