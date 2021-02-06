package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Transform(BiomeBase.class)
public class BiomeBaseTrans {
    @Link
    protected List J;
    @Link
    protected List K;
    @Link
    protected List L;
    @Link
    protected List M;
    @Link
    public byte A;
    @Link
    public byte B;
    @Link
    public int C;
    @Link
    public float D;
    @Link
    public float E;
    @Link
    public float F;
    @Link
    public float G;
    @Link
    public int H;
    @Link
    private boolean S;
    @Link
    private final boolean T;
    @Link
    public final int N;
    @Link
    protected WorldGenTrees O;
    @Link
    protected WorldGenBigTree P;
    @Link
    protected WorldGenForest Q;
    @Link
    protected WorldGenSwampTree R;
    @Link
    public BiomeDecorator I;

    protected BiomeBaseTrans(int par1) {
        this.A = (byte) Block.z.cF;
        this.B = (byte)Block.A.cF;
        this.C = 5169201;
        this.D = 0.1F;
        this.E = 0.3F;
        this.F = 0.5F;
        this.G = 0.5F;
        this.H = 16777215;
        this.J = new ArrayList();
        this.K = new ArrayList();
        this.L = new ArrayList();
        this.M = new ArrayList();
        this.T = true;
        this.O = new WorldGenTrees(false);
        this.P = new WorldGenBigTree(false);
        this.Q = new WorldGenForest(false);
        this.R = new WorldGenSwampTree();
        this.N = par1;
        BiomeBase.a[par1] = ReflectHelper.dyCast(this);
        this.I = this.a();
        this.K.add(new BiomeMeta(EntitySheep.class, 10, 1, 1));
        this.K.add(new BiomeMeta(EntityPig.class, 10, 1, 1));
        this.K.add(new BiomeMeta(EntityChicken.class, 10, 1, 1));
        this.K.add(new BiomeMeta(EntityCow.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntitySpider.class, 80, 1, 2));
        this.J.add(new BiomeMeta(EntityZombie.class, 100, 1, 4));
        this.J.add(new BiomeMeta(EntitySkeleton.class, 100, 1, 4));
        this.J.add(new BiomeMeta(EntityCreeper.class, 100, 1, 2));
        this.J.add(new BiomeMeta(EntitySlime.class, 100, 1, 4));
        this.J.add(new BiomeMeta(EntityEnderman.class, 10, 1, 4));
        this.L.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
        this.M.add(new BiomeMeta(EntityBat.class, 100, 8, 8));
        this.M.add(new BiomeMeta(EntityVampireBat.class, 20, 8, 8));
        this.M.add(new BiomeMeta(EntityNightwing.class, 4, 1, 4));
        this.J.add(new BiomeMeta(EntityGhoul.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityWight.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityInvisibleStalker.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityDemonSpider.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityHellhound.class, 10, 1, 2));
        this.J.add(new BiomeMeta(EntityWoodSpider.class, 20, 1, 1));
        this.J.add(new BiomeMeta(EntityShadow.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityRevenant.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityEarthElemental.class, 10, 1, 1));
        this.J.add(new BiomeMeta(EntityJelly.class, 30, 1, 4));
        this.J.add(new BiomeMeta(EntityBlob.class, 30, 1, 4));
        this.J.add(new BiomeMeta(EntityOoze.class, 20, 1, 4));
        this.J.add(new BiomeMeta(EntityPudding.class, 30, 1, 4));
        this.J.add(new BiomeMeta(EntityClayGolem.class, 50, 1, 1));
        this.J.add(new BiomeMeta(EntityBoneLord.class, 5, 1, 1));
        this.J.add(new BiomeMeta(EntityPhaseSpider.class, 5, 1, 4));
        //MITEITE
        this.J.add(new BiomeMeta(EntityGiantZombie.class, 10,1,1));
        this.J.add(new BiomeMeta(EntityGhast.class, 10,1,1));
        this.J.add(new BiomeMeta(EntityAncientBoneLord.class, 10,1,1));
        this.J.add(new BiomeMeta(EntityZombieLord.class, 1,1, 1));
    }

    @Marker
    private BiomeDecorator a() {
        return null;
    }
}
