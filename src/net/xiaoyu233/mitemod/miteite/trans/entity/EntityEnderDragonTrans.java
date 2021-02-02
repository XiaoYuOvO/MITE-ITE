package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.List;

@Transform(EntityEnderDragon.class)
public class EntityEnderDragonTrans extends EntityInsentient implements IComplex{
    @Link
    private EntityEnderCrystal bC;
    @Link
    private Entity bD;
    @Link
    private EntityComplexPart bq;
    @Link
    private boolean bz;
    @Link
    private double h;
    @Link
    private double i;
    @Link
    private double j;

    @Marker
    public EntityEnderDragonTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        this.a(GenericAttributes.a).a(500.0D);
    }

    @Override
    @Marker
    public World b() {
        return null;
    }

    private void b(List par1List) {
        for (Object value : par1List) {
            Entity var3 = (Entity) value;
            if (var3 instanceof EntityLiving) {
                var3.attackEntityFrom(new Damage(DamageSource.a(this), 20.0F));
            }
        }

    }


    @Override
    public EntityDamageResult attackEntityFromPart(EntityComplexPart par1EntityDragonPart, Damage damage) {

        if (par1EntityDragonPart != this.bq && damage.getAmount() > 1.0F) {
            damage.scaleAmount(0.2F, 0.5F);
        }

        float var4 = super.A * 3.1415927F / 180.0F;
        float var5 = MathHelper.a(var4);
        float var6 = MathHelper.b(var4);
        this.h = super.u + (double)(var5 * 5.0F) + (double)((this.aD().nextFloat() - 0.5F) * 2.0F);
        this.i = super.v + (double)(this.aD().nextFloat() * 3.0F) + 1.0D;
        this.j = super.w - (double)(var6 * 5.0F) + (double)((this.aD().nextFloat() - 0.5F) * 2.0F);
        this.bD = null;
        return !(damage.getSource().getResponsibleEntity() instanceof EntityHuman) && !damage.isExplosion() ? null : this.func_82195_e(damage);
    }

    private void bK() {

            this.bz = false;
            if (this.aD().nextInt(2) == 0 && !super.q.h.isEmpty() && this.aN()>150) {
                this.bD = (Entity) super.q.h.get(this.aD().nextInt(super.q.h.size()));
            } else {
                boolean var1;

                do {
                    this.h = 0.0D;
                    this.i = 70.0F + this.aD().nextFloat() * 50.0F;
                    this.j = 0.0D;
                    this.h += this.aD().nextFloat() * 120.0F - 60.0F;
                    this.j += this.aD().nextFloat() * 120.0F - 60.0F;
                    double var2 = super.u - this.h;
                    double var4 = super.v - this.i;
                    double var6 = super.w - this.j;
                    var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
                } while (!var1);
                this.bD = null;
            }


    }

    @Marker
    private EntityDamageResult func_82195_e(Damage damage) {
        return null;
    }

    private void bJ() {
        if (this.bC != null) {
            if (this.bC.M) {
                if (!super.q.I) {
                    this.attackEntityFromPart(this.bq, new Damage(DamageSource.a((Explosion) null), 5.0F));
                }

                this.bC = null;
            } else if (super.ac % 10 == 0 && this.aN() < this.aT()) {
                this.g(super.aN() + 2.0F);
            }
        }
        if (this.aN() < 150 &&super.ac % 30 == 0 && this.aN() < this.aT()) {
            this.g(super.aN() + 2.0F);
        }
        if (this.aD().nextInt(10) == 0) {
            float var1 = 48.0F;
            List var2 = super.q.a(EntityEnderCrystal.class, super.E.b(var1, var1, var1));
            EntityEnderCrystal var3 = null;
            double var4 = Double.MAX_VALUE;

            for (Object value : var2) {
                EntityEnderCrystal var7 = (EntityEnderCrystal) value;
                double var8 = var7.e(this);
                if (var8 < var4) {
                    var4 = var8;
                    var3 = var7;
                }
            }

            this.bC = var3;
        }

    }
}

