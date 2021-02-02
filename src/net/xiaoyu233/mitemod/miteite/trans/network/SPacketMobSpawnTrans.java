package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.Connection;
import net.minecraft.Packet;
import net.minecraft.Packet24MobSpawn;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Transform(Packet24MobSpawn.class)
public class SPacketMobSpawnTrans extends Packet {
    @Override
    @Marker
    public void a(DataInput var1) throws IOException {

    }

    @Override
    @Marker
    public void a(DataOutput var1) throws IOException {

    }

    public void a(Connection par1NetHandler) {
        //Safety First
        try {
            par1NetHandler.a(ReflectHelper.dyCast(Packet24MobSpawn.class,this));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    @Marker
    public int a() {
        return 0;
    }
}
