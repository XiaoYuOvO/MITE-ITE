package net.xiaoyu233.mitemod.miteite.trans.block;


import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(BlockLightStone.class)
public class BlockGlowingStoneTrans extends Block{
    @Marker
    public BlockGlowingStoneTrans(int par1, Material par2Material) {
        super(par1, par2Material, (new BlockConstants()).setNeverConnectsWithFence());
        this.a(CreativeModeTab.b);
    }

    public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
        return false;
    }
}
