package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
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
}
