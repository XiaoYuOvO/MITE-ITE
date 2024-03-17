package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.NetHandler;
import net.minecraft.Packet;
import net.minecraft.Packet24MobSpawn;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Packet24MobSpawn.class)
public abstract class SPacketMobSpawnTrans extends Packet {
   @Redirect(method = "processPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/NetHandler;handleMobSpawn(Lnet/minecraft/Packet24MobSpawn;)V"))
   public void processPacket(NetHandler instance, Packet24MobSpawn par1Packet24MobSpawn) {
      //Safe man, safe
      try {
         instance.handleMobSpawn(ReflectHelper.dyCast(Packet24MobSpawn.class, this));
      } catch (NullPointerException var3) {
         var3.printStackTrace();
      }
   }
}
