package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(ChunkProviderHell.class)
public class ChunkProviderHellTrans {
   @Shadow
   public WorldGenNether genNetherBridge;
   @Shadow
   public NoiseGeneratorOctaves netherNoiseGen6;
   @Shadow
   public NoiseGeneratorOctaves netherNoiseGen7;
   @Shadow
   double[] noiseData1;
   @Shadow
   double[] noiseData2;
   @Shadow
   double[] noiseData3;
   @Shadow
   double[] noiseData4;
   @Shadow
   double[] noiseData5;
   @Shadow
   private double[] gravelNoise;
   @Shadow
   private Random hellRNG;
   @Shadow
   private WorldGenBase netherCaveGenerator;
   @Shadow
   private NoiseGeneratorOctaves netherNoiseGen1;
   @Shadow
   private NoiseGeneratorOctaves netherNoiseGen2;
   @Shadow
   private NoiseGeneratorOctaves netherNoiseGen3;
   @Shadow
   private double[] netherrackExclusivityNoise;
   @Shadow
   private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
   @Shadow
   private double[] noiseField;
   @Shadow
   private NoiseGeneratorOctaves slowsandGravelNoiseGen;
   @Shadow
   private double[] slowsandNoise;
   @Shadow
   private World worldObj;

   @Overwrite
   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
      BlockFalling.fallInstantly = true;
      int var4 = par2 * 16;
      int var5 = par3 * 16;
      this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);

      int var6;
      int var7;
      int var8;
      int var9;
      for(var6 = 0; var6 < 8; ++var6) {
         var7 = var4 + this.hellRNG.nextInt(16) + 8;
         var8 = this.hellRNG.nextInt(120) + 4;
         var9 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenHellLava(Block.lavaMoving.blockID, false)).generate(this.worldObj, this.hellRNG, var7, var8, var9);
      }

      var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;

      int var10;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var4 + this.hellRNG.nextInt(16) + 8;
         var9 = this.hellRNG.nextInt(120) + 4;
         var10 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenFire()).generate(this.worldObj, this.hellRNG, var8, var9, var10);
      }

      var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);

      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var4 + this.hellRNG.nextInt(16) + 8;
         var9 = this.hellRNG.nextInt(120) + 4;
         var10 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenLightStone1()).generate(this.worldObj, this.hellRNG, var8, var9, var10);
      }

      for(var7 = 0; var7 < 10; ++var7) {
         var8 = var4 + this.hellRNG.nextInt(16) + 8;
         var9 = this.hellRNG.nextInt(128);
         var10 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenLightStone2()).generate(this.worldObj, this.hellRNG, var8, var9, var10);
      }

      if (this.hellRNG.nextInt(1) == 0) {
         var7 = var4 + this.hellRNG.nextInt(16) + 8;
         var8 = this.hellRNG.nextInt(128);
         var9 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.hellRNG, var7, var8, var9);
      }

      for(int i = 0; i < 16; ++i) {
         var7 = var4 + this.hellRNG.nextInt(16) + 8;
         var8 = this.hellRNG.nextInt(128);
         var9 = var5 + this.hellRNG.nextInt(16) + 8;
         (new WorldGenDeadBush(Block.deadBush.blockID)).generate(this.worldObj, this.hellRNG, var7, var8, var9);
      }

      WorldGenMinable var12 = new WorldGenMinable(Block.oreNetherQuartz.blockID, 9, Block.netherrack.blockID);

      int var11;
      for(var8 = 0; var8 < 16; ++var8) {
         var9 = var4 + this.hellRNG.nextInt(16);
         var10 = this.hellRNG.nextInt(80) + 35;
         var11 = var5 + this.hellRNG.nextInt(16);
         var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
      }

      if (this.worldObj.getWorldInfo().getEarliestMITEReleaseRunIn() >= 139) {
         var12 = (new WorldGenMinable(Block.oreGold.blockID, 8, Block.netherrack.blockID)).setMinableBlockMetadata(2);

         for(var8 = 0; var8 < 2; ++var8) {
            var9 = var4 + this.hellRNG.nextInt(16);
            var10 = this.hellRNG.nextInt(80) + 35;
            var11 = var5 + this.hellRNG.nextInt(16);
            var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
         }
      }

      var12 = (new WorldGenMinable(Blocks.netherAdamantiumOre.blockID, Configs.WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_VEIN.get(), Block.netherrack.blockID)).setMinableBlockMetadata(0);

      int count = this.hellRNG.nextInt(Configs.WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_CHUNK.get()) + 1;
      for(var8 = 0; var8 < count; ++var8) {
         var9 = var4 + this.hellRNG.nextInt(16);
         var10 = this.hellRNG.nextInt(80) + 35;
         var11 = var5 + this.hellRNG.nextInt(16);
         var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
      }

      if (this.worldObj.getWorldInfo().getEarliestMITEReleaseRunIn() >= 0) {
         var12 = (new WorldGenMinable(Block.silverfish.blockID, 8, Block.netherrack.blockID)).setMinableBlockMetadata(3);
         int num_veins = this.hellRNG.nextInt(7) + 2;

         for(var8 = 0; var8 < num_veins; ++var8) {
            var9 = var4 + this.hellRNG.nextInt(16);
            var10 = this.hellRNG.nextInt(108) + 10;
            var11 = var5 + this.hellRNG.nextInt(16);
            var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
         }
      }

      for(var8 = 0; var8 < 16; ++var8) {
         var9 = var4 + this.hellRNG.nextInt(16);
         var10 = this.hellRNG.nextInt(108) + 10;
         var11 = var5 + this.hellRNG.nextInt(16);
         (new WorldGenHellLava(Block.lavaMoving.blockID, true)).generate(this.worldObj, this.hellRNG, var9, var10, var11);
      }

      BlockFalling.fallInstantly = false;
   }
}
