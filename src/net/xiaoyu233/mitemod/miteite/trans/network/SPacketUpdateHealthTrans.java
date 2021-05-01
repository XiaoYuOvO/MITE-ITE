package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.minecraft.Packet;
import net.minecraft.Packet8UpdateHealth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Mixin(Packet8UpdateHealth.class)
public class SPacketUpdateHealthTrans extends Packet {
   @Shadow
   public float healthMP;
   @Shadow
   public int nutrition;
   public int phytonutrients;
   public int protein;
   @Shadow
   public int satiation;
   @Shadow
   public float vision_dimming;

   public SPacketUpdateHealthTrans() {
   }

   public SPacketUpdateHealthTrans(float health, int satiation, int nutrition, float vision_dimming) {
      this.healthMP = health;
      this.satiation = satiation;
      this.nutrition = nutrition;
      this.vision_dimming = vision_dimming;
   }

   @Shadow
   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }

   @Shadow
   public int getPacketSize() {
      return 10;
   }

   @Inject(method = "readPacketData",
           at = @At("RETURN"))
   private void injectReadPacketData(DataInput par1DataInput,CallbackInfo c) throws IOException {
      this.protein = par1DataInput.readInt();
      this.phytonutrients = par1DataInput.readInt();
   }

   @Inject(method = "writePacketData",
           at = @At("RETURN"))
   private void injectWritePacketData(DataOutput par1DataOutput, CallbackInfo c) throws IOException {
      par1DataOutput.writeInt(this.protein);
      par1DataOutput.writeInt(this.phytonutrients);
   }

   @Shadow
   public boolean isRealPacket() {
      return true;
   }

   @Shadow
   public void processPacket(Connection par1NetHandler) {
      par1NetHandler.handleUpdateHealth(dyCast(this));
   }

   @Override
   @Shadow
   public void readPacketData(DataInput dataInput) throws IOException {

   }

   public void setPhytonutrients(int phytonutrients) {
      this.phytonutrients = phytonutrients;
   }

   public void setProtein(int protein) {
      this.protein = protein;
   }

   @Override
   @Shadow
   public void writePacketData(DataOutput dataOutput) throws IOException {

   }
}
