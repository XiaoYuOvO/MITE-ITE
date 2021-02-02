package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(bcw.class)
public class ClientNetworkManagerTrans {
    @Link
    private Minecraft h;
    @Link
    private bdd i;

    public void a(Packet8UpdateHealth par1Packet8UpdateHealth) {
        this.h.h.n(par1Packet8UpdateHealth.a);
        this.h.h.bI().setSatiation(par1Packet8UpdateHealth.satiation, false);
        this.h.h.bI().setNutrition(par1Packet8UpdateHealth.nutrition, false);
        this.h.h.setPhytonutrients(par1Packet8UpdateHealth.phytonutrients);
        this.h.h.setProtein(par1Packet8UpdateHealth.protein);
        if (this.h.h.vision_dimming < par1Packet8UpdateHealth.vision_dimming) {
            this.h.h.vision_dimming = par1Packet8UpdateHealth.vision_dimming;
        }

    }

    public void a(Packet24MobSpawn par1Packet24MobSpawn) {
        double var2 = SpatialScaler.getPosX(par1Packet24MobSpawn.scaled_pos_x);
        double var4 = SpatialScaler.getPosY(par1Packet24MobSpawn.scaled_pos_y);
        double var6 = SpatialScaler.getPosZ(par1Packet24MobSpawn.scaled_pos_z);
        float var8 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_yaw);
        float var9 = SpatialScaler.getRotation(par1Packet24MobSpawn.scaled_pitch);
        EntityInsentient var10 = (EntityInsentient)EntityTypes.a(par1Packet24MobSpawn.b, this.h.f);
        if (var10 != null){
            var10.aP = (float)(par1Packet24MobSpawn.scaled_head_yaw * 360) / 256.0F;
            Entity[] var11 = var10.ao();
            if (var11 != null) {
                int var12 = par1Packet24MobSpawn.entity_id - var10.k;
                for (Entity entity : var11) {
                    entity.k += var12;
                }
            }

            var10.k = par1Packet24MobSpawn.entity_id;
            var10.v().a(par1Packet24MobSpawn.c());
            var10.onSendToClient(par1Packet24MobSpawn);
            var10.a(var2, var4, var6, var8, var9);
            var10.aO = var10.aN = var8;
            var10.aQ = var10.aP;
            var10.x = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_x);
            var10.y = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_y);
            var10.z = SpatialScaler.getMotion(par1Packet24MobSpawn.scaled_motion_z);
            if (par1Packet24MobSpawn.is_decoy && var10 instanceof EntityInsentient) {
                var10.setAsDecoy();
            }
            this.i.a(par1Packet24MobSpawn.entity_id, var10);
        }else {
            Minecraft.MITE_log.b("Bad mob spawning packet with entity_id:" + par1Packet24MobSpawn.b);
        }
    }
}
