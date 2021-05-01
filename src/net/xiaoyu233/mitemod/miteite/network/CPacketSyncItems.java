package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CPacketSyncItems extends Packet {
    public CPacketSyncItems() {
    }

    public int getPacketSize() {
        return 1;
    }

    public void processPacket(Connection var1) {
        var1.handleSyncItems(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
    }

    public void writePacketData(DataOutput var1) throws IOException {
    }
}
