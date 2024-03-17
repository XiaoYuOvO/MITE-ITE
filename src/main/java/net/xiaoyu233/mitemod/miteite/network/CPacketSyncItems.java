package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.NetHandler;
import net.minecraft.Packet;
import net.xiaoyu233.mitemod.miteite.api.ITENetHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CPacketSyncItems extends Packet {
    public CPacketSyncItems() {
    }

    public int getPacketSize() {
        return 1;
    }

    public void processPacket(NetHandler var1) {
        ((ITENetHandler) var1).handleSyncItems(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
    }

    public void writePacketData(DataOutput var1) throws IOException {
    }
}
