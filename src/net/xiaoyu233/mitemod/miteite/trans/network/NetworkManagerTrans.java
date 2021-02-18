package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketFinishForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketForgingTableInfo;

@Transform(Connection.class)
public class NetworkManagerTrans {
    public void processStartForgingPacket(CPacketStartForging packet){

    }

    public void processFinishForgingPacket(SPacketFinishForging packet){

    }

    public void processForgingTableInfoPacket(SPacketForgingTableInfo packet){

    }
}
