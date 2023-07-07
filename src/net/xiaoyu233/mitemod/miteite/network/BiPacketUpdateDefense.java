package net.xiaoyu233.mitemod.miteite.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.Connection;
import net.minecraft.Packet;

public class BiPacketUpdateDefense extends Packet {
    private int time;

    public BiPacketUpdateDefense() {
    }

    public BiPacketUpdateDefense(int time) {
        this.time = time;
    }

    public int getPacketSize() {
        return 2;
    }

    public int getTime() {
        return this.time;
    }

    public void processPacket(Connection connection) {
        connection.handleUpdateDefense(this);
    }

    public void readPacketData(DataInput dataInput) throws IOException {
        this.time = dataInput.readInt();
    }

    public void writePacketData(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.time);
    }
}

