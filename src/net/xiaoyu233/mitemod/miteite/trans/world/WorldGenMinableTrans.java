package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.World;
import net.minecraft.WorldGenMinable;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(WorldGenMinable.class)
public class WorldGenMinableTrans {
    @Link
    private int a;
    public int getMaxVeinHeight(World world) {
        if (world.isUnderworld()) {
            return 255;
        } else {
            Block block = Block.s[this.a];
            if (block == Block.A) {
                return 128;
            } else if (block == Block.K) {
                return 128;
            } else if (block == Block.N) {
                return 96;
            } else if (block == Block.oreCopper) {
                return 128;
            } else if (block == Block.oreSilver) {
                return 96;
            } else if (block == Block.L) {
                return 48;
            } else if (block == Block.M) {
                return 64;
            } else if (block == Block.oreMithril) {
                return 32;
            } else if (block == Block.oreAdamantium) {
                return 16;
            } else {
                if (block != Block.bq) {
                    if (block == Block.aS) {
                        return 24;
                    } else if (block == Block.aB) {
                        return 32;
                    } else if (block == Block.S) {
                        return 40;
                    } else {
                        Minecraft.setErrorMessage("WorldGenMinable: unknown ore id " + block.cF);
                        return -1;
                    }
                } else {
                    return 24;
                }
            }
        }
    }
}
