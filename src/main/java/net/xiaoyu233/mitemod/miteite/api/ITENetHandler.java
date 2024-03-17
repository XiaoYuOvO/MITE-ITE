package net.xiaoyu233.mitemod.miteite.api;

import net.xiaoyu233.mitemod.miteite.network.*;

public interface ITENetHandler {
    default void handleCraftingBoost(SPacketCraftingBoost packet) {}

    default void handleOverlayMessage(SPacketOverlayMessage packet) {}

    default void handleSyncItems(CPacketSyncItems packet) {}

    default void processFinishForgingPacket(SPacketFinishForging packet) {}

    default void processForgingTableInfoPacket(SPacketForgingTableInfo packet) {}

    default void processStartForgingPacket(CPacketStartForging packet) {}

    default void handleUpdateDefense(BiPacketUpdateDefense packet){}

    default void handleUpdateNutrition(SPacketUpdateNutrition packet){};
}
