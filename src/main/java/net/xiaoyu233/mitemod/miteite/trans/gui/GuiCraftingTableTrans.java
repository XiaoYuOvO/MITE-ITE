package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCrafting.class)
public class GuiCraftingTableTrans extends InventoryEffectRenderer {
   @Unique
   private static final String BOOST_INFO = StatCollector.translateToLocal("container.workbench.boost");

   public GuiCraftingTableTrans(Container par1Container) {
      super(par1Container);
   }

   @Shadow
   protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
   }

   @Inject(
           method = {"drawGuiContainerBackgroundLayer(FII)V"},
           at = {@At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/Container;getSlot(I)Lnet/minecraft/Slot;",
                   shift = At.Shift.BEFORE
           )},
           cancellable = true
   )
   private void injectRemoveChooseQualityIcon(float par1, int par2, int par3, CallbackInfo callback) {
      callback.cancel();
   }

   @Inject(method = "drawGuiContainerForegroundLayer", at = @At("RETURN"))
   protected void drawGuiContainerForegroundLayer(int par1, int par2, CallbackInfo ci) {
      float craftingBoostFactor = this.inventorySlots.player.getCraftingBoostFactor();
      if (craftingBoostFactor != 0.0F) {
         this.fontRenderer.drawString(BOOST_INFO + (int)(craftingBoostFactor * 100.0F) + "%", 90, 6, 11141290);
      }
   }
}
