package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;

import java.util.HashMap;
import java.util.Map;

@Transform(TileEntity.class)
public class TileEntityTrans {
    @Link
    private static final Map a = new HashMap();
    @Link
    private static final Map b = new HashMap();
    @Marker
    private static void a(Class par0Class, String par1Str) {}
    static {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityStrongbox.class, "Strongbox");
        a(TileEntityEnderChest.class, "EnderChest");
        a(TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntityDropper.class, "Dropper");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantTable.class, "EnchantTable");
        a(TileEntityEnderPortal.class, "Airportal");
        a(TileEntityCommand.class, "Control");
        a(TileEntityBeacon.class, "Beacon");
        a(TileEntitySkull.class, "Skull");
        a(TileEntityLightDetector.class, "DLDetector");
        a(TileEntityHopper.class, "Hopper");
        a(TileEntityComparator.class, "Comparator");
        a(TileEntityAnvil.class, "Anvil");
        a(TileEntityForgingTable.class, "ForgingTable");
    }
}
