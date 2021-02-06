package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;

@Transform(BiomeGenUnderworld.class)
public class BiomeGenUnderworldTrans extends BiomeBase{
    public BiomeGenUnderworldTrans(int id) {
        super(id);
        this.removeEntityFromSpawnableLists(EntitySkeleton.class);
        this.removeEntityFromSpawnableLists(EntityZombie.class);
        this.removeEntityFromSpawnableLists(EntityGhoul.class);
        this.removeEntityFromSpawnableLists(EntityRevenant.class);
        this.removeEntityFromSpawnableLists(EntityBoneLord.class);
        this.J.add(new BiomeMeta(EntityCaveSpider.class, 40, 1, 2));
        this.J.add(new BiomeMeta(EntityLongdead.class, 40, 1, 2));
        this.J.add(new BiomeMeta(EntityAncientBoneLord.class, 5, 1, 1));
        this.J.add(new BiomeMeta(EntityZombieLord.class, 1, 1, 1));
    }
}
