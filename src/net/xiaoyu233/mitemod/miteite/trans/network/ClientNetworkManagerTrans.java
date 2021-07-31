package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(NetClientHandler.class)
public class ClientNetworkManagerTrans extends NetworkManagerTrans{
   @Shadow
   private Minecraft h;
   @Shadow
   private bdd i;

   @SoftOverride
   public void handleCraftingBoost(SPacketCraftingBoost packet) {
      this.h.h.setCraftingBoostFactor(packet.getFactor(), null);
   }

   @Overwrite
   public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn) {
      double var2 = SpatialScaler.getPosX(par1Packet24MobSpawn.scaled_pos_x);
      double var4 = SpatialScaler.getPosY(par1Packet24MobSpawn.scaled_pos_y);
      double var6 = SpatialScaler.getPosZ(par1Packet24MobSpawn.scaled_pos_z);
      float var8 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_yaw);
      float var9 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_pitch);
      EntityInsentient var10 = (EntityInsentient)EntityTypes.createEntityByID(par1Packet24MobSpawn.type, this.h.f);
      if (var10 != null) {
         var10.rotationYawHead = (float)(par1Packet24MobSpawn.scaled_head_yaw * 360) / 256.0F;
         Entity[] var11 = var10.getParts();
         if (var11 != null) {
            int var12 = par1Packet24MobSpawn.entity_id - var10.entityId;

            for (Entity entity : var11) {
               entity.entityId += var12;
            }
         }

         var10.entityId = par1Packet24MobSpawn.entity_id;
         var10.getDataWatcher().a(par1Packet24MobSpawn.c());
         var10.onSendToClient(par1Packet24MobSpawn);
         var10.setPositionAndRotation(var2, var4, var6, var8, var9);
         var10.prevRenderYawOffset = var10.renderYawOffset = var8;
         var10.prevRotationYawHead = var10.rotationYawHead;
         var10.motionX = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_x);
         var10.motionY = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_y);
         var10.motionZ = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_z);
         if (par1Packet24MobSpawn.is_decoy) {
            var10.setAsDecoy();
         }

         this.i.a(par1Packet24MobSpawn.entity_id, var10);
      } else {
         Minecraft.MITE_log.logWarning("Bad mob spawning packet with entity_id:" + par1Packet24MobSpawn.type);
      }

   }

   @SoftOverride
   public void handleOverlayMessage(SPacketOverlayMessage packet) {
      this.h.r.setOverlayMsg(packet.getMsg(), packet.getTime(), packet.getColor());
   }

   public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth) {
      this.h.h.n(par1Packet8UpdateHealth.healthMP);
      this.h.h.getFoodStats().setSatiation(par1Packet8UpdateHealth.satiation, false);
      this.h.h.getFoodStats().setNutrition(par1Packet8UpdateHealth.nutrition, false);
      this.h.h.setPhytonutrients(par1Packet8UpdateHealth.phytonutrients);
      this.h.h.setProtein(par1Packet8UpdateHealth.protein);
      if (this.h.h.vision_dimming < par1Packet8UpdateHealth.vision_dimming) {
         this.h.h.vision_dimming = par1Packet8UpdateHealth.vision_dimming;
      }

   }

   public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems) {
      ClientPlayer var2 = this.h.h;
      if (par1Packet104WindowItems.windowId == 0) {
         var2.inventoryContainer.a(par1Packet104WindowItems.itemStack);
      } else if (par1Packet104WindowItems.windowId == var2.openContainer.windowId) {
         var2.openContainer.a(par1Packet104WindowItems.itemStack);
      }

      this.h.h.itemsSynced();
   }

   @SoftOverride
   public void processFinishForgingPacket(SPacketFinishForging packet) {
      awe openingGUI = this.h.n;
      if (openingGUI instanceof GuiForgingTable) {
         ((GuiForgingTable)openingGUI).enableButton();
      }

   }

   @Override
   public void handleUpdateDefense(BiPacketUpdateDefense packet) {
      this.h.h.setDefenseCooldown(packet.getTime());
   }

   @SoftOverride
   public void processForgingTableInfoPacket(SPacketForgingTableInfo packet) {
      awe openingGUI = this.h.n;
      if (openingGUI instanceof GuiForgingTable) {
         if (packet.getInfo() instanceof SPacketForgingTableInfo.EnhanceInfo) {
            SPacketForgingTableInfo.EnhanceInfo info = (SPacketForgingTableInfo.EnhanceInfo)packet.getInfo();
            ((GuiForgingTable)openingGUI).setEnhanceInfo(info.getChanceOfFailure(), info.getFaultFeedbacks(), info.getDuration(), info.getHammerDurabilityCost(), info.getAxeDurabilityCost());
         } else {
            ((GuiForgingTable)openingGUI).setInfo(packet.getInfo().asString(), packet.getInfo().getColor());
         }
      }

   }
}
