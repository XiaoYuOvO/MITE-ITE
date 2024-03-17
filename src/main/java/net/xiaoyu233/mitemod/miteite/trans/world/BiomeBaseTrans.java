package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BiomeGenBase.class)
public class BiomeBaseTrans {
   @Shadow
   protected List<SpawnListEntry> spawnableMonsterList;
   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.spawnableMonsterList.add(new SpawnListEntry(EntityGiantZombie.class, 10, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 10, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieLord.class, 2, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityWanderingWitch.class, 1, 1, 1));
   }

}
