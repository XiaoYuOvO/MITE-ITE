package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.minecraft.Packet;
import net.minecraft.Packet8UpdateHealth;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Transform(Packet8UpdateHealth.class)
public class SPacketUpdateHealthTrans extends Packet {
    @Link
    public float a;
    @Link
    public int satiation;
    @Link
    public int nutrition;
    @Link
    public float vision_dimming;
    public int protein,phytonutrients;

    public SPacketUpdateHealthTrans() {
    }

    public SPacketUpdateHealthTrans(float health, int satiation, int nutrition,int protein,int phytonutrients, float vision_dimming) {
        this.a = health;
        this.satiation = satiation;
        this.nutrition = nutrition;
        this.vision_dimming = vision_dimming;
        this.protein = protein;
        this.phytonutrients = phytonutrients;
    }

    public void a(DataInput par1DataInput) throws IOException {
        this.a = par1DataInput.readFloat();
        this.satiation = par1DataInput.readByte();
        this.nutrition = par1DataInput.readByte();
        this.vision_dimming = par1DataInput.readFloat();
        this.protein = par1DataInput.readInt();
        this.phytonutrients = par1DataInput.readInt();
    }

    public void a(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeFloat(this.a);
        par1DataOutput.writeByte(this.satiation);
        par1DataOutput.writeByte(this.nutrition);
        par1DataOutput.writeFloat(this.vision_dimming);
        par1DataOutput.writeInt(this.protein);
        par1DataOutput.writeInt(this.phytonutrients);
    }

    @Marker
    public void a(Connection par1NetHandler) {
        par1NetHandler.a(this);
    }

    @Marker
    public int a() {
        return 10;
    }

    @Marker
    public boolean e() {
        return true;
    }

    @Marker
    public boolean a(Packet par1Packet) {
        return true;
    }
}
