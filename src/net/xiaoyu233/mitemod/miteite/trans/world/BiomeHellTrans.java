package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;

@Transform(BiomeHell.class)
public class BiomeHellTrans extends BiomeBase{
    public BiomeHellTrans(int par1) {
        super(par1);
        this.J.clear();
        this.K.clear();
        this.L.clear();
        this.M.clear();
        this.J.add(new BiomeMeta(EntityGhast.class, 50, 1, 2));
        this.J.add(new BiomeMeta(EntityPigZombie.class, 100, 1, 4));
        this.J.add(new BiomeMeta(EntityMagmaCube.class, 10, 4, 4));
        this.J.add(new BiomeMeta(EntityEarthElemental.class, 40, 1, 1));
        this.J.add(new BiomeMeta(EntityZombieLord.class, 1, 1, 1));
    }
}
