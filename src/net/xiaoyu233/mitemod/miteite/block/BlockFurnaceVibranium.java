package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.BlockFurnace;
import net.minecraft.mt;
import net.xiaoyu233.mitemod.miteite.item.Materials;

public class BlockFurnaceVibranium extends BlockFurnace {
    protected BlockFurnaceVibranium(int par1, boolean par2) {
        super(par1, Materials.vibranium, par2);
    }

    @Override
    public void a(mt mt) {
        this.cW = mt.a("furnace/vibranium/side");
        this.e = mt.a(this.isActive ? "furnace/vibranium/front_on" : "furnace/vibranium/front_off");
        this.d = mt.a("furnace/vibranium/top");
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
