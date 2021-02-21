package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityGhast.class)
public class EntityGhastTrans extends EntityFlying implements IMonster {
    @Link
    public int h;
    @Link
    public double i;
    @Link
    public double j;
    @Link
    public double bn;
    @Link
    private Entity bq;
    @Link
    private int br;
    @Link
    public int bo;
    @Link
    public int bp;
    @Link
    private int bs;
    
    @Marker
    public EntityGhastTrans(World world) {
        super(world);
    }

    @Override
    protected void az() {
        super.az();
        this.bs = 2;
        super.a(GenericAttributes.a).a(15.0D);
    }

    protected void bl() {
        if (!super.q.I && super.q.r == 0) {
            super.x();
        }

        super.tryDespawnEntity();
        this.bo = this.bp;
        double var1 = this.i - super.u;
        double var3 = this.j - super.v;
        double var5 = this.bn - super.w;
        double var7 = var1 * var1 + var3 * var3 + var5 * var5;
        if (var7 < 1.0D || var7 > 3600.0D) {
            this.i = super.u + (double)((super.ab.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.j = super.v + (double)((super.ab.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.bn = super.w + (double)((super.ab.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.h-- <= 0) {
            this.h += super.ab.nextInt(5) + 2;
            var7 = MathHelper.a(var7);
            if (this.a(this.i, this.j, this.bn, var7)) {
                super.x += var1 / var7 * 0.1D;
                super.y += var3 / var7 * 0.1D;
                super.z += var5 / var7 * 0.1D;
            } else {
                this.i = super.u;
                this.j = super.v;
                this.bn = super.w;
            }
        }

        if (this.bq != null && this.bq.M) {
            this.bq = null;
        }

        if (this.bq == null || this.br-- <= 0) {
            this.bq = super.getClosestVulnerablePlayer(100.0D);
            if (this.bq != null) {
                this.br = 20;
            }
        }

        double var9 = 64.0D;
        if (this.bq != null && this.bq.e(this) < var9 * var9) {
            Vec3D target_center = this.bq.getCenterPoint();
            super.aN = super.A = (float)MathHelper.getYawInDegrees(super.getCenterPoint(), target_center);
            if (super.canSeeEntity(this.bq)) {
                if (this.bp == 8) {
                    super.q.a(null, 1007, (int)super.u, (int)super.v, (int)super.w, 0);
                }

                ++this.bp;
                if (this.bp == 15) {
                    double distance_sq = super.getCenterPoint().e(target_center);
                    float lead = (float)Math.pow(distance_sq, 0.44D);
                    lead *= 0.5F + super.ab.nextFloat();
                    target_center.c = this.bq.getPredictedPosX(lead);
                    target_center.e = this.bq.getPredictedPosZ(lead);
                    super.q.a(null, 1008, (int)super.u, (int)super.v, (int)super.w, 0);
                    for(int i = 3; i > 0;i--){
                        EntityLargeFirebal var17 = new EntityLargeFireballNB(super.q, this, target_center, 4.0F + i * 2);
                        if (this.q.isOverworld()){
                            var17.e = Math.round((float)this.bs * 1.5f);
                        }else {
                            var17.e = this.bs;
                        }
                        super.q.d(var17);
                    }
                    this.bp = -35;
                }
            } else if (this.bp > 0) {
                --this.bp;
            }
        } else {
            super.aN = super.A = -((float)Math.atan2(super.x, super.z)) * 180.0F / 3.1415927F;
            if (this.bp > 0) {
                --this.bp;
            }
        }

        if (!super.q.I) {
            byte var21 = super.ah.a(16);
            byte var12 = (byte)(this.bp > 10 ? 1 : 0);
            if (var21 != var12) {
                super.ah.b(16, var12);
            }
        }

    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        if (this.getWorld().isTheNether()){
            return super.getCanSpawnHere(perform_light_check) ;
        }else if (this.getWorld().isOverworld()){
            return this.getWorld().isBloodMoon24HourPeriod() &&
                    this.getWorld().getDayOfWorld() >= MITEITEMod.CONFIG.get(Config.ConfigEntry.GHAST_SPAWN_LIMIT_DAY) &&
                    !this.getWorld().anySolidBlockIn(this.E.a(0.0,3.0,0.0));
        }
        return super.getCanSpawnHere(perform_light_check);
    }

    @Marker
    private boolean a(double par1, double par3, double par5, double par7){
        return false;
    }
    @Override
    @Marker
    public boolean canSpawnInShallowWater() {
        return false;
    }
    public static class EntityLargeFireballNB extends EntityLargeFirebal{

        @Marker
        public EntityLargeFireballNB(World world, EntityLiving shooter, Vec3D target, float initial_distance) {
            super(world, shooter, target, initial_distance);
        }

        protected void onImpact(RaycastCollision rc) {
            if (!this.q.I) {
                if (rc.isEntity()) {
                    rc.getEntityHit().attackEntityFrom(new Damage(DamageSource.a(this, this.a), 6.0F));
                }

                this.q.newExplosion(this, this.u, this.v, this.w, (float)this.e, (float)this.e, true, this.getWorld().isTheNether());
                this.x();
            }

        }
    }
}
