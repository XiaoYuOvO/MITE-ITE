package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(axk.class)
public class GuiCraftingTableTrans extends axp {
   private static final String BOOST_INFO = LocaleI18n.translateToLocal("container.workbench.boost");

   public GuiCraftingTableTrans(Container par1Container) {
      super(par1Container);
   }

   @Shadow
   protected void a(float v, int i, int i1) {
   }

   @Inject(method = "a",at = @At(value = "INVOKE",target = "Lnet/minecraft/Container;getSlot(I)Lnet/minecraft/Slot;",shift = At.Shift.BEFORE),cancellable = true)
   private void injectRemoveChooseQualityIcon(float par1, int par2, int par3, CallbackInfo callback){
      //Do nothing,just cancel
      callback.cancel();
   }

   @Overwrite
   protected void b(int par1, int par2) {
      ContainerWorkbench container_workbench = (ContainerWorkbench)this.e;
      String var3 = Translator.get("tile.toolbench." + BlockWorkbench.getToolMaterial(container_workbench.getBlockMetadata()) + ".name");
      this.o.b(var3, 29, 6, 4210752);
      this.o.b(bkb.a("container.inventory"), 7, this.d - 96 + 3, 4210752);
      if (this.e.player.getCraftingBoostFactor() != 0.0F) {
         this.o.b(BOOST_INFO + (int)(this.e.player.getCraftingBoostFactor() * 100.0F) + "%", 90, 6, 11141290);
      }
   }
}
