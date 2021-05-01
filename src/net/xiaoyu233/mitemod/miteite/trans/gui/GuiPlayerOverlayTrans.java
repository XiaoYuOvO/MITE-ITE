package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiIngame.class)
public class GuiPlayerOverlayTrans extends avk {
   @Shadow
   @Final
   private Minecraft g;
   private String overlayMsg;
   private int overlayMsgColor;
   private int overlayMsgDisplayTime;

   public void b(int x, int y, int textureX, int textureY, int width, int height) {
      super.b(x, y, textureX, textureY, width, height);
   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "a(II)V",
           at = @At(value = "INVOKE_STRING",
                    target = "endStartSection(Ljava/lang/String;)V",
                    args = "ldc=air",
                    shift = At.Shift.BEFORE))
   private void injectRenderNutrition(int par1, int par2, CallbackInfo ci, boolean var3, int var4, int var5, FoodMetaData var7, int var8, AttributeInstance var10, int var11, int var12, int var13, float var14, float var15){
      float protein = (float)this.g.h.getProtein();
      float phytonutrients = (float)this.g.h.getPhytonutrients();
      int var25 = var13 + 32 + Configs.Client.Gui.HEALTH_BAR_Y_OFFSET.get();
      int var26 = var12 + 240 + Configs.Client.Gui.HEALTH_BAR_X_OFFSET.get();
      GL11.glPushMatrix();
      GL11.glPushMatrix();
      this.b(this.g.l, (int)phytonutrients + "/" + 160000, var26 - 167, var25 - 8, 16777215);
      GL11.glScalef(0.6F, 1.0F, 1.0F);
      this.g.J().a(Constant.icons_ite);
      this.b(var26, var25, 0, 106, 182, 6);
      this.b(var26, var25, 0, 94, (int)(182.0F * (phytonutrients / 160000.0F)), 6);
      GL11.glPopMatrix();
      var26 = var12 - 303;
      GL11.glPushMatrix();
      this.b(this.g.l, (int)protein + "/" + 160000, var26, var25 - 8, 16777215);
      GL11.glScalef(0.6F, 1.0F, 1.0F);
      this.g.J().a(Constant.icons_ite);
      this.b(var26, var25, 0, 106, 182, 6);
      this.b(var26, var25, 0, 100, (int)(182.0F * (protein / 160000.0F)), 6);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }

   @Inject(method = "a(II)V",
           at = @At(value = "INVOKE_STRING",
                   target = "endStartSection(Ljava/lang/String;)V",
                   args = "ldc=air",
                   shift = At.Shift.AFTER))
   private void injectRenderOverlayMessage(CallbackInfo c){
      avi var8 = this.g.l;
      if (this.overlayMsgDisplayTime > 0) {
         this.g.C.startSection("overlayMessage");
         awf window = new awf(this.g.u, this.g.d, this.g.e);
         int var25 = window.a();
         int var26 = window.b();
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(var25 / 2), (float)(var26 - 68), 0.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         var8.b(this.overlayMsg, -var8.a(this.overlayMsg) / 2, -4, this.overlayMsgColor);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
         this.g.C.endSection();
         --this.overlayMsgDisplayTime;
      }
   }

   @Redirect(method = "a(II)V",
           at = @At(value = "INVOKE",target = "Lnet/minecraft/AttributeInstance;getAttributeValue()D"))
   private double redirectHealthLimit(AttributeInstance att){
      return this.g.h.getHealthLimit();
   }

   public void setOverlayMsg(String overlayMsg, int displayTime, int color) {
      this.overlayMsg = overlayMsg;
      this.overlayMsgColor = color;
      this.overlayMsgDisplayTime = displayTime;
   }

}
