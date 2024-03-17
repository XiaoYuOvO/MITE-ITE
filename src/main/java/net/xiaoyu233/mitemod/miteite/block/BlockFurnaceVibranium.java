package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.BlockFurnace;
import net.minecraft.IconRegister;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public abstract class BlockFurnaceVibranium extends BlockFurnace {
    protected BlockFurnaceVibranium(int par1, boolean par2) {
        super(par1, Materials.vibranium, par2);
    }

    @Override
    public void registerIcons(IconRegister mt) {
        this.blockIcon = mt.registerIcon("furnace/vibranium/side");
        this.furnaceIconFront = mt.registerIcon(this.isActive ? "furnace/vibranium/front_on" : "furnace/vibranium/front_off");
        this.furnaceIconTop = mt.registerIcon("furnace/vibranium/top");
    }

    @Override
    public int getMaxHeatLevel() {
        return 5;
    }

    @Override
    public int getActiveBlockID() {
        return Blocks.furnaceVibraniumBurning.blockID;
    }

    @Override
    public int getIdleBlockID() {
        return Blocks.furnaceVibraniumIdle.blockID;
    }
}
