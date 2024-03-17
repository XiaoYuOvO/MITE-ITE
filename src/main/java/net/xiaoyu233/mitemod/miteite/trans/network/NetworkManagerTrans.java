package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.NetHandler;
import net.xiaoyu233.mitemod.miteite.api.ITENetHandler;
import net.xiaoyu233.mitemod.miteite.network.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetHandler.class)
public class NetworkManagerTrans implements ITENetHandler{
   public void handleCraftingBoost(SPacketCraftingBoost packet) {
   }

   public void handleOverlayMessage(SPacketOverlayMessage packet) {
   }

   public void handleSyncItems(CPacketSyncItems packet) {
   }

   public void processFinishForgingPacket(SPacketFinishForging packet) {
   }

   public void processForgingTableInfoPacket(SPacketForgingTableInfo packet) {
   }

   public void processStartForgingPacket(CPacketStartForging packet) {
   }

   public void handleUpdateDefense(BiPacketUpdateDefense packet){
   }

   public void handleUpdateNutrition(SPacketUpdateNutrition packet){
   }
}
