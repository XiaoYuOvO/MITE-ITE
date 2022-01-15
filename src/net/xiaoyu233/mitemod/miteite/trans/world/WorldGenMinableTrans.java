package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenMinable.class)
public class WorldGenMinableTrans {
   @Shadow
   private int minableBlockId;

//   @Overwrite
   @Shadow
   public int getRandomVeinHeight(World world, Random rand) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (world.underworld_y_offset == 0) {
            return rand.nextInt(256);
         }

         if (block == Block.oreAdamantium) {
            if (rand.nextFloat() < 0.75F) {
               return 0;
            }else {
               return Math.max(world.underworld_y_offset,250);
            }
         }

         if (block instanceof BlockOre && rand.nextFloat() < 0.75F) {
            return Math.max(world.underworld_y_offset,250);
         }

         return rand.nextInt(256);
      } else {
         float relative_height;
         if (block == Block.dirt) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height > rand.nextFloat()));
         } else if (block == Block.gravel) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height > rand.nextFloat()));
         } else if (block == Block.oreCoal) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height > rand.nextFloat()));
         } else if (block == Block.oreCopper) {
            if (rand.nextInt(2) == 0) {
               relative_height = rand.nextFloat() * 0.6F + 0.4F;
            } else do {
               relative_height = rand.nextFloat();
            } while (!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreSilver) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreGold) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreIron) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreMithril) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreAdamantium || block == Block.silverfish) {
            do {
               relative_height = rand.nextFloat();
            } while(!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreRedstone) {
            do {
               relative_height = rand.nextFloat();
            } while (!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreDiamond) {
            do {
               relative_height = rand.nextFloat();
            } while (!(relative_height < rand.nextFloat()));
         } else if (block == Block.oreLapis) {
            relative_height = (rand.nextFloat() + rand.nextFloat()) / 2.0F;
         } else if (block == Blocks.netherAdamantiumOre){
            relative_height = rand.nextFloat();
         }else {
            Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + this.minableBlockId);
            return -1;
         }

         int min_height = this.getMinVeinHeight(world);
         int height_range = this.getMaxVeinHeight(world) - min_height + 1;
         return min_height + (int)(relative_height * (float)height_range);
      }
   }


   @Overwrite
   public int getMinVeinHeight(World world) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (block == Block.oreAdamantium){
            return 130;
         }
         return 140;
      } else if (block == Block.dirt) {
         return 32;
      } else if (block == Block.gravel) {
         return 24;
      } else if (block == Block.oreCoal) {
         return 16;
      } else if (block == Block.oreCopper) {
         return 0;
      } else if (block == Block.oreSilver) {
         return 0;
      } else if (block == Block.oreGold) {
         return 0;
      } else if (block == Block.oreIron) {
         return 0;
      } else if (block == Block.oreMithril) {
         return 0;
      } else if (block == Block.oreAdamantium || block == Block.silverfish) {
         return 0;
      } else if (block == Block.oreRedstone) {
         return 0;
      } else if (block == Block.oreDiamond) {
         return 0;
      } else if (block == Block.oreLapis) {
         return 8;
      } else if(world.isTheNether()){
         return 35;
      }else {
         Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.blockID);
         return -1;
      }
   }

   @Inject(method = "generate(Lnet/minecraft/World;Ljava/util/Random;IIIZ)Z",at = @At("HEAD"),cancellable = true)
   private void injectMinableGenerate(World world, Random rand, int x, int y, int z, boolean vein_size_increases_with_depth,CallbackInfoReturnable<Boolean> callback){
      if ((world.isUnderworld() || world.isTheNether()) && y < this.getMinVeinHeight(world)){
         callback.setReturnValue(false);
         callback.cancel();
      }
   }

   @Overwrite
   public int getMaxVeinHeight(World world) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (block == Block.oreAdamantium){
            return 230;
         }
         return 225;
      } else if (world.isTheNether()){
         return 115;
      }else if (block == Block.dirt) {
         return 128;
      } else if (block == Block.gravel) {
         return 128;
      } else if (block == Block.oreCoal) {
         return 96;
      } else if (block == Block.oreCopper) {
         return 128;
      } else if (block == Block.oreSilver) {
         return 96;
      } else if (block == Block.oreGold) {
         return 48;
      } else if (block == Block.oreIron) {
         return 64;
      } else if (block == Block.oreMithril) {
         return 32;
      } else if (block == Block.oreAdamantium) {
         return 16;
      } else if (block == Block.silverfish) {
         return 24;
      } else if (block == Block.oreRedstone) {
         return 24;
      } else if (block == Block.oreDiamond) {
         return 32;
      } else if (block == Block.oreLapis) {
         return 40;
      } else {
         Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.blockID);
         return -1;
      }
   }
}
