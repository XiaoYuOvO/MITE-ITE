package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.GuiInventory;
import net.minecraft.InventoryEffectRenderer;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiInventory.class})
public abstract class GuiPlayerInventoryTrans extends InventoryEffectRenderer {
   public GuiPlayerInventoryTrans(Container par1Container) {
      super(par1Container);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectSyncItems(EntityPlayer par1EntityPlayer, CallbackInfo ci){
      par1EntityPlayer.sendPacket(new CPacketSyncItems());
   }
}