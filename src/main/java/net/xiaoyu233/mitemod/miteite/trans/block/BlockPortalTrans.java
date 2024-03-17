package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEPortal;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockPortal.class)
public abstract class BlockPortalTrans extends Block implements ITEPortal {
    @Shadow
    public abstract BlockRunestone getRunegateType(World world, int x, int y, int z);

   @Shadow protected abstract int getRunegateSeed(World world, int x, int y, int z);

   @Unique
   private BlockRunestone mITE_ITEPlugin$runestoneOverride;
   private int mITE_ITEPlugin$portalSeedOverride;
   @ModifyConstant(method = "getRunegateDestinationCoords", constant = @Constant(intValue = 254, ordinal = 0))
   private int modifyNetherMaxPortalHeight(int original){
      return 123;
   }


   @Inject(method = "getRunegateDestinationCoords", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldServer;isAirOrPassableBlock(IIIZ)Z", ordinal = 6, shift = At.Shift.BEFORE), cancellable = true)
   private void injectUnderworldTeleportationProtect(WorldServer world, int x, int y, int z, CallbackInfoReturnable<int[]> cir){
      if (world.isUnderworld()) {
         int minY = Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 5;
         if (!world.isAirOrPassableBlock(x, minY, z, true)) {
            world.setBlockToAir(x, minY, z);
         }

         int headY = Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get() + 6;
         if (!world.isAirOrPassableBlock(x, headY, z, true)) {
            world.setBlockToAir(x, headY, z);
         }
         cir.setReturnValue(new int[]{x, minY, z});
      }
   }

   @Redirect(method = "getRunegateDestinationCoords", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockPortal;getRunegateType(Lnet/minecraft/World;III)Lnet/minecraft/BlockRunestone;"))
   private BlockRunestone redirectOverrideRuneType(BlockPortal instance, World world, int x, int y, int z){
      if (this.mITE_ITEPlugin$runestoneOverride != null){
         BlockRunestone cached = this.mITE_ITEPlugin$runestoneOverride;
         this.mITE_ITEPlugin$runestoneOverride = null;
         return cached;
      }else {
         return getRunegateType(world,x,y,z);
      }
   }

   @Redirect(method = "getRunegateDestinationCoords", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockPortal;getRunegateSeed(Lnet/minecraft/World;III)I"))
   private int redirectOverrideRuneSeed(BlockPortal instance, World world, int x, int y, int z){
      if (this.mITE_ITEPlugin$portalSeedOverride != 0){
         int cached = this.mITE_ITEPlugin$portalSeedOverride;
         this.mITE_ITEPlugin$portalSeedOverride = 0;
         return cached;
      }else {
         return getRunegateSeed(world,x,y,z);
      }
   }

   protected BlockPortalTrans(int par1, Material par2Material, BlockConstants constants) {
      super(par1, par2Material, constants);
   }

   @Inject(
           locals = LocalCapture.CAPTURE_FAILHARD,
           method = "onEntityCollidedWithBlock",at = @At(value = "INVOKE",shift = At.Shift.BEFORE,target = "Lnet/minecraft/BlockPortal;initiateRunegateTeleport(Lnet/minecraft/WorldServer;IIILnet/minecraft/ServerPlayer;Z)V"))
   private void injectTeleportTips(World par1World, int par2, int par3, int par4, Entity par5Entity, CallbackInfo ci, EntityPlayer player, int metadata, boolean is_runegate, boolean is_portal_to_world_spawn){
      if ((Configs.Misc.LOG_PLAYERS_INTERACT_WITH_PORTAL.get())) {
         ((WorldServer)par1World).getMinecraftServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromTranslationWithSubstitutions("gameplay.portal_door.interact", player.getEntityName()).setColor(EnumChatFormatting.DARK_RED));
      }
   }

   @Override
   @Unique
   public void setRuneTypeOverride(BlockRunestone runeTypeOverride) {
      mITE_ITEPlugin$runestoneOverride = runeTypeOverride;
   }

   @Override
   @Unique
   public void setPortalSeedOverride(int newSeed) {
      mITE_ITEPlugin$portalSeedOverride = newSeed;
   }
}
