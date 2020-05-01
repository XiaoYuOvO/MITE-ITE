package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityRideMarker;

@Transform(BlockStairs.class)
public class BlockStairsTrans extends Block {
    @Marker
    protected BlockStairsTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if (player.o instanceof EntityRideMarker && player.getBlockPosX() == x && player.getBlockPosY() == y && player.getBlockPosZ() == z){
            return false;
        }
        int metadata = world.h(x, y, z);
        if ((metadata & 0b100) != 4) {
            if (player.onServer()) {
                EntityRideMarker rideMarker = new EntityRideMarker(world);
                float rotation = 0.0f;
                switch (metadata) {
                    case 0:
                        rotation = 90f;
                        break;
                    case 1:
                        rotation = -90f;
                        break;
                    case 2:
                        rotation = 180f;
                        break;
                    case 3:
                        rotation = 0f;
                        break;
                }
                player.A = rotation % 360.0F;
                rideMarker.A = rotation % 360.0F;
                rideMarker.a((double) x + 0.5d, 0.5d + y,(double) z + 0.5d);
                world.d(rideMarker);
                rideMarker.p = true;
                player.a(x, y, z);
                player.a((Entity) rideMarker);

            }
            return true;
        }else {
            return false;
        }
    }
}
