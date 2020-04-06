package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import static net.minecraft.BlockWorkbench.getToolMaterial;

@Transform(BlockWorkbench.class)
public class BlockWorkbenchTrans {
    @Link
    private static final Material[] tool_materials;

    static {
        tool_materials = new Material[]{Material.flint, Material.copper, Material.silver, Material.gold, Material.f, Material.ancient_metal, Material.mithril, Material.adamantium,
                Materials.vibranium, Material.obsidian};
    }

    @Link
    private IIcon a;
    @Link
    private IIcon[] front_icons;
    @Link
    private IIcon icon_flint_top;
    @Link
    private IIcon icon_obsidian_top;
    @Link
    protected IIcon[] side_icons;

    public IIcon a(int side, int metadata) {
        if (metadata < 4) {
            return side == 1 ? this.icon_flint_top : Block.O.a(side, metadata);
        } else if (metadata > 11) {
            return side == 1 ? this.icon_obsidian_top : Block.O.a(side, metadata - 11);
        } else if (side == 0) {
            return Block.C.m(side);
        } else if (side == 1) {
            return this.a;
        } else {
            return side != 2 && side != 3 ? this.side_icons[metadata] : this.front_icons[metadata];
        }
    }

    public void a(mt par1IconRegister) {
        this.icon_flint_top = par1IconRegister.a("crafting_table/flint/top");
        this.icon_obsidian_top = par1IconRegister.a("crafting_table/obsidian/top");
        this.a = par1IconRegister.a("crafting_table_top");

        for(int i = 4; i < this.front_icons.length - 3; ++i) {
            this.front_icons[i] = par1IconRegister.a("crafting_table/" + getToolMaterial(i).getName() + "/front");
            this.side_icons[i] = par1IconRegister.a("crafting_table/" + getToolMaterial(i).getName() + "/side");
        }

    }

}
