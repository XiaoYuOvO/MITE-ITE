package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BiPacketUpdateDefense extends Packet {
    private int time;
    public BiPacketUpdateDefense(){

    }

    public BiPacketUpdateDefense(int time){
        this.time = time;
    }

    @Override
    public int getPacketSize() {
        return 2;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void processPacket(Connection connection) {
        connection.handleUpdateDefense(this);
    }

    @Override
    public void readPacketData(DataInput dataInput) throws IOException {
        this.time = dataInput.readInt();
    }

    @Override
    public void writePacketData(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(time);
    }
}
