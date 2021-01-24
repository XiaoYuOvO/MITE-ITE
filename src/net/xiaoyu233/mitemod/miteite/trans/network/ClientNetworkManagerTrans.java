package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Minecraft;
import net.minecraft.Packet8UpdateHealth;
import net.minecraft.bcw;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(bcw.class)
public class ClientNetworkManagerTrans {
    @Link
    private Minecraft h;

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
}
