package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Packet100OpenWindow.class)
public class PacketOpenWindowTrans {
   @Unique
   private static final int TYPE_FORGING_TABLE = 14;
   @Shadow
   public int inventoryType;
   @Shadow
   public int slotsCount;
   @Shadow
   public boolean useProvidedWindowTitle;
   @Shadow
   public int windowId;
   @Shadow
   public String windowTitle;
   @Shadow
   public int x;
   @Shadow
   public int y;
   @Shadow
   public int z;

   @Inject(method = "handleOpenWindow", at = @At("HEAD"), cancellable = true)
   public void handleOpenWindow(EntityClientPlayerMP player, CallbackInfo ci) {
      if (this.inventoryType == TYPE_FORGING_TABLE) {
         player.displayGUIForgingTable(this.x, this.y, this.z, new ForgingTableSlots(new InventoryBasic(this.windowTitle, this.useProvidedWindowTitle, this.slotsCount)));
         player.openContainer.windowId = this.windowId;
         ci.cancel();
      }
   }

   @ModifyConstant(method = "readPacketData", constant = @Constant(intValue = 32))
   public int readPacketData(int constant) {
      return 32767;
   }
}
