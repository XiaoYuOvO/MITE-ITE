package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntitySkeleton.class)
public class EntitySkeletonLiar extends EntityMonster {
    @Stealing
    public EntitySkeletonLiar(World par1World) {
        super(par1World);
        this.c.a(1, new PathfinderGoalFloat(this));
        this.c.a(2, new PathfinderGoalRestrictSun(this));
        this.c.a(3, new PathfinderGoalFleeSun(this, 1.0D));
        this.c.a(5, new PathfinderGoalRandomStroll(this, 0.0D));
        this.c.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.c.a(6, new PathfinderGoalRandomLookaround(this));
        this.d.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.c.a(4, new EntityAIMoveToRepairItem(this, 1.0F, true));
    }


    @Stealing
    public boolean isLongdead(){
        return false;
    }

    public void a(EntityLiving par1EntityLivingBase, float par2) {
        EntityArrow var3 = new EntityArrow(this.getWorld(), this, par1EntityLivingBase, 1.6F, (float)(14 - this.getWorld().r * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        int var4 = EnchantmentManager.a(Enchantment.v.z, this.getHeldItemStack()) + 1;
        int var5 = (int) (EnchantmentManager.a(Enchantment.w.z, this.getHeldItemStack()) + Math.max((1 + Math.floor(day / 48F)),5));
        double damage = (double)(par2 * 2.0F) + this.aD().nextGaussian() * 0.0D + (double)((float)this.getWorld().r * 0.11F);
        var3.b(damage);
        if (var4 > 0) {
            var3.b(var3.c() + (double)var4 * 2.0D + 1);
        }

        if (var5 > 0) {
            var3.a(var5);
        }

        if (EnchantmentManager.a(Enchantment.x.z, this.getHeldItemStack()) > 0 || this.bV() == 1 || this.af() && this.aD().nextInt(3) == 0) {
            var3.d(100);
        }

        this.a("random.bow", 1.0F, 1.0F / (this.aD().nextFloat() * 0.4F + 0.8F));
        this.getWorld().d(var3);
    }

    @Stealing
    public int bV() {
        return this.ah.a(13);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.setEntityAttribute(GenericAttributes.a,12d + day / 16D);
        this.setEntityAttribute(GenericAttributes.d, 0.3F);
        this.setEntityAttribute(GenericAttributes.b, 32D);
        this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e) * 3d);
    }
}