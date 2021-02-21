package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;

import java.util.ArrayList;

@Transform(PlayerConnection.class)
public class ServerNetworkManagerTrans extends Connection {
    @Link
    public EntityPlayer c;

    @Override
    @Marker
    public boolean a() {
        return false;
    }

    @Override
    @Marker
    public INetworkManager getNetManager() {
        return null;
    }

    public void processStartForgingPacket(CPacketStartForging packet){
        if (this.c.bp instanceof ContainerForgingTable){
            ((ContainerForgingTable) this.c.bp).startForging();
        }
    }

    public void handleSyncItems(CPacketSyncItems packet){
        ArrayList<ItemStack> itemList = new ArrayList<>();

        for(int index = 0; index < c.bp.c.size(); ++index) {
            itemList.add(((Slot)c.bp.c.get(index)).d());
        }
        c.a(c.bp, itemList);
    }
}
