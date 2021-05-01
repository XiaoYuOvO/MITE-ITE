package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketFinishForging extends Packet {
    private SPacketFinishForging.Status finishStatus;

    public SPacketFinishForging() {
    }

    public SPacketFinishForging(SPacketFinishForging.Status status) {
        this.finishStatus = status;
    }

    public int getPacketSize() {
        return 4;
    }

    public void processPacket(Connection var1) {
        var1.processFinishForgingPacket(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
        this.finishStatus = SPacketFinishForging.Status.values()[var1.readInt()];
    }

    public void writePacketData(DataOutput var1) throws IOException {
        var1.writeInt(this.finishStatus.ordinal());
    }

    public enum Status {
        COMPLETED,
        FAILED,
        CANCELED
    }
}
