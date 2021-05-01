package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.World;
import net.minecraft.WorldGenMinable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldGenMinable.class)
public class WorldGenMinableTrans {
   @Shadow
   private int minableBlockId;

   @Overwrite
   public int getMaxVeinHeight(World world) {
      if (world.isUnderworld()) {
         return 255;
      } else {
         Block block = Block.blocksList[this.minableBlockId];
         if (block == Block.dirt) {
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
         } else if (block != Block.silverfish) {
            if (block == Block.oreRedstone) {
               return 24;
            } else if (block == Block.oreDiamond) {
               return 32;
            } else if (block == Block.oreLapis) {
               return 40;
            } else {
               Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.blockID);
               return -1;
            }
         } else {
            return 24;
         }
      }
   }
}
