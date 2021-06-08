package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.xiaoyu233.mitemod.miteite.network.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Connection.class)
public class NetworkManagerTrans {
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
}
