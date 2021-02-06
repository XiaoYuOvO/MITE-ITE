package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;
import static net.xiaoyu233.mitemod.miteite.util.Config.ConfigEntry.FIRST_DAY_LONGER_DAY_TIME;

@Transform(WorldServer.class)
public class WorldServerTrans extends World{
    @Link
    private MinecraftServer a;
    @Link
    private EntityTracker J;
    @Link
    private PlayerChunkMap K;
    @Link
    private Set L;
    @Link
    private TreeSet M;
    @Link
    public ChunkProviderServer b;
    @Link
    public boolean c;
    @Link
    private PortalTravelAgent P;
    @Link
    private SpawnerCreature Q ;
    @Link
    public WorldMap world_map;
    @Link
    public int no_hostile_mob_spawning_counter;
    private boolean pushTimeNextTick;

    @Marker
    public WorldServerTrans(IDataManager par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, MethodProfiler par5Profiler, IConsoleLogManager par6ILogAgent, long world_creation_time, long total_world_time) {
        super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
    }


    @Marker
    public boolean v() {
        return false;
    }

    @Override
    @Marker
    public Entity a(int var1) {
        return null;
    }

    @Marker
    public BiomeBase a(int par1, int par2) {
        return null;
    }

    @Marker
    protected IChunkProvider j() {
        return null;
    }

    @Marker
    public IChunkProvider L() {
        return null;
    }

    public Class getSuitableCreature(EnumCreatureType creature_type, int x, int y, int z) {
        boolean check_depth = this.isOverworld();
        boolean is_blood_moon_up = this.isBloodMoon(true);
        boolean is_freezing_biome = this.a(x, z).isFreezing();
        boolean is_desert_biome = this.a(x, z).isDesertBiome();
        boolean can_spawn_ghouls_on_surface = is_blood_moon_up;
        boolean can_spawn_wights_on_surface = is_blood_moon_up && is_freezing_biome;
        boolean can_spawn_shadows_on_surface = is_blood_moon_up && is_desert_biome;
        boolean can_spawn_revenants_on_surface = is_blood_moon_up;
        boolean can_spawn_bone_lords_on_surface = is_blood_moon_up;
        boolean can_spawn_giant_on_surface = is_blood_moon_up;
        boolean can_spawn_ghast_on_surface = is_blood_moon_up;
        boolean can_spawn_ancient_bone_lord_on_surface = is_blood_moon_up;

        for(int attempt = 0; attempt < 16; ++attempt) {
            List possible_creatures = this.L().a(creature_type, x, y, z);
            if (possible_creatures == null || possible_creatures.isEmpty()) {
                return null;
            }

            BiomeMeta entry = (BiomeMeta) WeightedRandom.a(this.s, possible_creatures);
            Class entity_class = entry.b;
            if (entity_class == EntityCreeper.class) {
                if (!this.hasSkylight() || this.v() || this.s.nextInt(4) == 0 || !this.isOutdoors(x, y, z)) {
                    if (this.s.nextInt(40) >= y && this.s.nextFloat() < 0.5F) {
                        return EntityInfernalCreeper.class;
                    }

                    return entity_class;
                }
            } else if (entity_class == EntitySlime.class) {
                if (!this.blockTypeIsAbove(Block.y, x, y, z)) {
                    return entity_class;
                }
            } else if (entity_class == EntityGhoul.class) {
                if (!check_depth || y <= 56 || can_spawn_ghouls_on_surface) {
                    return entity_class;
                }
            } else if (entity_class == EntityJelly.class) {
                if (this.blockTypeIsAbove(Block.y, x, y, z)) {
                    return entity_class;
                }
            } else if (entity_class == EntityWight.class) {
                if (!check_depth || y <= 48 || can_spawn_wights_on_surface) {
                    return entity_class;
                }
            }else if (entity_class == EntityGiantZombie.class){
                if (can_spawn_giant_on_surface){
                    return entity_class;
                }
            }else if (entity_class == EntityVampireBat.class) {
                if (!check_depth || y <= 48 || is_blood_moon_up) {
                    return entity_class;
                }
            } else if (entity_class == EntityRevenant.class) {
                if (!check_depth || y <= 44 || can_spawn_revenants_on_surface) {
                    return entity_class;
                }
            } else if (entity_class == EntityZombieLord.class){
                return entity_class;
            }else if (entity_class == EntityInvisibleStalker.class) {
                if (!check_depth || y <= 40) {
                    return entity_class;
                }
            } else if (entity_class == EntityEarthElemental.class) {
                if (!check_depth || y <= 40) {
                    return entity_class;
                }
            } else if (entity_class == EntityBlob.class) {
                if ((!check_depth || y <= 40) && this.blockTypeIsAbove(Block.y, x, y, z)) {
                    return entity_class;
                }
            } else if (entity_class == EntityOoze.class) {
                if ((!check_depth || y <= 32) && this.getBlock(x, y - 1, z) == Block.y && this.blockTypeIsAbove(Block.y, x, y, z)) {
                    return entity_class;
                }
            } else if (entity_class == EntityNightwing.class) {
                if (!check_depth || y <= 32 || is_blood_moon_up) {
                    return entity_class;
                }
            } else if (entity_class == EntityBoneLord.class) {
                if (!check_depth || y <= 32 || can_spawn_bone_lords_on_surface) {
                    return entity_class;
                }
            } else if (entity_class == EntityPudding.class) {
                if ((!check_depth || y <= 24) && this.getBlock(x, y - 1, z) == Block.y && this.blockTypeIsAbove(Block.y, x, y, z)) {
                    return entity_class;
                }
            } else if (entity_class != EntityDemonSpider.class && entity_class != EntityPhaseSpider.class) {
                if (entity_class == EntityHellhound.class) {
                    if (!check_depth || y <= 32) {
                        return entity_class;
                    }
                } else if (entity_class == EntityShadow.class) {
                    if (!check_depth || y <= 32 || can_spawn_shadows_on_surface) {
                        return entity_class;
                    }
                } else if (entity_class == EntitySpider.class) {
                    if (!this.hasSkylight() || this.s.nextInt(4) != 0 || !this.isOutdoors(x, y, z)) {
                        return entity_class;
                    }
                } else if (entity_class == EntityWoodSpider.class) {
                    if ((this.l(x, y, z) || this.blockTypeIsAbove(Block.P, x, y, z) || this.blockTypeIsAbove(Block.O, x, y, z)) && this.blockTypeIsNearTo(Block.O.cF, x, y, z, 5, 2) && this.blockTypeIsNearTo(Block.P.cF, x, y + 5, z, 5, 5)) {
                        return entity_class;
                    }
                } else if (entity_class == EntityAncientBoneLord.class){
                    if (can_spawn_ancient_bone_lord_on_surface && this.getDayOfWorld() >= MITEITEMod.CONFIG.get(Config.ConfigEntry.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY)){
                        return entity_class;
                    }
                }else if (entity_class == EntityBlackWidowSpider.class) {
                    if (this.s.nextFloat() >= 0.5F) {
                        return entity_class;
                    }
                } else {
                    if (entity_class == EntityGhast.class) {
                        for (Object value : this.e) {
                            Entity entity = (Entity) value;
                            if (entity instanceof EntityGhast)
                                if (this.isTheNether() && entity.getDistanceSqToBlock(x, y, z) < 2304.0D && this.s.nextFloat() < 0.8F) {
                                    entity_class = null;
                                }else if (this.isOverworld() && entity.getDistanceSqToBlock(x, y, z) < 2304.0D && can_spawn_ghast_on_surface){
                                    if (this.getDayOfWorld() >= MITEITEMod.CONFIG.get(Config.ConfigEntry.GHAST_SPAWN_LIMIT_DAY)){
                                        return entity_class;
                                    }
                                }
                        }
                    }

                    return entity_class;
                }
            } else if (!check_depth || y <= 32) {
                return entity_class;
            }
        }

        return null;
    }
    public void b() {
        if (this.no_hostile_mob_spawning_counter > 0 && Minecraft.inDevMode() && this.no_hostile_mob_spawning_counter % 200 == 0) {
            System.out.println("no_hostile_mob_spawning_counter=" + this.no_hostile_mob_spawning_counter);
        }

        this.x.setEarliestAllowableMITERelease(149);
        super.b();
        if (!this.x.isValidMITEWorld()) {
            MinecraftServer.setTreacheryDetected();
        }

        if (this.t.i == 0 && this.a.isServerSideMappingEnabled() && this.world_map == null) {
            this.world_map = new WorldMap(dyCast(this));
        }

        if (this.world_map != null) {
            this.world_map.writeToFileProgressively(false);
        }

        this.checkCurses();
        if (this.N().t() && this.r < 3) {
            this.r = 3;
        }

        this.t.e.b();
        if (this.hasNonGhostPlayers()) {
            boolean sleeping_prevented = this.isBloodMoon(false) || DedicatedServer.isTournament();
            if (sleeping_prevented || !this.allPlayersInBedOrDead() || this.getAdjustedTimeOfDay() >= getTimeOfSunrise() - 1000 && this.getAdjustedTimeOfDay() < getTimeOfSleeping()) {
                this.wakeAllPlayersGently();
            } else if (this.allPlayersAsleepOrDead()) {
                if (this.O().b("doDaylightCycle")) {
                    this.runSleepTicks(this.getTimeTillSunrise());
                } else {
                    this.wakeAllPlayersGently();
                }
            } else {
                this.signalAllPlayersToStartFallingAsleep();
            }
        }

        this.C.a("mobSpawner");
        if (this.O().b("doMobSpawning")) {
            this.Q.performRandomLivingEntitySpawning(dyCast(this));
        }

        this.C.c("chunkSource");
        this.v.c();
        this.tickBlocksInFastForward();
        this.checkScheduledBlockChanges(false);
        int var3 = this.a(1.0F);
        if (var3 != this.j) {
            this.j = var3;
        }

        int ticks_progressed = this.shouldTimeProgress() ? 1 : 0;
        if (MITEITEMod.CONFIG.get(FIRST_DAY_LONGER_DAY_TIME) && this.getDayOfWorld() == 1 && this.I() < 12000){
            if (pushTimeNextTick){
                if (ticks_progressed > 0) {
                    this.advanceTotalWorldTime(ticks_progressed);
                }
                pushTimeNextTick = false;
            }else {
                pushTimeNextTick = true;
            }
        }else if (ticks_progressed > 0) {
            this.advanceTotalWorldTime(ticks_progressed);
        }
        this.C.c("tickPending");
        this.a(false);
        this.C.c("tickTiles");
        this.performQueuedBlockOperations();
        if (ticks_progressed <= 0) {
            this.setActivePlayerChunks();
        } else {
            this.g();
        }

        this.C.c("chunkMap");
        this.K.b();
        this.C.c("village");
        this.A.a();
        this.B.a();
        this.C.c("portalForcer");
        this.P.a(this.I());
        this.C.b();
        this.aa();
    }
    @Marker
    protected void wakeAllPlayersGently() {}

    @Marker
    private void performQueuedBlockOperations() {}

    @Marker
    public boolean shouldTimeProgress() { return false; }

    @Marker
    public void checkScheduledBlockChanges(boolean flush) {}

    @Marker
    protected void tickBlocksInFastForward() {}

    @Marker
    private void signalAllPlayersToStartFallingAsleep() {}

    @Marker
    public boolean runSleepTicks(int ticks) {return false;}

    @Marker
    public boolean allPlayersAsleepOrDead() {return false;}

    @Marker
    private boolean allPlayersInBedOrDead() {return false;}

    @Marker
    public void checkCurses() {}

    @Marker
    private void aa() {}
}
