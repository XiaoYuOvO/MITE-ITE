package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.fml.util.Utils;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.List;

@Transform(MobSpawnerAbstract.class)
public abstract class MobSpawnerTrans {
    private static final Class<?> MOB_SPAWNER = Utils.safeMake(new MobSpawnerGetter(), MobSpawnerAbstract.class);
    @Link("b")
    public int delay;
    @Link
    private String a;
    @Link
    private List e;
    @Link
    public double c;
    @Link
    public double d;
    @Link("g")
    private int minSpawnDelay;
    @Link("h")
    private int maxSpawnDelay;
    @Link("i")
    private int spawnCount;
    @Link
    private Entity j;
    @Link
    private int k;
    @Link
    private int l;
    @Link
    private int m;

    public void g() {
        if (this.f()) {
            double var5;
            boolean playerBoost = MITEITEMod.CONFIG.get(Config.ConfigEntry.MOB_SPAWNER_SPEED_UP_WITH_PLAYERS) && !this.a().a(EntityHuman.class, AxisAlignedBB.a().a(this.b(), this.c(), this.d(), this.b() + 1, this.c() + 1, this.d() + 1).b(this.m * 2, this.m, this.m * 2)).isEmpty();
            if (this.a().I) {
                double var1 = (float) this.b() + this.a().s.nextFloat();
                double var3 = (float) this.c() + this.a().s.nextFloat();
                var5 = (float) this.d() + this.a().s.nextFloat();
                this.a().spawnParticle(EnumParticle.smoke, var1, var3, var5, 0.0D, 0.0D, 0.0D);
                this.a().spawnParticle(EnumParticle.flame, var1, var3, var5, 0.0D, 0.0D, 0.0D);
                if (this.delay > 0) {
                    if (playerBoost) {
                        this.delay -= 2;
                    }
                    --this.delay;
                }

                this.d = this.c;
                this.c = (this.c + (double) (1000.0F / ((float) this.delay + 200.0F))) % 360.0D;
            } else {
                if (this.delay <= -1) {
                    this.j();
                }

                if (this.delay > 0) {
                    if (playerBoost) {
                        this.delay -= 2;
                    }
                    --this.delay;
                    return;
                }

                //Check players


                boolean var12 = false;
                int var2 = 0;

                while (true) {
                    if (var2 >= this.spawnCount) {
                        if (var12) {
                            this.j();
                        } else {
                            this.delay += 10;
                        }
                        break;
                    }

                    Entity var13 = EntityTypes.a(this.e(), this.a());
                    if (var13 == null) {
                        return;
                    }

                    int var4 = this.a().a(var13.getClass(), AxisAlignedBB.a().a(this.b(), this.c(), this.d(), this.b() + 1, this.c() + 1, this.d() + 1).b(this.m * 2, 4.0D, this.m * 2)).size();
                    if (var4 >= this.k) {
                        this.j();
                        return;
                    }

                    var5 = (double) this.b() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m;
                    double var7 = this.c() + this.a().s.nextInt(3) - 1;
                    double var9 = (double) this.d() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m;
                    EntityInsentient var11 = var13 instanceof EntityInsentient ? (EntityInsentient) var13 : null;
                    var13.b(var5, var7, var9, this.a().s.nextFloat() * 360.0F, 0.0F);
                    if (var11 != null) {
//                        var11.came_from_spawner = true;
                        if (MOB_SPAWNER.isAssignableFrom(this.getClass())) {
                            var11.setSpawnBlock(this.b(), this.c(), this.d());
                        }
                    }

                    if (var11 == null || var11.getCanSpawnHere(true)) {
                        this.a(var13);
                        this.a().e(2004, this.b(), this.c(), this.d(), 0);
                        if (var11 != null) {
                            var11.q();
                        }

                        var12 = true;
                    }

                    ++var2;
                }
            }
        }

    }

    @Marker
    public abstract boolean f();

    @Marker
    public abstract String e();

    @Marker
    public abstract void b(NBTTagCompound par1NBTTagCompound);

    @Marker
    public abstract Entity a(Entity par1Entity);

    @Marker
    private void j() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.delay = this.minSpawnDelay;
        } else {
            int var10003 = this.maxSpawnDelay - this.minSpawnDelay;
            this.delay = this.minSpawnDelay + this.a().s.nextInt(var10003);
        }

        if (this.e != null && this.e.size() > 0) {
//            this.a((TileEntityMobSpawnerData)WeightedRandom.a(this.a().s, this.e));
        }

        this.func_98267_a(EnumEntityState.mob_spawn);
    }

    @Marker
    public abstract void func_98267_a(EnumEntityState var1);

    @Marker
    public abstract int b();

    @Marker
    public abstract int c();

    @Marker
    public abstract int d();

    @Marker
    public abstract World a();

    public static class MobSpawnerGetter implements Utils.DangerConsumer<Class<?>> {
        @Override
        public Class<?> dangerGet() throws Throwable {
            return Class.forName("net.minecraft.MobSpawner");
        }

    }
}
