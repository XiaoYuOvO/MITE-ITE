package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityRideMarker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockStairs.class)
public class BlockStairsTrans extends Block {
   protected BlockStairsTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   @Overwrite
   public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
      return this.isFaceFlatAndSolid(block_access.getBlockMetadata(x, y, z), EnumFace.get(MathHelper.clamp_int(side,0,5)).getOpposite());
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
      if (player.ridingEntity instanceof EntityRideMarker && player.getBlockPosX() == x && player.getBlockPosY() == y && player.getBlockPosZ() == z) {
         return false;
      } else {
         int metadata = world.getBlockMetadata(x, y, z);
         if ((metadata & 4) != 4) {
            if (player.onServer()) {
               EntityRideMarker rideMarker = new EntityRideMarker(world);
               float rotation = 0.0F;
               switch(metadata) {
               case 0:
                  rotation = 90.0F;
                  break;
               case 1:
                  rotation = -90.0F;
                  break;
               case 2:
                  rotation = 180.0F;
                  break;
               case 3:
                  rotation = 0.0F;
               }

               player.rotationYaw = rotation % 360.0F;
               rideMarker.rotationYaw = rotation % 360.0F;
               rideMarker.setPosition((double)x + 0.5D, 0.5D + (double)y, (double)z + 0.5D);
               world.spawnEntityInWorld(rideMarker);
               rideMarker.forceSpawn = true;
               player.setPosition((float)x, (float)y, (float)z);
               player.mountEntity(rideMarker);
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
