package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BiomeDecorator.class)
public class BiomeDecoratorTrans {
   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 40), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;copperGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifyCopperOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.COPPER_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 10), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;silverGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifySilverOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.SILVER_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 20), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;goldGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifyGoldOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.GOLD_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 60), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;ironGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifyIronOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.IRON_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 10), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;ironGen:Lnet/minecraft/WorldGenMinable;"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;mithrilGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifyMithrilOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.MITHRIL_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 5), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;redstoneGen:Lnet/minecraft/WorldGenMinable;"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;diamondGen:Lnet/minecraft/WorldGenMinable;", ordinal = 0)))
   private int modifyDiamondOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.DIAMOND_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 5), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;diamondGen:Lnet/minecraft/WorldGenMinable;"), to = @At(value = "INVOKE", target = "Lnet/minecraft/World;isUnderworld()Z")))
   private int modifyLapisOverworldFreq(int constant){
      return Configs.WorldGen.Overworld.LAPIS_FREQUENCY_OVERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 40), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/World;isUnderworld()Z"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;copperGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyCopperUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.COPPER_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 10), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/World;isUnderworld()Z"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;silverGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifySilverUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.SILVER_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 20), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/World;isUnderworld()Z"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;goldGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyGoldUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.GOLD_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 60), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/World;isUnderworld()Z"), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;ironGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyIronUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.IRON_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 10), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;ironGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;mithrilGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyMithrilUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.MITHRIL_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 5), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;mithrilGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;adamantiteGen:Lnet/minecraft/WorldGenMinable;")))
   private int modifyAdamantiteUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.MITHRIL_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 5), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;redstoneGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1), to = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;diamondGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyDiamondUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.DIAMOND_FREQUENCY_UNDERWORLD.get();
   }

   @ModifyConstant(method = "generateOres", constant = @Constant(intValue = 5), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/BiomeDecorator;diamondGen:Lnet/minecraft/WorldGenMinable;", ordinal = 1)))
   private int modifyLapisUnderworldFreq(int constant){
      return Configs.WorldGen.Underworld.LAPIS_FREQUENCY_UNDERWORLD.get();
   }
}
