package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;

public class EntityUtil{

    public static void generateRandomParticles(EntityMob entity, EnumParticle particle) {
        for(int var2 = 0; var2 < 5; ++var2) {
            double var3 = entity.getRNG().nextGaussian() * 0.02D;
            double var5 = entity.getRNG().nextGaussian() * 0.02D;
            double var7 = entity.getRNG().nextGaussian() * 0.02D;
            entity.worldObj.spawnParticle(particle, entity.posX + (double)(entity.getRNG().nextFloat() * entity.width * 2.0F) - (double)entity.width, entity.posY + 1.0D + (double)(entity.getRNG().nextFloat() * entity.height), entity.posZ + (double)(entity.getRNG().nextFloat() * entity.width * 2.0F) - (double)entity.width, var3, var5, var7);
        }

    }
}
