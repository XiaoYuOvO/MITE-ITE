package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.Random;

@Transform(ChunkProviderHell.class)
public class ChunkProviderHellTrans {
    @Link
    private Random i;
    @Link
    private NoiseGeneratorOctaves j;
    @Link
    private NoiseGeneratorOctaves k;
    @Link
    private NoiseGeneratorOctaves l;
    @Link
    private NoiseGeneratorOctaves m;
    @Link
    private NoiseGeneratorOctaves n;
    @Link
    public NoiseGeneratorOctaves a;
    @Link
    public NoiseGeneratorOctaves b;
    @Link
    private World o;
    @Link
    private double[] p;
    @Link
    public WorldGenNether c;
    @Link
    private double[] q;
    @Link
    private double[] r;
    @Link
    private double[] s;
    @Link
    private WorldGenBase t;
    @Link
    double[] d;
    @Link
    double[] e;
    @Link
    double[] f;
    @Link
    double[] g;
    @Link
    double[] h;
    public void a(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        this.c.a(this.o, this.i, par2, par3);

        int var6;
        int var7;
        int var8;
        int var9;
        for(var6 = 0; var6 < 8; ++var6) {
            var7 = var4 + this.i.nextInt(16) + 8;
            var8 = this.i.nextInt(120) + 4;
            var9 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenHellLava(Block.H.cF, false)).a(this.o, this.i, var7, var8, var9);
        }

        var6 = this.i.nextInt(this.i.nextInt(10) + 1) + 1;

        int var10;
        for(var7 = 0; var7 < var6; ++var7) {
            var8 = var4 + this.i.nextInt(16) + 8;
            var9 = this.i.nextInt(120) + 4;
            var10 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenFire()).a(this.o, this.i, var8, var9, var10);
        }

        var6 = this.i.nextInt(this.i.nextInt(10) + 1);

        for(var7 = 0; var7 < var6; ++var7) {
            var8 = var4 + this.i.nextInt(16) + 8;
            var9 = this.i.nextInt(120) + 4;
            var10 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenLightStone1()).a(this.o, this.i, var8, var9, var10);
        }

        for(var7 = 0; var7 < 10; ++var7) {
            var8 = var4 + this.i.nextInt(16) + 8;
            var9 = this.i.nextInt(128);
            var10 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenLightStone2()).a(this.o, this.i, var8, var9, var10);
        }

        if (this.i.nextInt(1) == 0) {
            var7 = var4 + this.i.nextInt(16) + 8;
            var8 = this.i.nextInt(128);
            var9 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenFlowers(Block.ak.cF)).a(this.o, this.i, var7, var8, var9);
        }

        for(int i = 0; i < 16; ++i) {
            var7 = var4 + this.i.nextInt(16) + 8;
            var8 = this.i.nextInt(128);
            var9 = var5 + this.i.nextInt(16) + 8;
            (new WorldGenDeadBush(Block.ad.cF)).a(this.o, this.i, var7, var8, var9);
        }

        //Decrease quartz
        WorldGenMinable var12 = new WorldGenMinable(Block.cu.cF, 9, Block.bg.cF);

        int var11;
        for(var8 = 0; var8 < 16; ++var8) {
            var9 = var4 + this.i.nextInt(16);
            var10 = this.i.nextInt(108) + 10;
            var11 = var5 + this.i.nextInt(16);
            var12.a(this.o, this.i, var9, var10, var11);
        }

        if (this.o.N().getEarliestMITEReleaseRunIn() >= 139) {
            var12 = (new WorldGenMinable(Block.L.cF, 8, Block.bg.cF)).setMinableBlockMetadata(2);

            for(var8 = 0; var8 < 2; ++var8) {
                var9 = var4 + this.i.nextInt(16);
                var10 = this.i.nextInt(108) + 10;
                var11 = var5 + this.i.nextInt(16);
                var12.a(this.o, this.i, var9, var10, var11);
            }
        }

        if (this.o.N().getEarliestMITEReleaseRunIn() >= 0) {
            var12 = (new WorldGenMinable(Block.bq.cF, 8, Block.bg.cF)).setMinableBlockMetadata(3);
            int num_veins = this.i.nextInt(7) + 2;

            for(var8 = 0; var8 < num_veins; ++var8) {
                var9 = var4 + this.i.nextInt(16);
                var10 = this.i.nextInt(108) + 10;
                var11 = var5 + this.i.nextInt(16);
                var12.a(this.o, this.i, var9, var10, var11);
            }
        }

        for(var8 = 0; var8 < 16; ++var8) {
            var9 = var4 + this.i.nextInt(16);
            var10 = this.i.nextInt(108) + 10;
            var11 = var5 + this.i.nextInt(16);
            (new WorldGenHellLava(Block.H.cF, true)).a(this.o, this.i, var9, var10, var11);
        }

        BlockFalling.fallInstantly = false;
    }
}
