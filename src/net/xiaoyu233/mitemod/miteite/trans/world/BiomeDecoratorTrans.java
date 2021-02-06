package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.Random;

@Transform(BiomeDecorator.class)
public class BiomeDecoratorTrans {
    private static final int IRON_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.IRON_FREQUENCY_OVERWORLD);
    private static final int SILVER_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.SILVER_FREQUENCY_OVERWORLD);
    private static final int COPPER_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.COPPER_FREQUENCY_OVERWORLD);
    private static final int GOLD_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.GOLD_FREQUENCY_OVERWORLD);
    private static final int MITHRIL_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.MITHRIL_FREQUENCY_OVERWORLD);
    private static final int LAPIS_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.LAPIS_FREQUENCY_OVERWORLD);
    private static final int DIAMOND_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.DIAMOND_FREQUENCY_OVERWORLD);
    private static final int ADAMANTIUM_FREQUENCY_OVERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.ADAMANTIUM_FREQUENCY_OVERWORLD);

    private static final int IRON_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.IRON_FREQUENCY_UNDERWORLD);
    private static final int SILVER_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.SILVER_FREQUENCY_UNDERWORLD);
    private static final int COPPER_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.COPPER_FREQUENCY_UNDERWORLD);
    private static final int GOLD_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.GOLD_FREQUENCY_UNDERWORLD);
    private static final int MITHRIL_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.MITHRIL_FREQUENCY_UNDERWORLD);
    private static final int LAPIS_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.LAPIS_FREQUENCY_UNDERWORLD);
    private static final int DIAMOND_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.DIAMOND_FREQUENCY_UNDERWORLD);
    private static final int ADAMANTIUM_FREQUENCY_UNDERWORLD = MITEITEMod.CONFIG.get(Config.ConfigEntry.ADAMANTIUM_FREQUENCY_UNDERWORLD);
    @Link
    protected World a;
    @Link
    protected Random b;
    @Link
    protected int c;
    @Link
    protected int d;
    @Link
    protected BiomeBase e;
    @Link
    protected WorldGenerator f = new WorldGenClay(4);
    @Link
    protected WorldGenerator g;
    @Link
    protected WorldGenerator h;
    @Link
    protected WorldGenMinable i;
    @Link
    protected WorldGenMinable j;
    @Link
    protected WorldGenMinable k;
    @Link
    protected WorldGenMinable l;
    @Link
    protected WorldGenMinable copperGen;
    @Link
    protected WorldGenMinable silverGen;
    @Link
    protected WorldGenMinable mithrilGen;
    @Link
    protected WorldGenMinable adamantiteGen;

    @Link
    protected WorldGenMinable m;//Gold
    @Link
    protected WorldGenMinable n;//Redstone
    @Link
    protected WorldGenMinable o;//Diamond
    @Link
    protected WorldGenMinable p;//Lapis
    @Link
    protected WorldGenMinable silverfishGen;
    @Link
    protected WorldGenerator q;
    @Link
    protected WorldGenFlowers r;
    @Link
    protected WorldGenerator s;
    @Link
    protected WorldGenerator t;
    @Link
    protected WorldGenerator u;
    @Link
    protected WorldGenerator v;
    @Link
    protected WorldGenerator w;
    @Link
    protected WorldGenerator x;
    @Link
    protected int y;
    @Link
    protected int z;
    @Link
    protected int A;
    @Link
    protected int B;
    @Link
    protected int G;
    @Link
    protected int H;
    @Link
    protected int I;
    @Link
    public boolean K;
    @Link
    protected WorldGenPlants bush_gen;
    public BiomeDecoratorTrans(BiomeBase par1BiomeGenBase) {
        this.g = new WorldGenSand(7, Block.J.cF);
        this.h = new WorldGenSand(6, Block.K.cF);
        this.q = new WorldGenFlowers(Block.ai.cF);
        this.r = new WorldGenFlowers(Block.aj.cF);
        this.s = new WorldGenFlowers(Block.ak.cF);
        this.t = new WorldGenFlowers(Block.al.cF);
        this.u = new WorldGenHugeMushroom();
        this.v = new WorldGenReed();
        this.w = new WorldGenCactus();
        this.x = new WorldGenWaterLily();
        this.A = 2;
        this.B = 1;
        this.G = 1;
        this.H = 3;
        this.I = 1;
        this.K = true;
        this.e = par1BiomeGenBase;
        this.i = new WorldGenMinable(Block.A.cF, 32);
        this.j = new WorldGenMinable(Block.K.cF, 32);
        this.k = new WorldGenMinable(Block.N.cF, 16);
        this.copperGen = new WorldGenMinable(Block.oreCopper.cF, 6);
        this.silverGen = new WorldGenMinable(Block.oreSilver.cF, 6);
        this.m = new WorldGenMinable(Block.L.cF, 4);
        this.l = new WorldGenMinable(Block.M.cF, 6);
        this.mithrilGen = new WorldGenMinable(Block.oreMithril.cF, 3);
        this.adamantiteGen = new WorldGenMinable(Block.oreAdamantium.cF, 3);
        this.n = new WorldGenMinable(Block.aS.cF, 5);
        this.o = new WorldGenMinable(Block.aB.cF, 3);
        //Decrease Lapis
        this.p = new WorldGenMinable(Block.S.cF, 3);
        this.silverfishGen = new WorldGenMinable(Block.bq.cF, 2);
        this.bush_gen = new WorldGenPlants(Block.bush);
    }

    @Marker
    protected void genMinable(int frequency, WorldGenMinable world_gen_minable) {

    }

    @Marker
    protected void genMinable(int frequency, WorldGenMinable world_gen_minable, boolean vein_size_increases_with_depth){

    }

    protected void b() {
        if (this.a.isOverworld()) {
            this.genMinable(200, this.i);
            this.genMinable(200, this.j);
            this.genMinable(50, this.k);
            this.genMinable(COPPER_FREQUENCY_OVERWORLD, this.copperGen, true);
            this.genMinable(SILVER_FREQUENCY_OVERWORLD, this.silverGen, true);
            this.genMinable(GOLD_FREQUENCY_OVERWORLD, this.m, true);
            this.genMinable(IRON_FREQUENCY_OVERWORLD, this.l, true);
            this.genMinable(MITHRIL_FREQUENCY_OVERWORLD, this.mithrilGen, true);
            this.genMinable(5, this.silverfishGen, true);
            this.genMinable(10, this.n);
            this.genMinable(DIAMOND_FREQUENCY_OVERWORLD, this.o);
            this.genMinable(LAPIS_FREQUENCY_OVERWORLD, this.p);
            //Add adamantite to overworld
            if (MITEITEMod.CONFIG.get(Config.ConfigEntry.OVERWORLD_ADAMANTITE_ORE)){
                this.genMinable(ADAMANTIUM_FREQUENCY_OVERWORLD, this.adamantiteGen, false);
            }
        } else if (this.a.isUnderworld()) {
            this.genMinable(300, this.j);
            this.genMinable(COPPER_FREQUENCY_UNDERWORLD, this.copperGen, true);
            this.genMinable(SILVER_FREQUENCY_UNDERWORLD, this.silverGen, true);
            this.genMinable(GOLD_FREQUENCY_UNDERWORLD, this.m, true);
            this.genMinable(IRON_FREQUENCY_UNDERWORLD, this.l, true);
            this.genMinable(MITHRIL_FREQUENCY_UNDERWORLD, this.mithrilGen, true);
            this.genMinable(ADAMANTIUM_FREQUENCY_UNDERWORLD, this.adamantiteGen, true);
            this.genMinable(10, this.n);
            this.genMinable(DIAMOND_FREQUENCY_UNDERWORLD, this.o);
            this.genMinable(LAPIS_FREQUENCY_UNDERWORLD, this.p);
            if (this.a.underworld_y_offset != 0) {
                this.genMinable(50, this.silverfishGen);
            }
        } else if (!this.a.isTheEnd()) {
            Minecraft.setErrorMessage("generateOres: don't know how to handle world " + this.a);
        }

    }
}
