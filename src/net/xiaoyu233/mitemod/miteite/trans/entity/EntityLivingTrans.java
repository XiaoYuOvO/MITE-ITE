package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(EntityLiving.class)
public class EntityLivingTrans extends Entity{
    @Link
    public boolean au;
    @Link
    public int ay;
    @Link
    public int az;
    @Link
    public int aC;
    @Link
    public float aD;
    @Link
    public float aE;
    @Link
    public float aJ;
    @Link
    public float aK;
    @Link
    public float aN;
    @Link
    public float aO;
    @Link
    public float aP;
    @Link
    public float aQ;
    @Link
    protected EntityHuman aS;
    @Link
    protected int aT;
    @Link
    protected float bc;
    @Link
    protected boolean bd;
    @Link
    public float be;
    @Link
    public double bj;
    @Link
    private EntityLiving i;
    @Link
    private EntityLiving lastAttackTarget;
    @Link
    private int knockback_resistant_ticks;

    @Marker
    public EntityLivingTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    protected void a() {

    }

    @Marker
    protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
    }

    public void tryDamageArmorP(DamageSource damage_source, float amount, EntityDamageResult result){
        this.tryDamageArmor(damage_source, amount, result);
    }

    public void y() {
        this.aD = this.aE;
        super.y();
        this.q.C.a("livingEntityBaseTick");
        if (this.onServer() && this.T()) {
            if (dyCast(this) instanceof EntitySilverfish) {
                if (this.isSilverfishInsideDamagingOpaqueBlock()) {
                    this.attackEntityFrom(new Damage(DamageSource.d, 1.0F));
                }
            } else if (this.U()) {
                if (dyCast(this) instanceof EntityHuman){
                    this.attackEntityFrom(new Damage(DamageSource.d, MITEITEMod.CONFIG.get(Config.ConfigEntry.IN_WALL_DAMAGE_FOR_PLAYER).floatValue()));
                }else {
                    this.attackEntityFrom(new Damage(DamageSource.d, 1.0F));
                }
            }
        }

        if (!this.canCatchFire() || this.q.I) {
            this.B();
        }

        boolean var1 = dyCast(this) instanceof EntityHuman && dyCast(EntityHuman.class,this).bG.a;
        if (!this.breathesAir()) {
            var1 = true;
        }

        if (this.T() && this.a(Material.h)) {
            if (dyCast(this) instanceof EntityChicken && this.g_() && this.q.a(MathHelper.c(this.u), MathHelper.c(this.v + (double)this.P), MathHelper.c(this.w)) == 0) {
                this.g(300);
                var1 = true;
            }

            if (!this.aA() && !this.i(MobEffectList.o.H) && !var1) {
                this.g(this.h(this.al()));
                if (this.al() <= -20) {
                    this.g(0);

                    for(int var2 = 0; var2 < 8; ++var2) {
                        float var3 = this.ab.nextFloat() - this.ab.nextFloat();
                        float var4 = this.ab.nextFloat() - this.ab.nextFloat();
                        float var5 = this.ab.nextFloat() - this.ab.nextFloat();
                        this.q.spawnParticle(EnumParticle.bubble, this.u + (double)var3, this.v + (double)var4, this.w + (double)var5, this.x, this.y, this.z);
                    }

                    if (this.onServer()) {
                        this.attackEntityFrom(new Damage(DamageSource.e, 2.0F));
                    }
                }
            }

            this.B();
            if (!this.q.I && this.ag() && this.o instanceof EntityLiving) {
                this.a((Entity) null);
            }
        } else if (this.isEntityPlayer()) {
            this.g(MathHelper.a(this.al() + 10, 0, 300));
        } else {
            this.g(300);
        }

        this.aJ = this.aK;
        if (this.aC > 0) {
            --this.aC;
        }

        if (this.ay > 0) {
            --this.ay;
        }

        if (this.af > 0) {
            --this.af;
        }

        if (this.knockback_resistant_ticks > 0) {
            --this.knockback_resistant_ticks;
        }

        if (this.aN() <= 0.0F) {
            this.aB();
        }

        if (this.aT > 0) {
            --this.aT;
        } else {
            this.aS = null;
        }

        if (this.lastAttackTarget != null && !this.lastAttackTarget.T()) {
            this.lastAttackTarget = null;
        }

        if (this.i != null && !this.i.T()) {
            this.b((EntityLiving)null);
        }

        this.aJ();
        this.aO = this.aN;
        this.aQ = this.aP;
        this.C = this.A;
        this.D = this.B;
        this.q.C.b();
    }

    @Override
    @Marker
    protected void a(NBTTagCompound var1) {

    }

    @Override
    @Marker
    protected void b(NBTTagCompound var1) {

    }

    @Marker
    private boolean g_() {
        return false;
    }

    @Marker
    private boolean breathesAir() {
        return false;
    }

    @Marker
    protected int h(int par1) {return 0;}

    @Marker
    private void b(EntityLiving entityLiving) {

    }

    @Marker
    protected void aB() {}
    @Marker
    public final float aN() {return 0;}
    @Marker
    protected void aJ() {}
    @Marker
    public boolean aA() {
        return false;
    }
    @Marker
    public boolean i(int par1) {
        return false;
    }
}
