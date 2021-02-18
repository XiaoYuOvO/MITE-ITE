package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;

import java.io.DataInput;
import java.io.IOException;

@Transform(Packet100OpenWindow.class)
public class PacketOpenWindowTrans {
    public static final int TYPE_FORGING_TABLE = 14;
    @Link
    public boolean e;
    @Link
    public int f;
    @Link
    public int a;
    @Link
    public int b;
    @Link
    public String c;
    @Link
    public int d;
    @Link
    public int x;
    @Link
    public int y;
    @Link
    public int z;
    @Link
    public boolean has_set_coords;
    @Marker
    private Entity getEntityByID(EntityHuman player, int id) {
        return null;
    }
    @Marker
    public boolean hasTileEntity() {
        return false;
    }
    public void a(DataInput par1DataInput) throws IOException {
        this.a = par1DataInput.readByte() & 255;
        this.b = par1DataInput.readByte() & 255;
        this.c = Packet.a(par1DataInput, 32767);
        this.d = par1DataInput.readByte() & 255;
        this.e = par1DataInput.readBoolean();
        if (this.b == 11) {
            this.f = par1DataInput.readInt();
        }

        if (this.hasCoords()) {
            this.x = par1DataInput.readInt();
            this.y = par1DataInput.readInt();
            this.z = par1DataInput.readInt();
        }

    }

    @Marker
    public boolean hasCoords(){
        return false;
    }

    public void handleOpenWindow(bdi player) {
        bdd world = player.q.getAsWorldClient();
        TileEntity tile_entity = world.r(this.x, this.y, this.z);
        if (this.hasTileEntity() && tile_entity == null) {
            Minecraft.setErrorMessage("handleOpenWindow: no tile entity found at " + StringHelper.getCoordsAsString(this.x, this.y, this.z));
        }

        if (this.b == 0) {
            player.displayGUIChest(this.x, this.y, this.z, new InventorySubcontainer(this.c, this.e, this.d));
            player.bp.d = this.a;
        } else if (this.b == 1) {
            player.b(this.x, this.y, this.z);
            player.bp.d = this.a;
        } else if (this.b == 2) {
            TileEntityFurnace var4 = (TileEntityFurnace)tile_entity;
            if (this.e) {
                var4.setCustomInvName(this.c);
            }

            player.a(var4);
            player.bp.d = this.a;
        } else if (this.b == 3) {
            TileEntityDispenser var7 = (TileEntityDispenser)tile_entity;
            if (this.e) {
                var7.setCustomInvName(this.c);
            }

            player.a(var7);
            player.bp.d = this.a;
        } else if (this.b == 4) {
            player.a(this.x, this.y, this.z, this.e ? this.c : null);
            player.bp.d = this.a;
        } else if (this.b == 5) {
            TileEntityBrewingStand var5 = (TileEntityBrewingStand)tile_entity;
            if (this.e) {
                var5.setCustomInvName(this.c);
            }

            player.a(var5);
            player.bp.d = this.a;
        } else if (this.b == 6) {
            player.a(new tz(player), this.e ? this.c : null);
            player.bp.d = this.a;
        } else if (this.b == 7) {
            TileEntityBeacon var8 = (TileEntityBeacon)tile_entity;
            player.a(var8);
            if (this.e) {
                var8.setCustomInvName(this.c);
            }

            player.bp.d = this.a;
        } else if (this.b == 8) {
            tile_entity.setCustomInvName(this.c);
            player.c(this.x, this.y, this.z);
            player.bp.d = this.a;
        } else {
            TileEntityHopper var3;
            if (this.b == 9) {
                var3 = (TileEntityHopper)tile_entity;
                if (this.e) {
                    var3.setCustomInvName(this.c);
                }

                player.a(var3);
                player.bp.d = this.a;
            } else if (this.b == 10) {
                TileEntityDropper var6 = (TileEntityDropper)tile_entity;
                if (this.e) {
                    var6.setCustomInvName(this.c);
                }

                player.a(var6);
                player.bp.d = this.a;
            } else if (this.b == 11) {
                Entity var9 = this.getEntityByID(player, this.f);
                if (var9 != null && var9 instanceof EntityHorse) {
                    player.a((EntityHorse)var9, new InventoryHorseChest(this.c, this.e, this.d));
                    player.bp.d = this.a;
                }
            } else if (this.b == 12) {
                player.displayGUIChestForMinecart(new InventorySubcontainer(this.c, this.e, this.d));
                player.bp.d = this.a;
            } else if (this.b == 13) {
                var3 = new TileEntityHopper();
                if (this.e) {
                    var3.setCustomInvName(this.c);
                }

                player.a(var3);
                player.bp.d = this.a;
            } else if (this.b == TYPE_FORGING_TABLE){
                player.displayGUIForgingTable(this.x, this.y, this.z,new ForgingTableSlots(new InventorySubcontainer(this.c, this.e, this.d)));
                player.bp.d = this.a;
            }else {
                Minecraft.setErrorMessage("handleOpenWindow: type not handled " + this.b);
            }
        }

    }
}
