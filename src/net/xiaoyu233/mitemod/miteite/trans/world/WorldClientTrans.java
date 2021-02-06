package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.minecraft.client.main.Main;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;

import java.util.Set;

import static net.xiaoyu233.mitemod.miteite.util.Config.ConfigEntry.FIRST_DAY_LONGER_DAY_TIME;

@Transform(bdd.class)
public class WorldClientTrans extends World{
    @Link
    private bcw a;
    @Link
    private bdb b;
    @Link
    private IntHashMap c;
    @Link
    private Set J;
    @Link
    private Set K;
    @Link
    private Minecraft L;
    @Link
    private Set M;
    @Link
    public boolean tick_has_passed;
    private boolean pushTimeNextTick;

    @Marker
    public WorldClientTrans(IDataManager par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, MethodProfiler par5Profiler, IConsoleLogManager par6ILogAgent, long world_creation_time, long total_world_time) {
        super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
    }

    @Override
    @Marker
    protected IChunkProvider j() {
        return null;
    }

    public void b() {
        if (Minecraft.java_version_is_outdated) {
            Minecraft.O.h = null;
            Minecraft.O = null;
        }

        int var1;
        if (Main.is_MITE_DS && !this.L.A) {
            this.a.e();
            var1 = this.a.g().clearReceivedPackets();
            if (var1 > 0) {
                System.out.println(var1 + " packets cleared from the dedicated server received queue");
            }

        } else {
            super.b();
            if (MITEITEMod.CONFIG.get(FIRST_DAY_LONGER_DAY_TIME) && this.getDayOfWorld() == 1 && this.I() < 12000){
                if (pushTimeNextTick){
                    this.setTotalWorldTime(this.I() + 1L);
                    pushTimeNextTick = false;
                }else {
                    pushTimeNextTick = true;
                }
            }else {
                this.setTotalWorldTime(this.I() + 1L);
            }
            this.j = this.a(1.0F);
            this.C.a("reEntryProcessing");

            for(var1 = 0; var1 < 10 && !this.K.isEmpty(); ++var1) {
                Entity var2 = (Entity)this.K.iterator().next();
                this.K.remove(var2);
                if (!this.e.contains(var2)) {
                    this.d(var2);
                }
            }

            this.C.c("connection");
            this.a.e();
            this.C.c("chunkCache");
            this.b.c();
            this.C.c("tiles");
            this.g();
            this.C.b();
            this.tick_has_passed = true;
        }
    }

    @Override
    @Marker
    public Entity a(int var1) {
        return null;
    }

}
