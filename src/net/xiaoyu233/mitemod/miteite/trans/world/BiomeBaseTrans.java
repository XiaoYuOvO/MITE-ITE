package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.entity.EntityAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(BiomeBase.class)
public class BiomeBaseTrans {
   @Shadow
   @Final
   public final int biomeID;
   @Shadow
   private final boolean enableRain;
   @Shadow
   public int field_76754_C;
   @Shadow
   public byte fillerBlock;
   @Shadow
   public float maxHeight;
   @Shadow
   public float minHeight;
   @Shadow
   public float rainfall;
   @Shadow
   public float temperature;
   @Shadow
   public BiomeDecorator theBiomeDecorator;
   @Shadow
   public byte topBlock;
   @Shadow
   public int waterColorMultiplier;
   @Shadow
   protected List spawnableCaveCreatureList;
   @Shadow
   protected List spawnableCreatureList;
   @Shadow
   protected List spawnableMonsterList;
   @Shadow
   protected List spawnableWaterCreatureList;
   @Shadow
   protected WorldGenBigTree worldGeneratorBigTree;
   @Shadow
   protected WorldGenForest worldGeneratorForest;
   @Shadow
   protected WorldGenSwampTree worldGeneratorSwamp;
   @Shadow
   protected WorldGenTrees worldGeneratorTrees;

   protected BiomeBaseTrans(int par1) {
      this.topBlock = (byte)Block.grass.blockID;
      this.fillerBlock = (byte)Block.dirt.blockID;
      this.field_76754_C = 5169201;
      this.minHeight = 0.1F;
      this.maxHeight = 0.3F;
      this.temperature = 0.5F;
      this.rainfall = 0.5F;
      this.waterColorMultiplier = 16777215;
      this.spawnableMonsterList = new ArrayList();
      this.spawnableCreatureList = new ArrayList();
      this.spawnableWaterCreatureList = new ArrayList();
      this.spawnableCaveCreatureList = new ArrayList();
      this.enableRain = true;
      this.worldGeneratorTrees = new WorldGenTrees(false);
      this.worldGeneratorBigTree = new WorldGenBigTree(false);
      this.worldGeneratorForest = new WorldGenForest(false);
      this.worldGeneratorSwamp = new WorldGenSwampTree();
      this.biomeID = par1;
      BiomeBase.biomeList[par1] = ReflectHelper.dyCast(this);
      this.theBiomeDecorator = this.createBiomeDecorator();
      this.spawnableCreatureList.add(new BiomeMeta(EntitySheep.class, 10, 1, 1));
      this.spawnableCreatureList.add(new BiomeMeta(EntityPig.class, 10, 1, 1));
      this.spawnableCreatureList.add(new BiomeMeta(EntityChicken.class, 10, 1, 1));
      this.spawnableCreatureList.add(new BiomeMeta(EntityCow.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntitySpider.class, 80, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombie.class, 100, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntitySkeleton.class, 100, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityCreeper.class, 100, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntitySlime.class, 100, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityEnderman.class, 10, 1, 4));
      this.spawnableWaterCreatureList.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
      this.spawnableCaveCreatureList.add(new BiomeMeta(EntityBat.class, 100, 8, 8));
      this.spawnableCaveCreatureList.add(new BiomeMeta(EntityVampireBat.class, 20, 8, 8));
      this.spawnableCaveCreatureList.add(new BiomeMeta(EntityNightwing.class, 4, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityGhoul.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWight.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityInvisibleStalker.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityDemonSpider.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityHellhound.class, 13, 1, 2));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWoodSpider.class, 20, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityShadow.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityRevenant.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityEarthElemental.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityJelly.class, 30, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityBlob.class, 30, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityOoze.class, 20, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityPudding.class, 30, 1, 4));
      this.spawnableMonsterList.add(new BiomeMeta(EntityClayGolem.class, 50, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityBoneLord.class, 5, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityPhaseSpider.class, 5, 1, 4));
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.spawnableMonsterList.add(new BiomeMeta(EntityGiantZombie.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityGhast.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAncientBoneLord.class, 10, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityZombieLord.class, 2, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityAnnihilationSkeleton.class, 1, 1, 1));
      this.spawnableMonsterList.add(new BiomeMeta(EntityWanderingWitch.class, 1, 1, 1));
   }

   @Shadow
   private BiomeDecorator createBiomeDecorator() {
      return null;
   }
}
