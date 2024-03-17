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
public class BiomeGenUnderworldTrans extends BiomeGenBase {
   protected BiomeGenUnderworldTrans(int par1) {
      super(par1);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.removeEntityFromSpawnableLists(EntityCaveSpider.class);
      this.removeEntityFromSpawnableLists(EntityLongdead.class);
      this.removeEntityFromSpawnableLists(EntityAncientBoneLord.class);
      this.spawnableMonsterList.add(new SpawnListEntry(EntityCaveSpider.class, 80, 1, 2));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityLongdead.class, 80, 1, 2));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieLord.class, 2, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityWanderingWitch.class, 1, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityHellhound.class, 15, 1, 2));
   }
}
