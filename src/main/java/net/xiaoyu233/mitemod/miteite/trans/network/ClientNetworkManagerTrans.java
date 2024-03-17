package net.xiaoyu233.mitemod.miteite.trans.network;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEClientPlayer;
import net.xiaoyu233.mitemod.miteite.api.ITENetHandler;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public abstract class ClientNetworkManagerTrans extends NetHandler implements ITENetHandler {
   @Shadow
   private Minecraft mc;

   @SoftOverride
   public void handleCraftingBoost(SPacketCraftingBoost packet) {
      this.mc.thePlayer.setCraftingBoostFactor(packet.getFactor(), null);
   }

   @Inject(method = "handleMobSpawn", at = @At(value = "FIELD", target = "Lnet/minecraft/EntityLiving;rotationYawHead:F", shift = At.Shift.BEFORE, ordinal = 0))
   public void injectDenyBadSpawn(Packet24MobSpawn par1Packet24MobSpawn, CallbackInfo ci, @Local EntityLiving living) {
       if (living == null) {
          Minecraft.MITE_log.logWarning("Bad mob spawning packet with entity_id:" + par1Packet24MobSpawn.type);
       }
   }

   @SoftOverride
   public void handleOverlayMessage(SPacketOverlayMessage packet) {
      this.mc.ingameGUI.setOverlayMsg(packet.getMsg(), packet.getTime(), packet.getColor());
   }

   @Inject(method = "handleWindowItems", at = @At("RETURN"))
   public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems, CallbackInfo ci) {
      this.mc.thePlayer.itemsSynced();
   }

   @SoftOverride
   public void processFinishForgingPacket(SPacketFinishForging packet) {
      GuiScreen openingGUI = this.mc.currentScreen;
      if (openingGUI instanceof GuiForgingTable) {
         ((GuiForgingTable)openingGUI).enableButton();
      }

   }

   @Override
   public void handleUpdateDefense(BiPacketUpdateDefense packet) {
      this.mc.thePlayer.setDefenseCooldown(packet.getTime());
   }

   @Override
   @Unique
   public void handleUpdateNutrition(SPacketUpdateNutrition packet){
      ITEClientPlayer clientPlayer = this.mc.thePlayer;
      clientPlayer.setPhytonutrients(packet.getPhytonutrients());
      clientPlayer.setProtein(packet.getProtein());
   }


   @SoftOverride
   public void processForgingTableInfoPacket(SPacketForgingTableInfo packet) {
      GuiScreen openingGUI = this.mc.currentScreen;
      if (openingGUI instanceof GuiForgingTable) {
         if (packet.getInfo() instanceof SPacketForgingTableInfo.EnhanceInfo) {
            SPacketForgingTableInfo.EnhanceInfo info = (SPacketForgingTableInfo.EnhanceInfo)packet.getInfo();
            ((GuiForgingTable)openingGUI).setEnhanceInfo(info.getChanceOfFailure(), info.getFaultFeedbacks(), info.getDuration(), info.getHammerDurabilityCost(), info.getAxeDurabilityCost());
         } else {
            ((GuiForgingTable)openingGUI).setInfo(packet.getInfo().asString(), packet.getInfo().getColor());
         }
      }

   }
}
