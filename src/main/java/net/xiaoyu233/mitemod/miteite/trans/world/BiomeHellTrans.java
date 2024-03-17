package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombiePigmanLord;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenHell.class)
public class BiomeHellTrans extends BiomeGenBase {
   protected BiomeHellTrans(int par1) {
      super(par1);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.spawnableMonsterList.clear();
      this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 100, 1, 2));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 200, 1, 4));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 20, 4, 4));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityEarthElemental.class, 80, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityZombiePigmanLord.class, 1, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityHellhound.class, 20, 1, 2));
   }
}
