package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombiePigmanLord;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeHell.class)
public class BiomeHellTrans extends BiomeBase {
   protected BiomeHellTrans(int par1) {
      super(par1);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   public void injectCtor(CallbackInfo callbackInfo) {
      this.spawnableMonsterList.add(new BiomeMeta(EntityGhast.class, 50, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntityPigZombie.class, 100, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityMagmaCube.class, 10, 4, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityEarthElemental.class, 40, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombiePigmanLord.class, 1, 1, 1));
   }
}
