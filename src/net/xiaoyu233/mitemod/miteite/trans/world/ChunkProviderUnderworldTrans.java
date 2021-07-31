package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.ChunkProviderUnderworld;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(ChunkProviderUnderworld.class)
public class ChunkProviderUnderworldTrans {
    @Shadow
    private Random hellRNG;
    @Shadow
    private World worldObj;
    @Overwrite
    public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte) {
        //Remove one layer of bedrock....Might be
    }
}
