package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings({"ConstantConditions", "unused"})
@Transform(SpawnerCreature.class)
public class SpawnerCreatureTrans {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Link
    private final HashMap<ChunkCoordIntPair,Boolean> a = new HashMap<>();
    @Link
    private final Random random = new Random();
    public int trySpawningHostileMobs(WorldServer world, boolean deep_only) {
        EnumCreatureType creature_type = EnumCreatureType.a;
        boolean is_overworld = world.isOverworld();
        boolean is_new_moon = world.isNewMoon();
        boolean is_full_moon = world.isFullMoon();
        boolean is_blood_moon = world.isBloodMoon(false);
        boolean is_blue_moon = world.isBlueMoon(false);
        boolean is_daytime = world.v();
        int creature_limit = (int) ((int) (creature_type.b() * this.a.size() / (is_blood_moon ? MITEITEMod.CONFIG.get(Config.ConfigEntry.BLOOD_MOON_MAX_HOSTILE_FRACTION) : 256)) + MITEITEMod.CONFIG.get(Config.ConfigEntry.MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY) * world.getDayOfWorld());
        if (deep_only) {
            creature_limit *= 2;
        }

        if (deep_only) {
            world.last_mob_spawn_limit_under_60 = creature_limit;
        } else {
            world.last_mob_spawn_limit_at_60_or_higher = creature_limit;
        }

        if (world.countMobs(deep_only, !deep_only) >= creature_limit) {
            return 0;
        } else {
            boolean try_to_hang_bats_from_ceiling = world.s.nextBoolean();
            int total_spawned = 0;
            ChunkCoordinates spawn_point = world.K();
            Iterator<?> eligible_chunk_iterator = this.a.keySet().iterator();

            label251:
            while(true) {
                int x;
                int y;
                int z;
                do {
                    while(true) {
                        do {
                            do {
                                ChunkCoordIntPair chunk_coord;
                                do {
                                    if (!eligible_chunk_iterator.hasNext()) {
                                        return total_spawned;
                                    }

                                    chunk_coord = (ChunkCoordIntPair)eligible_chunk_iterator.next();
                                } while(this.a.get(chunk_coord));

                                ChunkPosition chunk_pos = a(world, chunk_coord.a, chunk_coord.b);
                                if (deep_only && chunk_pos.b >= 60) {
                                    chunk_pos = a(world, chunk_coord.a, chunk_coord.b);
                                }

                                x = chunk_pos.a;
                                y = chunk_pos.b;
                                z = chunk_pos.c;
                            } while(world.isOverworld() && y == 63 && world.s.nextInt(4) > 0 && world.getBlock(x, y - 1, z) == Block.aY);
                        } while(world.getClosestPlayer(x, y, z, 48.0D, true) == null && world.s.nextInt(2) == 0);

                        if (deep_only) {
                            if (y < 60 && world.countMobs(true, false) < creature_limit) {
                                break;
                            }
                        } else if (y >= 60 && world.countMobs(false, true) < creature_limit) {
                            break;
                        }
                    }
                } while(!canCreatureTypeSpawnAtLocation(creature_type, world, x, y, z, false, null));

                int num_spawned_below_60 = 0;
                int num_spawned_at_60_or_higher = 0;

                label249:
                for(int var18 = 0; var18 < 3; ++var18) {
                    int x_with_random_offset = x;
                    int z_with_random_offset = z;
                    byte random_offset_range = 6;
                    Class<?> suitable_creature_class = null;
                    GroupDataEntity entity_living_data = null;
                    int var25 = 0;
                    byte max_spawn_attempts = 4;

                    while(true) {
                        while(true) {
                            if (var25 >= max_spawn_attempts) {
                                continue label249;
                            }

                            x_with_random_offset += world.s.nextInt(random_offset_range) - world.s.nextInt(random_offset_range);
                            z_with_random_offset += world.s.nextInt(random_offset_range) - world.s.nextInt(random_offset_range);
                            double[] resulting_y_pos = new double[1];
                            if (canCreatureTypeSpawnAtLocation(creature_type, world, x_with_random_offset, y, z_with_random_offset, false, resulting_y_pos)) {
                                float pos_x = (float) x_with_random_offset + 0.5F;
                                float pos_y;
                                float pos_z = (float) z_with_random_offset + 0.5F;
                                pos_y = (float) resulting_y_pos[0];
                                boolean can_spawn_close_to_player = (world.isOverworld() || world.isUnderworld()) && world.getClosestPlayer(pos_x, pos_y, pos_z, 24.0D, true) != null && world.n(x_with_random_offset, MathHelper.d(pos_y), z_with_random_offset) == 0 && world.n(x_with_random_offset, MathHelper.d(pos_y) + 1, z_with_random_offset) == 0;
                                if (can_spawn_close_to_player) {
                                    if (world.getClosestPlayer(pos_x, pos_y, pos_z, 8.0D, false) != null) {
                                        ++var25;
                                        continue;
                                    }
                                } else if (world.getClosestPlayer(pos_x, pos_y, pos_z, 24.0D, false) != null) {
                                    ++var25;
                                    continue;
                                }

                                float delta_x = pos_x - (float) spawn_point.a;
                                float delta_y = pos_y - (float) spawn_point.b;
                                float delta_z = pos_z - (float) spawn_point.c;
                                float distance_from_spawn_point_sq = delta_x * delta_x + delta_y * delta_y + delta_z * delta_z;
                                if (!(distance_from_spawn_point_sq < 576.0F)) {
                                    if (suitable_creature_class == null) {
                                        suitable_creature_class = world.getSuitableCreature(creature_type, x_with_random_offset, y, z_with_random_offset);
                                        if (suitable_creature_class == null) {
                                            continue label249;
                                        }
                                    }

                                    if (suitable_creature_class != EntityGhast.class || world.getClosestPlayer(pos_x, pos_y, pos_z, 48.0D, false) == null) {
                                        EntityInsentient entity_living;
                                        try {
                                            entity_living = this.getSubstituteClassToSpawn(world, y, suitable_creature_class).getConstructor(World.class).newInstance(world);
                                        } catch (Exception var43) {
                                            var43.printStackTrace();
                                            return total_spawned;
                                        }

                                        if (entity_living instanceof EntityBat && try_to_hang_bats_from_ceiling) {
                                            pos_y = this.tryHangBatFromCeiling(world, (EntityBat) entity_living, x_with_random_offset, y, z_with_random_offset, pos_x, pos_y, pos_z);
                                        }

                                        if (is_overworld && Entity.isClass(entity_living, EntityPhaseSpider.class)) {
                                            max_spawn_attempts = 64;
                                        } else {
                                            max_spawn_attempts = 4;
                                        }

                                        entity_living.b(pos_x, pos_y, pos_z, world.s.nextFloat() * 360.0F, 0.0F);
                                        if (!world.isOverworld() || !world.isBlueMoonNight() || world.a(EnumSkyBlock.a, entity_living.getBlockPosX(), entity_living.getEyeBlockPosY(), entity_living.getBlockPosZ()) <= 0) {
                                            if (entity_living.O < 1.0F) {
                                                if (!entity_living.getCanSpawnHere(true)) {
                                                    ++var25;
                                                    continue;
                                                }
                                            } else {
                                                boolean can_spawn_here = entity_living.getCanSpawnHere(true);
                                                if (!can_spawn_here) {
                                                    entity_living.b(pos_x - 0.5F, pos_y, pos_z, world.s.nextFloat() * 360.0F, 0.0F);
                                                    can_spawn_here = entity_living.getCanSpawnHere(true);
                                                }

                                                if (!can_spawn_here) {
                                                    entity_living.b(pos_x + 0.5F, pos_y, pos_z, world.s.nextFloat() * 360.0F, 0.0F);
                                                    can_spawn_here = entity_living.getCanSpawnHere(true);
                                                }

                                                if (!can_spawn_here) {
                                                    entity_living.b(pos_x, pos_y, pos_z - 0.5F, world.s.nextFloat() * 360.0F, 0.0F);
                                                    can_spawn_here = entity_living.getCanSpawnHere(true);
                                                }

                                                if (!can_spawn_here) {
                                                    entity_living.b(pos_x, pos_y, pos_z + 0.5F, world.s.nextFloat() * 360.0F, 0.0F);
                                                    can_spawn_here = entity_living.getCanSpawnHere(true);
                                                }

                                                if (!can_spawn_here) {
                                                    ++var25;
                                                    continue;
                                                }
                                            }

                                            if (is_daytime) {
                                                if (entity_living.aM() && world.isOutdoors(MathHelper.c((double) pos_x), MathHelper.c((double) pos_y), MathHelper.c((double) pos_z))) {
                                                    continue label251;
                                                }
                                            } else {
                                                int chance_of_skipping = is_blue_moon ? 54 : (is_blood_moon ? 2 : (is_full_moon ? 3 : (is_new_moon ? 6 : 4)));
                                                if (world.s.nextInt(chance_of_skipping) != 0 && world.isOutdoors(MathHelper.c((double) pos_x), MathHelper.c((double) pos_y), MathHelper.c((double) pos_z))) {
                                                    continue label251;
                                                }
                                            }

                                            if (y < 60) {
                                                ++num_spawned_below_60;
                                            } else {
                                                ++num_spawned_at_60_or_higher;
                                            }

                                            entity_living_data = entity_living.a(entity_living_data);
                                            world.d(entity_living);
                                            ++total_spawned;
                                            if (y < 60) {
                                                if (num_spawned_below_60 >= entity_living.bv()) {
                                                    continue label251;
                                                }
                                            } else if (num_spawned_at_60_or_higher >= entity_living.bv()) {
                                                continue label251;
                                            }

                                        }
                                    }
                                }
                            }
                            ++var25;
                        }
                    }
                }
            }
        }
    }
    @Marker
    protected static ChunkPosition a(World par0World, int par1, int par2){
        return null;
    }

    @Marker
    private Class<EntityInsentient> getSubstituteClassToSpawn(World world, int y, Class<?> suitable_creature_class){
        return null;
    }

    @Marker
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType creature_type, World world, int x, int y, int z, boolean initial_spawn, double[] resulting_y_pos){
        return false;
    }

    @Marker
    private float tryHangBatFromCeiling(World world, EntityBat bat, int x, int y, int z, float pos_x, float pos_y, float pos_z){
        return 0.0f;
    }
}
