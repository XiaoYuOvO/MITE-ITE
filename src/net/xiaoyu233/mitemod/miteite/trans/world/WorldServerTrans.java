package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(WorldServer.class)
public abstract class WorldServerTrans extends World {
   @Shadow protected abstract EntityInsentient tryCreateNewLivingEntityCloseTo(int x, int y, int z, int min_distance, int max_distance, Class entity_living_class, EnumCreatureType enum_creature_type);

   @Shadow
   public boolean levelSaving;
   @Shadow
   public int no_hostile_mob_spawning_counter;
   @Shadow
   public ChunkProviderServer theChunkProviderServer;
   @Shadow
   public WorldMap world_map;
   @Final
   @Shadow
   private SpawnerCreature animalSpawner;
   @Shadow
   @Final
   private MinecraftServer mcServer;
   private boolean pushTimeNextTick;
   @Shadow
   @Final
   private PlayerChunkMap thePlayerManager;
   @Shadow
   @Final
   private PortalTravelAgent worldTeleporter;

   public WorldServerTrans(IDataManager par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, MethodProfiler par5Profiler, IConsoleLogManager par6ILogAgent, long world_creation_time, long total_world_time) {
      super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
   }

   @Shadow
   public boolean allPlayersAsleepOrDead() {
      return false;
   }

   @Shadow
   private boolean allPlayersInBedOrDead() {
      return false;
   }

   @Shadow
   public void checkCurses() {
   }

   @Shadow
   public void checkScheduledBlockChanges(boolean flush) {
   }

   @Shadow
   protected IChunkProvider createChunkProvider() {
      return null;
   }

   @Shadow
   public Entity getEntityByID(int var1) {
      return null;
   }


   public EntityInsentient tryCreateNewLivingEntityCloseToP(int x, int y, int z, int min_distance, int max_distance, Class entity_living_class, EnumCreatureType enum_creature_type) {
      return this.tryCreateNewLivingEntityCloseTo(x, y, z, min_distance, max_distance, entity_living_class, enum_creature_type);
   }
   @Overwrite
   public Class getSuitableCreature(EnumCreatureType creature_type, int x, int y, int z) {
      boolean check_depth = this.isOverworld();
      boolean is_blood_moon_up = this.isBloodMoon(true);
      boolean is_freezing_biome = this.getBiomeGenForCoords(x, z).isFreezing();
      boolean is_desert_biome = this.getBiomeGenForCoords(x, z).isDesertBiome();
      boolean can_spawn_ghouls_on_surface = is_blood_moon_up;
      boolean can_spawn_wights_on_surface = is_blood_moon_up && is_freezing_biome;
      boolean can_spawn_shadows_on_surface = is_blood_moon_up && is_desert_biome;
      boolean can_spawn_revenants_on_surface = is_blood_moon_up;
      boolean can_spawn_bone_lords_on_surface = is_blood_moon_up;
      boolean can_spawn_giant_on_surface = is_blood_moon_up;
      boolean can_spawn_ghast_on_surface = is_blood_moon_up;
      boolean can_spawn_ancient_bone_lord_on_surface = is_blood_moon_up;

      for(int attempt = 0; attempt < 16; ++attempt) {
         List possible_creatures = this.getChunkProvider().getPossibleCreatures(creature_type, x, y, z);
         if (possible_creatures == null || possible_creatures.isEmpty()) {
            return null;
         }

         BiomeMeta entry = (BiomeMeta)WeightedRandom.getRandomItem(this.rand, possible_creatures);
         Class entity_class = entry.entityClass;
         if (entity_class == EntityCreeper.class) {
            if (!this.hasSkylight() || this.isDaytime() || this.rand.nextInt(4) == 0 || !this.isOutdoors(x, y, z)) {
               if (this.rand.nextInt(40) >= y && this.rand.nextFloat() < 0.5F) {
                  return EntityInfernalCreeper.class;
               }

               return entity_class;
            }
         } else if (entity_class == EntitySlime.class) {
            if (!this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
         } else if (entity_class == EntityGhoul.class) {
            if (!check_depth || y <= 56 || can_spawn_ghouls_on_surface) {
               return entity_class;
            }
         } else if (entity_class == EntityJelly.class) {
            if (this.blockTypeIsAbove(Block.stone, x, y, z)) {
               return entity_class;
            }
         } else if (entity_class == EntityWight.class) {
            if (!check_depth || y <= 48 || can_spawn_wights_on_surface) {
               return entity_class;
            }
         } else if (entity_class == EntityGiantZombie.class) {
            if (can_spawn_giant_on_surface) {
               return entity_class;
            }
         } else if (entity_class == EntityVampireBat.class) {
            if (!check_depth || y <= 48 || is_blood_moon_up) {
               return entity_class;
            }
         } else if (entity_class == EntityRevenant.class) {
            if (!check_depth || y <= 44 || can_spawn_revenants_on_surface) {
               return entity_class;
            }
         } else {
            if (entity_class == EntityZombieLord.class) {
               return entity_class;
            }

            if (entity_class == EntityInvisibleStalker.class) {
               if (!check_depth || y <= 40) {
                  return entity_class;
               }
            } else if (entity_class == EntityEarthElemental.class) {
               if (!check_depth || y <= 40) {
                  return entity_class;
               }
            } else if (entity_class == EntityBlob.class) {
               if ((!check_depth || y <= 40) && this.blockTypeIsAbove(Block.stone, x, y, z)) {
                  return entity_class;
               }
            } else if (entity_class == EntityOoze.class) {
               if ((!check_depth || y <= 32) && this.getBlock(x, y - 1, z) == Block.stone && this.blockTypeIsAbove(Block.stone, x, y, z)) {
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
               if ((!check_depth || y <= 24) && this.getBlock(x, y - 1, z) == Block.stone && this.blockTypeIsAbove(Block.stone, x, y, z)) {
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
                  if (!this.hasSkylight() || this.rand.nextInt(4) != 0 || !this.isOutdoors(x, y, z)) {
                     return entity_class;
                  }
               } else if (entity_class == EntityWoodSpider.class) {
                  if ((this.canBlockSeeTheSky(x, y, z) || this.blockTypeIsAbove(Block.leaves, x, y, z) || this.blockTypeIsAbove(Block.wood, x, y, z)) && this.blockTypeIsNearTo(Block.wood.blockID, x, y, z, 5, 2) && this.blockTypeIsNearTo(Block.leaves.blockID, x, y + 5, z, 5, 5)) {
                     return entity_class;
                  }
               }else if (entity_class == EntityBlackWidowSpider.class) {
                    if (this.rand.nextFloat() >= 0.5F) {
                        return entity_class;
                    }
                } else {
                    if (entity_class == EntityGhast.class) {
                        for (Object value : this.loadedEntityList) {
                            Entity entity = (Entity) value;
                            if (entity instanceof EntityGhast)
                                if (this.isTheNether() && entity.getDistanceSqToBlock(x, y, z) < 2304.0D && this.rand.nextFloat() < 0.8F) {
                                    entity_class = null;
                                }else if (this.isOverworld() && entity.getDistanceSqToBlock(x, y, z) < 2304.0D && can_spawn_ghast_on_surface){
                                    if (this.getDayOfOverworld() >= (Configs.GameMechanics.MobSpawning.GHAST_SPAWN_LIMIT_DAY.get())){
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
      }

      return null;
   }

   @Shadow
   private void performQueuedBlockOperations() {
   }

   @Shadow
   public boolean runSleepTicks(int ticks) {
      return false;
   }

   @Shadow
   private void sendAndApplyBlockEvents() {
   }

   @Shadow
   public boolean shouldTimeProgress() {
      return false;
   }

   @Shadow
   private void signalAllPlayersToStartFallingAsleep() {
   }

   @Overwrite
   public void tick() {
      if (this.no_hostile_mob_spawning_counter > 0 && Minecraft.inDevMode() && this.no_hostile_mob_spawning_counter % 200 == 0) {
         System.out.println("no_hostile_mob_spawning_counter=" + this.no_hostile_mob_spawning_counter);
      }

      this.worldInfo.setEarliestAllowableMITERelease(149);
      super.tick();
      if (!this.worldInfo.isValidMITEWorld()) {
         MinecraftServer.setTreacheryDetected();
      }

      if (this.provider.dimensionId == 0 && this.mcServer.isServerSideMappingEnabled() && this.world_map == null) {
         this.world_map = new WorldMap(ReflectHelper.dyCast(this));
      }

      if (this.world_map != null) {
         this.world_map.writeToFileProgressively(false);
      }

      this.checkCurses();
      if (this.getWorldInfo().isHardcoreModeEnabled() && this.difficultySetting < 3) {
         this.difficultySetting = 3;
      }

      this.provider.worldChunkMgr.cleanupCache();
      if (this.hasNonGhostPlayers()) {
         boolean sleeping_prevented = this.isBloodMoon(false) || DedicatedServer.isTournament();
         if (sleeping_prevented || !this.allPlayersInBedOrDead() || this.getAdjustedTimeOfDay() >= getTimeOfSunrise() - 1000 && this.getAdjustedTimeOfDay() < getTimeOfSleeping()) {
            this.wakeAllPlayersGently();
         } else if (this.allPlayersAsleepOrDead()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
               this.runSleepTicks(this.getTimeTillSunrise());
            } else {
               this.wakeAllPlayersGently();
            }
         } else {
            this.signalAllPlayersToStartFallingAsleep();
         }
      }

      this.theProfiler.startSection("mobSpawner");
      if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
         this.animalSpawner.performRandomLivingEntitySpawning(ReflectHelper.dyCast(this));
      }

      this.theProfiler.endStartSection("chunkSource");
      this.chunkProvider.unloadQueuedChunks();
      this.tickBlocksInFastForward();
      this.checkScheduledBlockChanges(false);
      int var3 = this.calculateSkylightSubtracted(1.0F);
      if (var3 != this.skylightSubtracted) {
         this.skylightSubtracted = var3;
      }

      int ticks_progressed = this.shouldTimeProgress() ? 1 : 0;
      if (Configs.GameMechanics.FIRST_DAY_LONGER_DAY_TIME.get() && this.getDayOfOverworld() == 1 && this.getTotalWorldTime() < 12000L) {
         if (this.pushTimeNextTick) {
            if (ticks_progressed > 0) {
               this.advanceTotalWorldTime(ticks_progressed);
            }

            this.pushTimeNextTick = false;
         } else {
            this.pushTimeNextTick = true;
         }
      } else if (ticks_progressed > 0) {
         this.advanceTotalWorldTime(ticks_progressed);
      }

      this.theProfiler.endStartSection("tickPending");
      this.tickUpdates(false);
      this.theProfiler.endStartSection("tickTiles");
      this.performQueuedBlockOperations();
      if (ticks_progressed <= 0) {
         this.setActivePlayerChunks();
      } else {
         this.tickBlocksAndAmbiance();
      }

      this.theProfiler.endStartSection("chunkMap");
      this.thePlayerManager.updatePlayerInstances();
      this.theProfiler.endStartSection("village");
      this.villageCollectionObj.tick();
      this.villageSiegeObj.tick();
      this.theProfiler.endStartSection("portalForcer");
      this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
      this.theProfiler.endSection();
      this.sendAndApplyBlockEvents();
   }

   @Shadow
   protected void tickBlocksInFastForward() {
   }

   @Shadow
   protected void wakeAllPlayersGently() {
   }
}
