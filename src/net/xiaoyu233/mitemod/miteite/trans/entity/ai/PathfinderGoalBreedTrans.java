package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.Random;

@Transform(PathfinderGoalBreed.class)
public class PathfinderGoalBreedTrans {
    @Link
    private EntityAnimal d;
    @Link
    World a;
    @Link
    private EntityAnimal e;
    @Link
    int b;
    @Link
    double c;

    private void g() {
        EntityAgeable var1 = this.d.a((EntityAgeable)this.e);
        if (var1 != null) {
            this.d.setGrowingAgeAfterBreeding();
            this.e.setGrowingAgeAfterBreeding();
            this.d.bZ();
            this.e.bZ();
            var1.setGrowingAgeToNewborn();
            var1.b(this.d.u, this.d.v, this.d.w, 0.0F, 0.0F);
            if (!this.a.I && var1 instanceof EntityLivestock) {
                ((EntityLivestock)var1).adoptWellnessFromParents(this.d, this.e);
            }

            this.a.d(var1);
            Random var2 = this.d.aD();

            for(int var3 = 0; var3 < 7; ++var3) {
                double var4 = var2.nextGaussian() * 0.02D;
                double var6 = var2.nextGaussian() * 0.02D;
                double var8 = var2.nextGaussian() * 0.02D;
                this.a.spawnParticle(EnumParticle.heart, this.d.u + (double)(var2.nextFloat() * this.d.O * 2.0F) - (double)this.d.O, this.d.v + 0.5D + (double)(var2.nextFloat() * this.d.P), this.d.w + (double)(var2.nextFloat() * this.d.O * 2.0F) - (double)this.d.O, var4, var6, var8);
            }

            this.a.d(new EntityExperienceOrb(this.a, this.d.u, this.d.v, this.d.w, this.d.getBreedExp()));
        }

    }
}
