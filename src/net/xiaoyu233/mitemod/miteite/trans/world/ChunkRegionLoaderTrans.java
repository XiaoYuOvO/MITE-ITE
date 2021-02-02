package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Transform(ChunkRegionLoader.class)
public class ChunkRegionLoaderTrans {
    private void a(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound) {
        if (Minecraft.inDevMode() && (par1Chunk instanceof EmptyChunk || par1Chunk.g())) {
            Minecraft.setErrorMessage("writeChunkToNBT: trying to write empty chunk to disk");
        }

        par3NBTTagCompound.a("xPos", par1Chunk.g);
        par3NBTTagCompound.a("zPos", par1Chunk.h);
        int xz_and_seed_checksum_component = this.calcXZAndSeedChecksumComponent(par1Chunk);
        if (par1Chunk.invalidate_checksum) {
            ++xz_and_seed_checksum_component;
        }

        par3NBTTagCompound.a("last_total_world_time", par1Chunk.last_total_world_time);
        par3NBTTagCompound.a("animals_spawned", par1Chunk.animals_spawned);
        par3NBTTagCompound.a("LastUpdate", par2World.I());
        par3NBTTagCompound.a("HeightMap", par1Chunk.f);
        par3NBTTagCompound.a("TerrainPopulated", par1Chunk.k);
        par3NBTTagCompound.a("InhabitedTime", par1Chunk.q);
        ChunkSection[] var4 = par1Chunk.i();
        NBTTagList var5 = new NBTTagList("Sections");
        boolean var6 = !par2World.t.g;
        ChunkSection[] var7 = var4;
        int var8 = var4.length;

        NBTTagCompound var11;
        for (int var9 = 0; var9 < var8; ++var9) {
            ChunkSection var10 = var7[var9];
            if (var10 != null) {
                var11 = new NBTTagCompound();
                var11.a("Y", (byte) (var10.d() >> 4 & 255));
                var11.a("Blocks", this.calcSectionChecksum(xz_and_seed_checksum_component, var10.d() >> 4 & 255, var10.g()));
                var11.a("BlockData", var10.g());
                if (var10.i() != null) {
                    var11.a("Add", var10.i().a);
                }

                var11.a("Data", var10.j().a);
                var11.a("BlockLight", var10.k().a);
                if (var6) {
                    var11.a("SkyLight", var10.l().a);
                } else {
                    var11.a("SkyLight", new byte[var10.k().a.length]);
                }

                var5.a(var11);
            }
        }

        par3NBTTagCompound.a("Sections", var5);
        par3NBTTagCompound.a("Biomes", par1Chunk.m());
        NBTTagList var16 = new NBTTagList();
        int entities_checksum = xz_and_seed_checksum_component;
        List[] entity_lists = par1Chunk.getEntityListsForReadingOnly();

        Iterator var18;
        label220:
        for (var8 = 0; var8 < entity_lists.length; ++var8) {
            var18 = entity_lists[var8].iterator();

            while (true) {
                while (true) {
                    if (!var18.hasNext()) {
                        continue label220;
                    }

                    Entity var21 = (Entity) var18.next();
                    if (Minecraft.inDevMode() && par1Chunk.checkForEntityDuplicates(var21)) {
                        Minecraft.setErrorMessage("writeChunkToNBT: " + var21.an() + " was found in the chunk's entityLists more than once");
                    }

                    if (!var21.isWrittenToChunkNBT()) {
                        if (Minecraft.inDevMode()) {
                            System.out.println("writeChunkToNBT: skipping " + (var21.M ? "dead " : (var21.n != null ? "mounted " : "")) + var21.an() + " (UUID=" + var21.aw() + ") in " + par1Chunk.e.t.l() + " because it is not supposed to be written to chunk NBT " + "{clName: " + var21.getClass().getName() + "}");
                        }
                    } else if (var21.M) {
                        if (!var21.is_unwanted_duplicate) {
                            Minecraft.setErrorMessage("Why is a dead " + var21.an() + " being written to the chunk? Skipping.");
                            if (!par1Chunk.e.isEntityObjectInLoadedEntityList(var21)) {
                                System.out.println("Furthermore, the entity isn't in the world's loaded entity list");
                            }
                        }
                    } else {
                        Chunk chunk;
                        String msg;
                        if (var21.last_chunk_saved_to != null && (var21.last_chunk_saved_to.g != par1Chunk.g || var21.last_chunk_saved_to.h != par1Chunk.h)) {
                            chunk = par2World.e(var21.last_chunk_saved_to.g, var21.last_chunk_saved_to.h);
                            if (chunk != null) {
                                chunk.e();
                                if (chunk.doesEntityWithMatchingClassAndUUIDExistInEntityLists(var21)) {
                                    msg = "writeChunkToNBT: " + var21.an() + " was found in another chunk at the time of saving";
                                    if (Minecraft.inDevMode()) {
                                        Minecraft.setErrorMessage(msg);
                                    } else {
                                        System.out.println(msg);
                                    }
                                }
                            }
                        }

                        if (var21.last_chunk_loaded_from != null && (var21.last_chunk_loaded_from.g != par1Chunk.g || var21.last_chunk_loaded_from.h != par1Chunk.h)) {
                            chunk = par2World.e(var21.last_chunk_loaded_from.g, var21.last_chunk_loaded_from.h);
                            if (chunk != null) {
                                chunk.e();
                                if (chunk.doesEntityWithMatchingClassAndUUIDExistInEntityLists(var21)) {
                                    msg = "writeChunkToNBT: " + var21.an() + " was found in another chunk at the time of saving (last_chunk_loaded_from)";
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
                        if (var21.d(var11)) {
                            var16.a(var11);
                            var21.last_chunk_saved_to = par1Chunk;
                            var21.last_chunk_saved_to_entity_list_index = var8;
                        }
                    }
                }
            }
        }

        par3NBTTagCompound.a("Entities", entities_checksum);
        par3NBTTagCompound.a("EntityData", var16);
        NBTTagList var17 = new NBTTagList();
        var18 = par1Chunk.i.values().iterator();
        int tile_entities_checksum = xz_and_seed_checksum_component;

        while (var18.hasNext()) {
            TileEntity var22 = (TileEntity) var18.next();
            tile_entities_checksum += this.calcTileEntityChecksum(var22);
            var11 = new NBTTagCompound();
            var22.b(var11);
            var17.a(var11);
        }

        par3NBTTagCompound.a("TileEntities", tile_entities_checksum);
        par3NBTTagCompound.a("TileEntityData", var17);
        List var20 = par2World.a(par1Chunk, false);
        if (var20 != null) {
            long var19 = par2World.I();
            NBTTagList var12 = new NBTTagList();
            Iterator var13 = var20.iterator();

            while (var13.hasNext()) {
                NextTickListEntry var14 = (NextTickListEntry) var13.next();
                NBTTagCompound var15 = new NBTTagCompound();
                var15.a("i", var14.d);
                var15.a("x", var14.a);
                var15.a("y", var14.b);
                var15.a("z", var14.c);
                var15.a("t", (int) (var14.e - var19));
                var15.a("p", var14.f);
                var12.a(var15);
            }

            par3NBTTagCompound.a("TileTicks", var12);
        }

        if (par1Chunk.has_had_lighting_checked) {
            par3NBTTagCompound.a("has_had_lighting_checked", true);
        }

        if (par1Chunk.t) {
            par3NBTTagCompound.a("isGapLightingUpdated", true);
        }

        byte[] update_skylight_columns = new byte[par1Chunk.c.length];

        for (int i = 0; i < update_skylight_columns.length; ++i) {
            update_skylight_columns[i] = (byte) (par1Chunk.c[i] ? -1 : 0);
        }

        par3NBTTagCompound.a("update_skylight_columns", update_skylight_columns);
        par3NBTTagCompound.a("skylight_bottom", par1Chunk.skylight_bottom);
        byte[] pending_blocklight_updates;
        int i;
        if (par2World.hasSkylight()) {
            if (par1Chunk.num_pending_skylight_updates > 0) {
                pending_blocklight_updates = new byte[par1Chunk.num_pending_skylight_updates * 2];
                System.arraycopy(par1Chunk.pending_skylight_update_coords, 0, pending_blocklight_updates, 0, pending_blocklight_updates.length);
                par3NBTTagCompound.a("pending_skylight_update_coords", pending_blocklight_updates);
            }

            pending_blocklight_updates = new byte[par1Chunk.pending_skylight_updates.length];

            for (i = 0; i < pending_blocklight_updates.length; ++i) {
                pending_blocklight_updates[i] = (byte) (par1Chunk.pending_skylight_updates[i] ? -1 : 0);
            }

            par3NBTTagCompound.a("pending_skylight_updates", pending_blocklight_updates);
        }

        if (par1Chunk.num_pending_blocklight_updates > 0) {
            pending_blocklight_updates = new byte[par1Chunk.num_pending_blocklight_updates * 2];
            System.arraycopy(par1Chunk.pending_blocklight_update_coords, 0, pending_blocklight_updates, 0, pending_blocklight_updates.length);
            par3NBTTagCompound.a("pending_blocklight_update_coords", pending_blocklight_updates);
        }

        pending_blocklight_updates = new byte[par1Chunk.pending_blocklight_updates.length];

        for (i = 0; i < pending_blocklight_updates.length; ++i) {
            pending_blocklight_updates[i] = (byte) (par1Chunk.pending_blocklight_updates[i] ? -1 : 0);
        }

        par3NBTTagCompound.a("pending_blocklight_updates", pending_blocklight_updates);
        if (par1Chunk.getHadNaturallyOccurringMycelium()) {
            par3NBTTagCompound.a("had_naturally_occurring_mycelium", true);
        }

        if (par1Chunk.pending_sand_falls != null && par1Chunk.pending_sand_falls.size() > 0) {
            int[] pending_sand_falls = new int[par1Chunk.pending_sand_falls.size() * 2];
            int index = -1;

            Map.Entry entry;
            for (Iterator iterator = par1Chunk.pending_sand_falls.entrySet().iterator(); iterator.hasNext(); pending_sand_falls[index] = (Integer) entry.getValue()) {
                entry = (Map.Entry) iterator.next();
                ++index;
                pending_sand_falls[index] = (Integer) entry.getKey();
                ++index;
            }

            par3NBTTagCompound.a("pending_sand_falls", pending_sand_falls);
        }

    }

    @Marker
    private final int calcXZAndSeedChecksumComponent(Chunk chunk) {
        return 0;
    }

    @Marker
    private final int calcSectionChecksum(int xz_and_seed_checksum_component, int section_y, byte[] bytes) {
        return 0;
    }
    @Marker
    private final int calcEntityChecksum(Entity entity) {
        return 0;
    }

    @Marker
    private final int calcTileEntityChecksum(TileEntity tile_entity){
        return 0;
    }
}
