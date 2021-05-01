package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketCraftingBoost extends Packet {
    private float factor;

    public SPacketCraftingBoost() {
    }

    public SPacketCraftingBoost(float factor) {
        this.factor = factor;
    }

    public float getFactor() {
        return this.factor;
    }

    public int getPacketSize() {
        return 4;
    }

    public void processPacket(Connection var1) {
        var1.handleCraftingBoost(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
        this.factor = var1.readFloat();
    }

    public void writePacketData(DataOutput var1) throws IOException {
        var1.writeFloat(this.factor);
    }
}
