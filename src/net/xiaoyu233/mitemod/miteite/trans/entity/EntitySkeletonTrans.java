package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.util.Calendar;

@Transform(EntitySkeleton.class)
public class EntitySkeletonTrans extends EntityMonster {
    private final PathfinderGoalArrowAttack bp = new PathfinderGoalArrowAttack(ReflectHelper.dyCast(this), 1.0D, 20, 60, 15.0F);
    private final PathfinderGoalMeleeAttack bq = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);
    private int DATA_OBJ_ID_CAN_USE_FIRE_ARROW;
    public int forced_skeleton_type = -1;
    private int data_object_id_is_frenzied_by_bone_lord;

    public EntitySkeletonTrans(World par1World) {
        super(par1World);
        super.c.a(1, new PathfinderGoalFloat(this));
        super.c.a(2, new PathfinderGoalRestrictSun(this));
        super.c.a(3, new PathfinderGoalFleeSun(this, 1.0D));
        super.c.a(5, new PathfinderGoalRandomStroll(this, 0.75D));
        super.c.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        super.c.a(6, new PathfinderGoalRandomLookaround(this));
        super.d.a(1, new PathfinderGoalHurtByTarget(this, false));
        super.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        super.c.a(4, new EntityAIMoveToRepairItem(this, 1.0F, true));
        if (par1World != null && !par1World.I) {
            this.bT();
        }

    }

    public void bT() {
        this.c.a(this.bq);
        this.c.a(this.bp);
        ItemStack var1 = this.getHeldItemStack();
        if (var1 != null && var1.b() instanceof ItemBow) {
            this.c.a(4, this.bp);
            this.c.a(3, new EntityAISeekFiringPosition(this, 1.0F, true));
        } else {
            this.c.a(4, this.bq);
        }
    }

    @Marker
    public void a(int par1) {
        this.ah.b(13, (byte)par1);
        if (par1 == 1) {
            this.a(0.72F, 2.34F);
        } else {
            this.a(0.6F, 1.8F);
        }

    }

    protected void a() {
        super.a();
        this.ah.addObject(13, (byte) 0);
        this.data_object_id_is_frenzied_by_bone_lord = this.ah.addObject(this.ah.getNextAvailableId(), (byte) 0);
        this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW = this.ah.addObject(this.ah.getNextAvailableId(), (byte)0);
    }

    public GroupDataEntity a(GroupDataEntity par1EntityLivingData) {
        par1EntityLivingData = super.a(par1EntityLivingData);
        int skeleton_type = this.forced_skeleton_type >= 0 ? this.forced_skeleton_type : this.getRandomSkeletonType(super.q);
        if (skeleton_type == 1) {
            super.c.a(4, this.bq);
            this.a(1);
            super.c(0, (new ItemStack(Item.swordAncientMetal)).setQuality(EnumQuality.poor).randomizeForMob(this, super.q.getDayOfWorld() > 160));
            this.a(GenericAttributes.e).a(4.0D);
            this.setEntityAttribute(GenericAttributes.a,this.getEntityAttributeValue(GenericAttributes.a) * 2d);
            super.g(24f);
        } else {
            if (skeleton_type == 2) {
                this.a(2);
                super.c.a(4, this.bq);
            } else if (skeleton_type == 0) {
                super.c.a(4, this.bp);
                this.ah.b(DATA_OBJ_ID_CAN_USE_FIRE_ARROW,(byte)((this.ab.nextInt(10)>5) ? 1 : 0));
            } else {
                Minecraft.setErrorMessage("onSpawnWithEgg: Unrecognized skeleton type " + skeleton_type);
            }

            this.addRandomEquipment();
        }

        this.h(true);
        if (this.n(4) == null) {
            Calendar var2 = super.q.W();
            if (var2.get(Calendar.MONTH) + 1 == 10 && var2.get(Calendar.DATE) == 31 && this.aD().nextFloat() < 0.25F) {
                super.c(4, new ItemStack(this.aD().nextFloat() < 0.1F ? Block.bk : Block.bf));
                super.e[4] = 0.0F;
            }
        }

        return par1EntityLivingData;
    }

    public int getRandomSkeletonType(World world) {
        if (world.isTheNether()) {
            return 1;
        } else {
            return (double)this.aD().nextFloat() < (this.isLongdead() ? 0.5D : 0.25D) ? 2 : 0;
        }
    }

    @Marker
    private void addRandomEquipment() {

    }

    public void addRandomWeapon() {
        int day_of_world = MinecraftServer.F().getOverworld().getDayOfWorld();
        if (this.bV() == 2 && day_of_world >= 64){
            if (this.aD().nextInt(Math.max(2,20-day_of_world/48)) == 1){
                super.c(0, (new ItemStack(Item.s)).randomizeForMob(this, day_of_world >= 96));
            }else if (!this.aD().nextBoolean()){
                super.c(0, (new ItemStack(Item.daggerIron)).randomizeForMob(this, day_of_world >= 128));
            }else{
                super.c(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, day_of_world >=96));
            }
            return;
        }
        if (this.bV() == 2 && this.aD().nextInt(20) == 0) {
            if (day_of_world >= 10) {
                if (!this.aD().nextBoolean()) {
                    if (day_of_world >= 20) {
                        super.c(0, (new ItemStack(Item.swordRustedIron)).randomizeForMob(this, false));
                    } else {
                        super.c(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, false));
                    }
                } else {
                    super.c(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, false));
                }
                return;
            }
        }


        super.c(0, (new ItemStack(this.bV() == 2 ? Item.clubWood : Item.m)).randomizeForMob(this, true));
    }


    @Marker
    public boolean isLongdead(){
        return false;
    }

    public void a(EntityLiving par1EntityLivingBase, float par2) {
        EntityArrow var3 = new EntityArrow(this.getWorld(), this, par1EntityLivingBase, 1.6F, (float)(14 - this.getWorld().r * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
        int rawDay = this.getWorld() != null ? this.getWorld().getDayOfWorld() : 0;
        int day = Math.max(rawDay - 64,0);
        int var4 = EnchantmentManager.a(Enchantment.v.z, this.getHeldItemStack()) + 1;
        int var5 = (int) (EnchantmentManager.a(Enchantment.w.z, this.getHeldItemStack()) + Math.min((1 + Math.floor(day / 48F)),5));
        double damage = (double)(par2 * 2.0F) + this.aD().nextGaussian() * 0.0D + (double)((float)this.getWorld().r * 0.11F);
        var3.b(damage);
        if (var4 > 0) {
            var3.b(var3.c() + (double)var4 * 2.0D + 1);
        }

        if (var5 > 0) {
            var3.a(var5);
        }

        if ((EnchantmentManager.a(Enchantment.x.z, this.getHeldItemStack()) > 0 || this.bV() == 1 || (this.af() && this.aD().nextInt(3) == 0)) || (this.ah.a(DATA_OBJ_ID_CAN_USE_FIRE_ARROW) > 0 && rawDay>196)) {
            var3.d(100);
        }

        this.a("random.bow", 1.0F, 1.0F / (this.aD().nextFloat() * 0.4F + 0.8F));
        this.getWorld().d(var3);
    }


    @Marker
    public int bV() {
        return this.ah.a(13);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.setEntityAttribute(GenericAttributes.a,(this.bV() == 1 ? 28d : 15d) + day / 14D);
        this.setEntityAttribute(GenericAttributes.d, 0.3F);
        this.setEntityAttribute(GenericAttributes.b, 64D);
        this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e) * 3.6d);
    }
}