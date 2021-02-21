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
    public SPacketOverlayMessage(){}
    public SPacketOverlayMessage(String msg,int color,int time){
        this.msg = msg;
        this.color = color;
        this.time = time;
    }
    @Override
    public void a(DataInput var1) throws IOException {
        this.color = var1.readInt();
        this.msg = Packet.a(var1,32767);
        this.time = var1.readInt();
    }

    @Override
    public void a(DataOutput var1) throws IOException {
        var1.writeInt(this.color);
        Packet.a(this.msg,var1);
        var1.writeInt(this.time);
    }

    @Override
    public void a(Connection var1) {
        var1.handleOverlayMessage(this);
    }

    @Override
    public int a() {
        return  4 + 4 + Packet.getPacketSizeOfString(msg);
    }

    public int getColor() {
        return color;
    }

    public int getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }
}
