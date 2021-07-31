package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenUnderworld.class)
public class BiomeGenUnderworldTrans extends BiomeBase {
   protected BiomeGenUnderworldTrans(int par1) {
      super(par1);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.removeEntityFromSpawnableLists(EntityCaveSpider.class);
      this.removeEntityFromSpawnableLists(EntityLongdead.class);
      this.removeEntityFromSpawnableLists(EntityAncientBoneLord.class);
      this.spawnableMonsterList.add(new BiomeMeta(EntityCaveSpider.class, 80, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntityLongdead.class, 80, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieLord.class, 2, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWanderingWitch.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityHellhound.class, 15, 1, 2));
   }
}
