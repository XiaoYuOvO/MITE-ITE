package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ChunkProviderHell.class)
public class ChunkProviderHellTrans {
   @Shadow
   private Random hellRNG;
   @Shadow
   private World worldObj;

   @Inject(method = "populate", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldInfo;getEarliestMITEReleaseRunIn()I", ordinal = 1, shift = At.Shift.BEFORE))
   private void injectGenAdamantium(IChunkProvider par1IChunkProvider, int par2, int par3, CallbackInfo ci){
       int var8;
      int var9;
      int var10;
      int var11;
      int var4 = par2 * 16;
      int var5 = par3 * 16;
      WorldGenMinable var12 = (new WorldGenMinable(Blocks.netherAdamantiumOre.blockID, Configs.WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_VEIN.get(), Block.netherrack.blockID)).setMinableBlockMetadata(0);

      int count = this.hellRNG.nextInt(Configs.WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_CHUNK.get()) + 1;
      for(var8 = 0; var8 < count; ++var8) {
         var9 = var4 + this.hellRNG.nextInt(16);
         var10 = this.hellRNG.nextInt(80) + 35;
         var11 = var5 + this.hellRNG.nextInt(16);
         var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
      }
   }

   @ModifyConstant(method = "populate", constant = @Constant(intValue = 108), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/WorldInfo;getEarliestMITEReleaseRunIn()I", ordinal = 1)))
   private int modifyNetherOreHeightRandom(int constant){
       return 80;
   }

   @ModifyConstant(method = "populate", constant = @Constant(intValue = 10), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/Block;deadBush:Lnet/minecraft/BlockDeadBush;"), to = @At(value = "INVOKE", target = "Lnet/minecraft/WorldInfo;getEarliestMITEReleaseRunIn()I", ordinal = 1)))
   private int modifyNetherOreHeight(int constant){
      return 35;
   }
}
