package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

import java.util.ArrayList;

@Mixin(PlayerConnection.class)
public class ServerNetworkManagerTrans extends NetworkManagerTrans {
   @Shadow
   public ServerPlayer playerEntity;

   @Shadow
   public INetworkManager getNetManager() {
      return null;
   }

   @SoftOverride
   public void handleSyncItems(CPacketSyncItems packet) {
      ArrayList<ItemStack> itemList = new ArrayList<>();

      for(int index = 0; index < this.playerEntity.openContainer.inventorySlots.size(); ++index) {
         itemList.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(index)).getStack());
      }

      this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, itemList);
   }

   @Shadow
   public boolean isServerHandler() {
      return false;
   }

   @SoftOverride
   public void processStartForgingPacket(CPacketStartForging packet) {
      if (this.playerEntity.openContainer instanceof ContainerForgingTable) {
         ((ContainerForgingTable)this.playerEntity.openContainer).startForging();
      }

   }
}
