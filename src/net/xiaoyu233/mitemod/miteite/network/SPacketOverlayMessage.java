package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketOverlayMessage extends Packet {
    private int color;
    private String msg;
    private int time;

    public SPacketOverlayMessage() {
    }

    public SPacketOverlayMessage(String msg, int color, int time) {
        this.msg = msg;
        this.color = color;
        this.time = time;
    }

    public int getColor() {
        return this.color;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getPacketSize() {
        return 8 + Packet.getPacketSizeOfString(this.msg);
    }

    public int getTime() {
        return this.time;
    }

    public void processPacket(Connection var1) {
        var1.handleOverlayMessage(this);
    }

    public void readPacketData(DataInput var1) throws IOException {
        this.color = var1.readInt();
        this.msg = Packet.readString(var1, 32767);
        this.time = var1.readInt();
    }

    public void writePacketData(DataOutput var1) throws IOException {
        var1.writeInt(this.color);
        Packet.writeString(this.msg, var1);
        var1.writeInt(this.time);
    }
}
