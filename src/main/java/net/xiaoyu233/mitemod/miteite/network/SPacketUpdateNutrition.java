package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.NetHandler;
import net.minecraft.Packet;
import net.xiaoyu233.mitemod.miteite.api.ITENetHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketUpdateNutrition extends Packet {
    private int protein;
    private int phytonutrients;
    public SPacketUpdateNutrition(){}
    public SPacketUpdateNutrition(int phytonutrients, int protein){
        this.phytonutrients = phytonutrients;
        this.protein = protein;
    }

    public int getPhytonutrients() {
        return phytonutrients;
    }

    public int getProtein() {
        return protein;
    }

    @Override
    public void readPacketData(DataInput dataInput) throws IOException {
        this.protein = dataInput.readInt();
        this.phytonutrients = dataInput.readInt();
    }

    @Override
    public void writePacketData(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.protein);
        dataOutput.writeInt(this.phytonutrients);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((ITENetHandler) netHandler).handleUpdateNutrition(this);
    }

    @Override
    public int getPacketSize() {
        return 8;
    }
}
