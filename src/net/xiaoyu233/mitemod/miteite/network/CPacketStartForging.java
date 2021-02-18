package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CPacketStartForging extends Packet {
    public CPacketStartForging() {

    }
    @Override
    public void a(DataInput var1) throws IOException {

    }

    @Override
    public void a(DataOutput var1) throws IOException {

    }

    @Override
    public void a(Connection var1) {
        var1.processStartForgingPacket(this);
    }

    @Override
    public int a() {
        return 3;
    }
}
