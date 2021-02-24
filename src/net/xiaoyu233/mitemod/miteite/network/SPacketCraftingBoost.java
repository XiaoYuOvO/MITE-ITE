package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketCraftingBoost extends Packet {
    private float factor;
    public SPacketCraftingBoost(){}
    public SPacketCraftingBoost(float factor){
        this.factor = factor;
    }
    @Override
    public void a(DataInput var1) throws IOException {
        factor = var1.readFloat();
    }

    public float getFactor() {
        return factor;
    }

    @Override
    public void a(DataOutput var1) throws IOException {
        var1.writeFloat(factor);
    }

    @Override
    public void a(Connection var1) {
        var1.handleCraftingBoost(this);
    }

    @Override
    public int a() {
        return 4;
    }
}
