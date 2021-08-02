package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableLevel;
import net.xiaoyu233.mitemod.miteite.render.block.SideIconHolder;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;
import org.spongepowered.asm.mixin.SoftOverride;

import java.util.*;
import java.util.stream.Collectors;

public class BlockForgingTable extends Block implements IContainer,IBlockWithSubtypes {
    private final BlockSubtypes subtypes = new BlockSubtypes(Arrays.stream(ForgingTableLevel.values()).map(value -> value.name().toLowerCase(Locale.ROOT)).collect(Collectors.toList()).toArray(new String[0]));
    private final Map<Integer, SideIconHolder> textureMap = new HashMap<>();

    protected BlockForgingTable(int par1) {
        super(par1, Material.anvil, new BlockConstants());
        this.setCreativeTab(CreativeModeTab.tabDecorations);
        this.setMaxStackSize(1);
        this.setLightOpacity(0);
    }

    @Override
    public boolean isValidMetadata(int metadata) {
        return 0 <= metadata && metadata < ForgingTableLevel.values().length ;
    }

    @Override
    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata;
    }

    @Override
    @SoftOverride
    public String getItemDisplayName(ItemStack itemStack) {
        return Translator.get("tile.forging_table." + this.getNames()[itemStack.getItemSubtype()] + ".name");
    }

    @Override
    public String getMetadataNotes() {
        return "metadata is the level of the forging table,min is 0 and max is " + subtypes.getNames().length;
    }

    @Override
    public IIcon a(int side, int metadata) {
        SideIconHolder icon = this.textureMap.get(metadata);
        switch (side){
            //top
            case 1:
                return icon.getTexture_top();
            //bottom
            case 0:
                return icon.getTexture_bottom();
            case 2:
            case 3:
            case 5:
            case 4:
                return icon.getTexture_side();
        }
        return super.a(side, metadata);
    }

    @Override
    public void a(mt mt) {
        for (ForgingTableLevel value : ForgingTableLevel.values()) {
            SideIconHolder sideIconHolder = new SideIconHolder();
            sideIconHolder.setTexture_top(mt.a("forging_table/" + value.name().toLowerCase() + "/top"));
            sideIconHolder.setTexture_bottom(mt.a("forging_table/" + value.name().toLowerCase() + "/bottom"));
            sideIconHolder.setTexture_side(mt.a("forging_table/" + value.name().toLowerCase() + "/side"));
            this.textureMap.put(value.ordinal(),sideIconHolder);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int block_id, int metadata) {
        super.breakBlock(world, x, y, z, block_id, metadata);
        TileEntityForgingTable forgingTable = (TileEntityForgingTable)world.getBlockTileEntity(x, y, z);
        forgingTable.dropAllItems();
        world.removeBlockTileEntity(x, y, z);
    }

    //onBlockDestroyed

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityForgingTable();
    }


    @Override
    public void getItemStacks(int id, CreativeModeTab creative_tabs, List list) {
        super.getItemStacks(id, creative_tabs, list);
    }

    public void addItemBlockMaterials(ItemBlock item_block) {
        item_block.addMaterial(Material.iron);
    }

    @Override
    public String[] getTextures() {
        return this.subtypes.getTextures();
    }

    @Override
    public String[] getNames() {
        return this.subtypes.getNames();
    }

    public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
        return true;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if (!world.isAirOrPassableBlock(x, y + 1, z, false)) {
            return false;
        } else {
            if (player.onServer()) {
                TileEntityForgingTable tile_entity = (TileEntityForgingTable)world.getBlockTileEntity(x, y, z);
                if (tile_entity != null && !tile_entity.isUsing()) {
                    player.displayGUIForgingTable(x, y, z,tile_entity.getSlots());
                }else {
                    return false;
                }
            }

            return true;
        }
    }
}
