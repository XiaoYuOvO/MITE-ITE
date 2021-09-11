package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(BlockPortal.class)
public abstract class BlockPortalTrans extends Block {
   @Overwrite
   public int[] getRunegateDestinationCoords(WorldServer world, int x, int y, int z) {
      int seed = this.getRunegateSeed(world, x, y, z);
      BlockRunestone block_runestone = this.getRunegateType(world, x, y, z);
      int chunk_z;
      int runegate_domain_radius;
      if (seed == 0) {
         x = 0;
         z = 0;
      } else {
         Random random = new Random(seed);

         for(chunk_z = 0; chunk_z < 4; ++chunk_z) {
            runegate_domain_radius = world.getRunegateDomainRadius(block_runestone == Block.runestoneAdamantium ? Material.adamantium : Material.mithril);
            x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;

            for(z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius; block_runestone == Block.runestoneAdamantium && WorldServer.getDistanceFromDeltas(x, z) < (double)(runegate_domain_radius / 2); z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius) {
               x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;
            }

            if (world.getBiomeGenForCoords(x, z) != BiomeBase.ocean) {
               break;
            }
         }
      }

      int chunk_x = x >> 4;
      chunk_z = z >> 4;

      for(runegate_domain_radius = -1; runegate_domain_radius <= 1; ++runegate_domain_radius) {
         for(int dz = -1; dz <= 1; ++dz) {
            world.getChunkProvider().provideChunk(chunk_x + runegate_domain_radius, chunk_z + dz);
         }
      }

      if (world.isTheNether()) {
         y = 0;

         while(true) {
            ++y;
            if (y >= 123) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               ++y;
               if (world.isAirOrPassableBlock(x, y, z, false) && !world.isAirOrPassableBlock(x, y - 2, z, false) && !world.isLavaBlock(x, y - 2, z) && !world.isLavaBlock(x, y - 1, z)) {
                  return new int[]{x, y-1, z};
               }
            }
         }
      } else if (world.isUnderworld()) {
         y = 254;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (BlockPortal.isGoodSpotForPlayerToAppearAt(world, x, y, z)) {
               return new int[]{x, y, z};
            }
         }
      } else {
         y = 256;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               --y;
               if (world.isAirOrPassableBlock(x, y, z, false)) {
                  while(y > 0 && world.isAirOrPassableBlock(x, y - 1, z, false)) {
                     --y;
                  }

                  if (y == 0) {
                     y = 64;
                  }

                  return new int[]{x, y, z};
               }
            }
         }
      }

      if (!world.isUnderworld()){
         if (!world.isAirOrPassableBlock(x, 64, z, true)) {
            world.setBlockToAir(x, 64, z);
         }

         if (!world.isAirOrPassableBlock(x, 65, z, true)) {
            world.setBlockToAir(x, 65, z);
         }
         return new int[]{x, 64, z};
      }else {
         int minY = Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 5;
         if (!world.isAirOrPassableBlock(x, 120, z, true)) {
            world.setBlockToAir(x, minY, z);
         }

         if (!world.isAirOrPassableBlock(x, 121, z, true)) {
            world.setBlockToAir(x, Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 6, z);
         }
         return new int[]{x, minY, z};
      }
   }

   @Shadow public abstract BlockRunestone getRunegateType(World world, int x, int y, int z);

   protected BlockPortalTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "onEntityCollidedWithBlock",at = @At(value = "INVOKE",shift = At.Shift.BEFORE,target = "Lnet/minecraft/BlockPortal;initiateRunegateTeleport(Lnet/minecraft/WorldServer;IIILnet/minecraft/ServerPlayer;Z)V"))
   private void injectTeleportTips(World par1World, int par2, int par3, int par4, Entity par5Entity, CallbackInfo ci, EntityPlayer player, int metadata, boolean is_runegate, boolean is_portal_to_world_spawn){
      if ((Configs.Misc.LOG_PLAYERS_INTERACT_WITH_PORTAL.get())) {
         ((WorldServer)par1World).p().getConfigurationManager().sendChatMsg(ChatMessage.createFromTranslationWithSubstitutions("gameplay.portal_door.interact", player.getEntityName()).setColor(EnumChatFormat.DARK_RED));
      }
   }

   @Shadow protected abstract int getRunegateSeed(World world, int x, int y, int z);

   public boolean isTouchingBottomBedrock(World world, int x, int y, int z) {
      int frame_min_y = this.getFrameMinY(world, x, y, z);
      if (frame_min_y <= 8 || (world.isUnderworld() && frame_min_y <= Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 8)) {
         int frame_min_x = this.getFrameMinX(world, x, y, z);
         int frame_max_x = this.getFrameMaxX(world, x, y, z);
         int frame_min_z = this.getFrameMinZ(world, x, y, z);
         int frame_max_z = this.getFrameMaxZ(world, x, y, z);

         for (int frame_x = frame_min_x; frame_x <= frame_max_x; ++frame_x) {
            for (int frame_z = frame_min_z; frame_z <= frame_max_z; ++frame_z) {
               if (world.isBottomBlock(frame_x, frame_min_y - 1, frame_z)) {
                  return true;
               }
            }
         }

      }
      return false;
   }

   @Shadow
   private int getFrameMaxZ(World world, int x, int y, int z) {
      return 0;
   }

   @Shadow
   private int getFrameMinZ(World world, int x, int y, int z) {
      return 0;
   }

   @Shadow
   private int getFrameMaxX(World world, int x, int y, int z) {
      return 0;
   }

   @Shadow
   private int getFrameMinX(World world, int x, int y, int z) {
      return 0;
   }

   @Shadow
   private int getFrameMinY(World world, int x, int y, int z) {
      return 0;
   }
}
