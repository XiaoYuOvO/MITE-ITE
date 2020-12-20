package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.BlockLeaves;
import net.minecraft.IBlockAccess;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(BlockLeaves.class)
public class BlockLeavesTrans {
    public boolean hidesAdjacentSide(IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
        return false;
    }
}
