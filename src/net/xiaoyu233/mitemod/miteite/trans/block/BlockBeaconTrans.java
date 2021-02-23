package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(BlockBeacon.class)
public class BlockBeaconTrans extends BlockContainer {
    @Marker
    protected BlockBeaconTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    public void a(World world, int x, int y, int z, int block_id, int metadata) {
        TileEntityBeacon beacon = (TileEntityBeacon)world.r(x, y, z);
        beacon.clearAllPlayersBoost();
        world.s(x,y,z);
    }

    @Override
    @Marker
    public TileEntity b(World world) {
        return null;
    }
}
