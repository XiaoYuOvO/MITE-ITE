package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockPortal.class)
public class BlockPortalTrans extends Block {
   protected BlockPortalTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,method = "onEntityCollidedWithBlock",at = @At(value = "INVOKE",shift = At.Shift.BEFORE,target = "Lnet/minecraft/BlockPortal;initiateRunegateTeleport(Lnet/minecraft/WorldServer;IIILnet/minecraft/ServerPlayer;Z)V"))
   private void injectTeleportTips(World par1World, int par2, int par3, int par4, Entity par5Entity, CallbackInfo ci, EntityPlayer player, int metadata, boolean is_runegate, boolean is_portal_to_world_spawn){
      if ((Configs.Misc.LOG_PLAYERS_INTERACT_WITH_PORTAL.get())) {
         ((WorldServer)par1World).p().getConfigurationManager().sendChatMsg(ChatMessage.createFromTranslationWithSubstitutions("gameplay.portal_door.interact", player.getEntityName()).setColor(EnumChatFormat.DARK_RED));
      }
   }

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
