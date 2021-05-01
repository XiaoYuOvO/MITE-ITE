package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Mixin(ChunkRegionLoader.class)
public class ChunkRegionLoaderTrans {
   @Shadow
   private final int calcEntityChecksum(Entity entity) {
      return 0;
   }

   @Shadow
   private final int calcSectionChecksum(int xz_and_seed_checksum_component, int section_y, byte[] bytes) {
      return 0;
   }

   @Shadow
   private final int calcTileEntityChecksum(TileEntity tile_entity) {
      return 0;
   }

   @Shadow
   private final int calcXZAndSeedChecksumComponent(Chunk chunk) {
      return 0;
   }

   @Overwrite
   private void writeChunkToNBT(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound) {
      if (Minecraft.inDevMode() && (par1Chunk instanceof EmptyChunk || par1Chunk.isEmpty())) {
         Minecraft.setErrorMessage("writeChunkToNBT: trying to write empty chunk to disk");
      }

      par3NBTTagCompound.setInteger("xPos", par1Chunk.xPosition);
      par3NBTTagCompound.setInteger("zPos", par1Chunk.zPosition);
      int xz_and_seed_checksum_component = this.calcXZAndSeedChecksumComponent(par1Chunk);
      if (par1Chunk.invalidate_checksum) {
         ++xz_and_seed_checksum_component;
      }

      par3NBTTagCompound.setLong("last_total_world_time", par1Chunk.last_total_world_time);
      par3NBTTagCompound.setInteger("animals_spawned", par1Chunk.animals_spawned);
      par3NBTTagCompound.setLong("LastUpdate", par2World.getTotalWorldTime());
      par3NBTTagCompound.setIntArray("HeightMap", par1Chunk.heightMap);
      par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.isTerrainPopulated);
      par3NBTTagCompound.setLong("InhabitedTime", par1Chunk.inhabitedTime);
      ChunkSection[] var4 = par1Chunk.getBlockStorageArray();
      NBTTagList var5 = new NBTTagList("Sections");
      boolean var6 = !par2World.provider.hasNoSky;
      ChunkSection[] var7 = var4;
      int var8 = var4.length;

      NBTTagCompound var11;
      for(int var9 = 0; var9 < var8; ++var9) {
         ChunkSection var10 = var7[var9];
         if (var10 != null) {
            var11 = new NBTTagCompound();
            var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 255));
            var11.setInteger("Blocks", this.calcSectionChecksum(xz_and_seed_checksum_component, var10.getYLocation() >> 4 & 255, var10.getBlockLSBArray()));
            var11.setByteArray("BlockData", var10.getBlockLSBArray());
            if (var10.getBlockMSBArray() != null) {
               var11.setByteArray("Add", var10.getBlockMSBArray().data);
            }

            var11.setByteArray("Data", var10.getMetadataArray().data);
            var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
            if (var6) {
               var11.setByteArray("SkyLight", var10.getSkylightArray().data);
            } else {
               var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
            }

            var5.appendTag(var11);
         }
      }

      par3NBTTagCompound.setTag("Sections", var5);
      par3NBTTagCompound.setByteArray("Biomes", par1Chunk.getBiomeArray());
      NBTTagList var16 = new NBTTagList();
      int entities_checksum = xz_and_seed_checksum_component;
      List[] entity_lists = par1Chunk.getEntityListsForReadingOnly();

      Iterator var18;
      label220:
      for(var8 = 0; var8 < entity_lists.length; ++var8) {
         var18 = entity_lists[var8].iterator();

         while(true) {
            while(true) {
               if (!var18.hasNext()) {
                  continue label220;
               }

               Entity var21 = (Entity)var18.next();
               if (Minecraft.inDevMode() && par1Chunk.checkForEntityDuplicates(var21)) {
                  Minecraft.setErrorMessage("writeChunkToNBT: " + var21.getEntityName() + " was found in the chunk's entityLists more than once");
               }

               if (!var21.isWrittenToChunkNBT()) {
                  if (Minecraft.inDevMode()) {
                     System.out.println("writeChunkToNBT: skipping " + (var21.isDead ? "dead " : (var21.riddenByEntity != null ? "mounted " : "")) + var21.getEntityName() + " (UUID=" + var21.getUniqueID() + ") in " + par1Chunk.worldObj.provider.getDimensionName() + " because it is not supposed to be written to chunk NBT {clName: " + var21.getClass().getName() + "}");
                  }
               } else if (var21.isDead) {
                  if (!var21.is_unwanted_duplicate) {
                     Minecraft.setErrorMessage("Why is a dead " + var21.getEntityName() + " being written to the chunk? Skipping.");
                     if (!par1Chunk.worldObj.isEntityObjectInLoadedEntityList(var21)) {
                        System.out.println("Furthermore, the entity isn't in the world's loaded entity list");
                     }
                  }
               } else {
                  Chunk chunk;
                  String msg;
                  if (var21.last_chunk_saved_to != null && (var21.last_chunk_saved_to.xPosition != par1Chunk.xPosition || var21.last_chunk_saved_to.zPosition != par1Chunk.zPosition)) {
                     chunk = par2World.getChunkFromChunkCoords(var21.last_chunk_saved_to.xPosition, var21.last_chunk_saved_to.zPosition);
                     if (chunk != null) {
                        chunk.setChunkModified();
                        if (chunk.doesEntityWithMatchingClassAndUUIDExistInEntityLists(var21)) {
                           msg = "writeChunkToNBT: " + var21.getEntityName() + " was found in another chunk at the time of saving";
                           if (Minecraft.inDevMode()) {
                              Minecraft.setErrorMessage(msg);
                           } else {
                              System.out.println(msg);
                           }
                        }
                     }
                  }

                  if (var21.last_chunk_loaded_from != null && (var21.last_chunk_loaded_from.xPosition != par1Chunk.xPosition || var21.last_chunk_loaded_from.zPosition != par1Chunk.zPosition)) {
                     chunk = par2World.getChunkFromChunkCoords(var21.last_chunk_loaded_from.xPosition, var21.last_chunk_loaded_from.zPosition);
                     if (chunk != null) {
                        chunk.setChunkModified();
                        if (chunk.doesEntityWithMatchingClassAndUUIDExistInEntityLists(var21)) {
                           msg = "writeChunkToNBT: " + var21.getEntityName() + " was found in another chunk at the time of saving (last_chunk_loaded_from)";
                           if (Minecraft.inDevMode()) {
                              Minecraft.setErrorMessage(msg);
                           } else {
                              System.out.println(msg);
                           }
                        }
                     }
                  }

                  entities_checksum += this.calcEntityChecksum(var21);
                  var11 = new NBTTagCompound();
                  if (var21.writeToNBTOptional(var11)) {
                     var16.appendTag(var11);
                     var21.last_chunk_saved_to = par1Chunk;
                     var21.last_chunk_saved_to_entity_list_index = var8;
                  }
               }
            }
         }
      }

      par3NBTTagCompound.setInteger("Entities", entities_checksum);
      par3NBTTagCompound.setTag("EntityData", var16);
      NBTTagList var17 = new NBTTagList();
      var18 = par1Chunk.chunkTileEntityMap.values().iterator();
      int tile_entities_checksum = xz_and_seed_checksum_component;

      while(var18.hasNext()) {
         TileEntity var22 = (TileEntity)var18.next();
         tile_entities_checksum += this.calcTileEntityChecksum(var22);
         var11 = new NBTTagCompound();
         var22.writeToNBT(var11);
         var17.appendTag(var11);
      }

      par3NBTTagCompound.setInteger("TileEntities", tile_entities_checksum);
      par3NBTTagCompound.setTag("TileEntityData", var17);
      List var20 = par2World.getPendingBlockUpdates(par1Chunk, false);
      if (var20 != null) {
         long var19 = par2World.getTotalWorldTime();
         NBTTagList var12 = new NBTTagList();

         for (Object o : var20) {
            NextTickListEntry var14 = (NextTickListEntry) o;
            NBTTagCompound var15 = new NBTTagCompound();
            var15.setInteger("i", var14.blockID);
            var15.setInteger("x", var14.xCoord);
            var15.setInteger("y", var14.yCoord);
            var15.setInteger("z", var14.zCoord);
            var15.setInteger("t", (int) (var14.scheduledTime - var19));
            var15.setInteger("p", var14.priority);
            var12.appendTag(var15);
         }

         par3NBTTagCompound.setTag("TileTicks", var12);
      }

      if (par1Chunk.has_had_lighting_checked) {
         par3NBTTagCompound.setBoolean("has_had_lighting_checked", true);
      }

      if (par1Chunk.isGapLightingUpdated) {
         par3NBTTagCompound.setBoolean("isGapLightingUpdated", true);
      }

      byte[] update_skylight_columns = new byte[par1Chunk.updateSkylightColumns.length];

      for(int i = 0; i < update_skylight_columns.length; ++i) {
         update_skylight_columns[i] = (byte)(par1Chunk.updateSkylightColumns[i] ? -1 : 0);
      }

      par3NBTTagCompound.setByteArray("update_skylight_columns", update_skylight_columns);
      par3NBTTagCompound.setIntArray("skylight_bottom", par1Chunk.skylight_bottom);
      byte[] pending_blocklight_updates;
      int i;
      if (par2World.hasSkylight()) {
         if (par1Chunk.num_pending_skylight_updates > 0) {
            pending_blocklight_updates = new byte[par1Chunk.num_pending_skylight_updates * 2];
            System.arraycopy(par1Chunk.pending_skylight_update_coords, 0, pending_blocklight_updates, 0, pending_blocklight_updates.length);
            par3NBTTagCompound.setByteArray("pending_skylight_update_coords", pending_blocklight_updates);
         }

         pending_blocklight_updates = new byte[par1Chunk.pending_skylight_updates.length];

         for(i = 0; i < pending_blocklight_updates.length; ++i) {
            pending_blocklight_updates[i] = (byte)(par1Chunk.pending_skylight_updates[i] ? -1 : 0);
         }

         par3NBTTagCompound.setByteArray("pending_skylight_updates", pending_blocklight_updates);
      }

      if (par1Chunk.num_pending_blocklight_updates > 0) {
         pending_blocklight_updates = new byte[par1Chunk.num_pending_blocklight_updates * 2];
         System.arraycopy(par1Chunk.pending_blocklight_update_coords, 0, pending_blocklight_updates, 0, pending_blocklight_updates.length);
         par3NBTTagCompound.setByteArray("pending_blocklight_update_coords", pending_blocklight_updates);
      }

      pending_blocklight_updates = new byte[par1Chunk.pending_blocklight_updates.length];

      for(i = 0; i < pending_blocklight_updates.length; ++i) {
         pending_blocklight_updates[i] = (byte)(par1Chunk.pending_blocklight_updates[i] ? -1 : 0);
      }

      par3NBTTagCompound.setByteArray("pending_blocklight_updates", pending_blocklight_updates);
      if (par1Chunk.getHadNaturallyOccurringMycelium()) {
         par3NBTTagCompound.setBoolean("had_naturally_occurring_mycelium", true);
      }

      if (par1Chunk.pending_sand_falls != null && par1Chunk.pending_sand_falls.size() > 0) {
         int[] pending_sand_falls = new int[par1Chunk.pending_sand_falls.size() * 2];
         int index = -1;

         Entry entry;
         for(Iterator iterator = par1Chunk.pending_sand_falls.entrySet().iterator(); iterator.hasNext(); pending_sand_falls[index] = (Integer)entry.getValue()) {
            entry = (Entry)iterator.next();
            ++index;
            pending_sand_falls[index] = (Integer)entry.getKey();
            ++index;
         }

         par3NBTTagCompound.setIntArray("pending_sand_falls", pending_sand_falls);
      }

   }
}
