package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.BlockOre;
import net.minecraft.EnumParticle;
import net.minecraft.Material;
import net.minecraft.World;

import java.util.Random;

public class BlockNetherAdamantiumOre extends BlockOre {
    public BlockNetherAdamantiumOre(int par1) {
        super(par1, Material.adamantium, 4);
    }

    @Override
    public void b(World par1World, int par2, int par3, int par4, Random par5Random) {
        for(int var6 = 0; var6 < 2; ++var6) {
            int var19 = par5Random.nextInt(2) * 2 - 1;
            int var20 = par5Random.nextInt(2) * 2 - 1;
            double x = (double)par2 + 0.5D + 0.25D * (double)var19;
            double y = (float)par3 + par5Random.nextFloat();
            double z = (double)par4 + 0.5D + 0.25D * (double)var20;
            double vY = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
            double vZ = par5Random.nextFloat() * 1.0F * (float) var20 * 0.125D;
            double vX = par5Random.nextFloat() * 1.0F * (float) var19 * 0.125D;
            par1World.spawnParticle(EnumParticle.largesmoke, x, y, z, vX, vY, vZ);
        }
    }
}
