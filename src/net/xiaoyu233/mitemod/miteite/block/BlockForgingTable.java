package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;

import java.util.List;

public class BlockForgingTable extends Block implements IContainer {
    private IIcon TEXTURE_TOP;
    private IIcon TEXTURE_BOTOTM;
    private IIcon TEXTURE_SIDE;
    private IIcon TEXTURE_FRONT;
    private IIcon TEXTURE_BACK;
    protected BlockForgingTable(int par1) {
        super(par1, Material.g, new BlockConstants());
        this.a(CreativeModeTab.c);
        this.setMaxStackSize(1);
        this.k(0);
    }

    @Override
    public IIcon a(int side, int metadata) {
        switch (side){
            //top
            case 1:
                return TEXTURE_TOP;
            //bottom
            case 0:
                return TEXTURE_BOTOTM;
            case 2:
                return TEXTURE_FRONT;
            case 3:
                return TEXTURE_BACK;
            case 5:
            case 4:
                return TEXTURE_SIDE;
        }
        return super.a(side, metadata);
    }

    @Override
    public void a(mt mt) {
        this.TEXTURE_TOP = mt.a("forgingTable/top");
        this.TEXTURE_BOTOTM = mt.a("forgingTable/bottom");
        this.TEXTURE_FRONT = mt.a("forgingTable/front");
        this.TEXTURE_SIDE = mt.a("forgingTable/side");
        this.TEXTURE_BACK = mt.a("forgingTable/back");
    }

    @Override
    public TileEntity b(World world) {
        return new TileEntityForgingTable();
    }

    //onBlockDestroyed
    public void a(World world, int x, int y, int z, int block_id, int metadata) {
        super.a(world, x, y, z, block_id, metadata);
        TileEntityForgingTable forgingTable = (TileEntityForgingTable)world.r(x, y, z);
        forgingTable.dropAllItems();
        world.s(x, y, z);
    }


    @Override
    public void getItemStacks(int id, CreativeModeTab creative_tabs, List list) {
        super.getItemStacks(id, creative_tabs, list);
    }

    public void addItemBlockMaterials(ItemBlock item_block) {
        item_block.addMaterial(Material.adamantium);
    }

    public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
        return true;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityHuman player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        } else {
            if (player.onServer()) {
                TileEntityForgingTable tile_entity = (TileEntityForgingTable)world.r(x, y, z);
                if (tile_entity != null) {
                    player.displayGUIForgingTable(x, y, z,tile_entity.getSlots());
                }
            }

            return true;
        }
    }
}
