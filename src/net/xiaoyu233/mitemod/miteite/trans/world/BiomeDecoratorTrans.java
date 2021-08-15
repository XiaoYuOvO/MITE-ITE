package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public class BiomeDecoratorTrans {
   private static final int ADAMANTIUM_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.ADAMANTIUM_FREQUENCY_OVERWORLD.get();
   private static final int ADAMANTIUM_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.ADAMANTIUM_FREQUENCY_UNDERWORLD.get();
   private static final int COPPER_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.COPPER_FREQUENCY_OVERWORLD.get();
   private static final int COPPER_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.COPPER_FREQUENCY_UNDERWORLD.get();
   private static final int DIAMOND_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.DIAMOND_FREQUENCY_OVERWORLD.get();
   private static final int DIAMOND_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.DIAMOND_FREQUENCY_UNDERWORLD.get();
   private static final int GOLD_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.GOLD_FREQUENCY_OVERWORLD.get();
   private static final int GOLD_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.GOLD_FREQUENCY_UNDERWORLD.get();
   private static final int IRON_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.IRON_FREQUENCY_OVERWORLD.get();
   private static final int IRON_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.IRON_FREQUENCY_UNDERWORLD.get();
   private static final int LAPIS_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.LAPIS_FREQUENCY_OVERWORLD.get();
   private static final int LAPIS_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.LAPIS_FREQUENCY_UNDERWORLD.get();
   private static final int MITHRIL_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.MITHRIL_FREQUENCY_OVERWORLD.get();
   private static final int MITHRIL_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.MITHRIL_FREQUENCY_UNDERWORLD.get();
   private static final int SILVER_FREQUENCY_OVERWORLD = Configs.WorldGen.Overworld.SILVER_FREQUENCY_OVERWORLD.get();
   private static final int SILVER_FREQUENCY_UNDERWORLD = Configs.WorldGen.Underworld.SILVER_FREQUENCY_UNDERWORLD.get();
   @Shadow
   public boolean generateLakes;
   @Shadow
   protected WorldGenMinable adamantiteGen;
   @Shadow
   protected WorldGenerator bigMushroomGen;
   @Shadow
   protected BiomeBase biome;
   @Shadow
   protected WorldGenPlants bush_gen;
   @Shadow
   protected WorldGenerator cactusGen;
   @Shadow
   protected int chunk_X;
   @Shadow
   protected int chunk_Z;
   @Shadow
   protected WorldGenerator clayGen = new WorldGenClay(4);
   @Shadow
   protected int clayPerChunk;
   @Shadow
   protected WorldGenMinable coalGen;
   @Shadow
   protected WorldGenMinable copperGen;
   @Shadow
   protected World currentWorld;
   @Shadow
   protected WorldGenMinable diamondGen;
   @Shadow
   protected WorldGenMinable dirtGen;
   @Shadow
   protected int flowersPerChunk;
   @Shadow
   protected WorldGenMinable goldGen;
   @Shadow
   protected int grassPerChunk;
   @Shadow
   protected WorldGenerator gravelAsSandGen;
   @Shadow
   protected WorldGenMinable gravelGen;
   @Shadow
   protected WorldGenMinable ironGen;
   @Shadow
   protected WorldGenMinable lapisGen;
   @Shadow
   protected WorldGenMinable mithrilGen;
   @Shadow
   protected WorldGenerator mushroomBrownGen;
   @Shadow
   protected WorldGenerator mushroomRedGen;
   @Shadow
   protected WorldGenFlowers plantRedGen;
   @Shadow
   protected WorldGenerator plantYellowGen;
   @Shadow
   protected Random randomGenerator;
   @Shadow
   protected WorldGenMinable redstoneGen;
   @Shadow
   protected WorldGenerator reedGen;
   @Shadow
   protected WorldGenerator sandGen;
   @Shadow
   protected int sandPerChunk;
   @Shadow
   protected int sandPerChunk2;
   @Shadow
   protected WorldGenMinable silverGen;
   @Shadow
   protected WorldGenMinable silverfishGen;
   @Shadow
   protected int treesPerChunk;
   @Shadow
   protected WorldGenerator waterlilyGen;
   @Shadow
   protected int waterlilyPerChunk;

   public BiomeDecoratorTrans(BiomeBase par1BiomeGenBase) {
      this.sandGen = new WorldGenSand(7, Block.sand.blockID);
      this.gravelAsSandGen = new WorldGenSand(6, Block.gravel.blockID);
      this.plantYellowGen = new WorldGenFlowers(Block.plantYellow.blockID);
      this.plantRedGen = new WorldGenFlowers(Block.plantRed.blockID);
      this.mushroomBrownGen = new WorldGenFlowers(Block.mushroomBrown.blockID);
      this.mushroomRedGen = new WorldGenFlowers(Block.mushroomRed.blockID);
      this.bigMushroomGen = new WorldGenHugeMushroom();
      this.reedGen = new WorldGenReed();
      this.cactusGen = new WorldGenCactus();
      this.waterlilyGen = new WorldGenWaterLily();
      this.flowersPerChunk = 2;
      this.grassPerChunk = 1;
      this.sandPerChunk = 1;
      this.sandPerChunk2 = 3;
      this.clayPerChunk = 1;
      this.generateLakes = true;
      this.biome = par1BiomeGenBase;
      this.dirtGen = new WorldGenMinable(Block.dirt.blockID, 32);
      this.gravelGen = new WorldGenMinable(Block.gravel.blockID, 32);
      this.coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
      this.copperGen = new WorldGenMinable(Block.oreCopper.blockID, 6);
      this.silverGen = new WorldGenMinable(Block.oreSilver.blockID, 6);
      this.goldGen = new WorldGenMinable(Block.oreGold.blockID, 4);
      this.ironGen = new WorldGenMinable(Block.oreIron.blockID, 6);
      this.mithrilGen = new WorldGenMinable(Block.oreMithril.blockID, 3);
      this.adamantiteGen = new WorldGenMinable(Block.oreAdamantium.blockID, 1);
      this.redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, 5);
      this.diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, 3);
      this.lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 2);
      this.silverfishGen = new WorldGenMinable(Block.silverfish.blockID, 2);
      this.bush_gen = new WorldGenPlants(Block.bush);
   }

   @Shadow
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable) {
   }

   @Shadow
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable, boolean vein_size_increases_with_depth) {
   }

   protected void generateOres() {
      if (this.currentWorld.isOverworld()) {
         this.genMinable(200, this.dirtGen);
         this.genMinable(200, this.gravelGen);
         this.genMinable(50, this.coalGen);
         this.genMinable(COPPER_FREQUENCY_OVERWORLD, this.copperGen, true);
         this.genMinable(SILVER_FREQUENCY_OVERWORLD, this.silverGen, true);
         this.genMinable(GOLD_FREQUENCY_OVERWORLD, this.goldGen, true);
         this.genMinable(IRON_FREQUENCY_OVERWORLD, this.ironGen, true);
         this.genMinable(MITHRIL_FREQUENCY_OVERWORLD, this.mithrilGen, true);
         this.genMinable(5, this.silverfishGen, true);
         this.genMinable(10, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_OVERWORLD, this.diamondGen);
         this.genMinable(LAPIS_FREQUENCY_OVERWORLD, this.lapisGen);
         if (Configs.WorldGen.Overworld.OVERWORLD_ADAMANTITE_ORE.get()) {
            this.genMinable(ADAMANTIUM_FREQUENCY_OVERWORLD, this.adamantiteGen, false);
         }
      } else if (this.currentWorld.isUnderworld()) {
         this.genMinable(300, this.gravelGen);
         this.genMinable(COPPER_FREQUENCY_UNDERWORLD, this.copperGen, true);
         this.genMinable(SILVER_FREQUENCY_UNDERWORLD, this.silverGen, true);
         this.genMinable(GOLD_FREQUENCY_UNDERWORLD, this.goldGen, true);
         this.genMinable(IRON_FREQUENCY_UNDERWORLD, this.ironGen, true);
         this.genMinable(MITHRIL_FREQUENCY_UNDERWORLD, this.mithrilGen, true);
         this.genMinable(ADAMANTIUM_FREQUENCY_UNDERWORLD / 2, this.adamantiteGen, false);
         this.genMinable(10, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_UNDERWORLD, this.diamondGen);
         this.genMinable(LAPIS_FREQUENCY_UNDERWORLD, this.lapisGen);
         if (this.currentWorld.underworld_y_offset != 0) {
            this.genMinable(50, this.silverfishGen);
         }
      } else if (!this.currentWorld.isTheEnd()) {
         Minecraft.setErrorMessage("generateOres: don't know how to handle world " + this.currentWorld);
      }

   }

}
