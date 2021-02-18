package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketFinishForging extends Packet {
    private Status finishStatus;
    public SPacketFinishForging() {

    }

    public SPacketFinishForging(Status status){
        finishStatus = status;
    }
    @Override
    public void a(DataInput var1) throws IOException {
        this.finishStatus = Status.values()[var1.readInt()];
    }

    @Override
    public void a(DataOutput var1) throws IOException {
        var1.writeInt(this.finishStatus.ordinal());
    }

    @Override
    public void a(Connection var1) {
        var1.processFinishForgingPacket(this);
    }

    @Override
    public int a() {
        return 4;
    }

    public enum Status {
        COMPLETED,
        FAILED,
        CANCELED
    }
}
