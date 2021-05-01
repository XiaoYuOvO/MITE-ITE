package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.minecraft.Packet;
import net.minecraft.Packet24MobSpawn;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Mixin(Packet24MobSpawn.class)
public class SPacketMobSpawnTrans extends Packet {
   @Shadow
   public int getPacketSize() {
      return 0;
   }

   @Overwrite
   public void processPacket(Connection par1NetHandler) {
      try {
         par1NetHandler.handleMobSpawn(ReflectHelper.dyCast(Packet24MobSpawn.class, this));
      } catch (NullPointerException var3) {
         var3.printStackTrace();
      }

   }

   @Shadow
   public void readPacketData(DataInput var1) throws IOException {
   }

   @Shadow
   public void writePacketData(DataOutput var1) throws IOException {
   }
}
