package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.Calendar;

@Transform(EntitySkeleton.class)
public class EntitySkeletonTrans extends EntityMonster implements IRangedEntity{
    private static final int DATA_OBJ_ID_SKELETON_TYPE = 13;
    private static final int WITHER_SKELETON_ID = 1;
    @Link
    private final PathfinderGoalArrowAttack bp = new PathfinderGoalArrowAttack((this), 1.0D, 20, 60, 15.0F);
    @Link
    private final PathfinderGoalMeleeAttack bq = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);
    @Link
    public int forced_skeleton_type = -1;
    @Link
    private int data_object_id_is_frenzied_by_bone_lord;
    private int DATA_OBJ_ID_CAN_USE_FIRE_ARROW;
    private int DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW;
    private int DATA_OBJ_ID_COMPRESSED;

    public EntitySkeletonTrans(World par1World) {
        super(par1World);
        this.c.a(1, new PathfinderGoalFloat(this));
        this.c.a(2, new PathfinderGoalRestrictSun(this));
        this.c.a(3, new PathfinderGoalFleeSun(this, 1.0D));
        this.c.a(5, new PathfinderGoalRandomStroll(this, 0.75D));
        this.c.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.c.a(6, new PathfinderGoalRandomLookaround(this));
        this.d.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.c.a(4, new EntityAIMoveToRepairItem(this, 1.0F, true));
        if (par1World != null && !par1World.I) {
            this.bT();
        }

    }

    //On Death
    @Override
    public void a(DamageSource par1DamageSource) {
        if (!this.q.I){
            if (this.isCompressed()){
                for (int integer = 0; integer < (MITEITEMod.CONFIG.get(Config.ConfigEntry.COMPRESSED_SKELETON_EXPAND_COUNT)); integer++) {
                    EntitySkeleton skeleton = new EntitySkeleton(this.q);
                    skeleton.a((double) this.getBlockPosX(),this.getFootBlockPosY(),this.getBlockPosZ());
                    skeleton.forced_skeleton_type = skeleton.getRandomSkeletonType(this.q);
                    skeleton.refreshDespawnCounter(-9600);
                    this.q.d(skeleton);
                    skeleton.a((GroupDataEntity)null);
                    skeleton.d(this.getTarget());
                    skeleton.entityFX(EnumEntityFX.summoned);
                }
            }
        }
        super.a(par1DamageSource);
    }

    private boolean isCompressed(){
        return this.ah.a(this.DATA_OBJ_ID_COMPRESSED) != 0;
    }

    public void bT() {
        this.c.a(this.bq);
        this.c.a(this.bp);
        ItemStack var1 = this.getHeldItemStack();
        if (var1 != null && var1.b() instanceof ItemBow) {
            this.c.a(4, this.bp);

            this.c.a(1, new EntityAISeekFiringPosition(this, 1.0F, true));
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
        this.ah.addObject(DATA_OBJ_ID_SKELETON_TYPE, (byte) 0);
        this.data_object_id_is_frenzied_by_bone_lord = this.ah.addObject(this.ah.getNextAvailableId(), (byte) 0);
        this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW = this.ah.addObject(this.ah.getNextAvailableId(), (byte)0);
        this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW = this.ah.addObject(this.ah.getNextAvailableId(), (byte)0);
        this.DATA_OBJ_ID_COMPRESSED = this.ah.addObject(this.ah.getNextAvailableId(), (byte)0);
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        if (this.getHeldItemStack() != null){
            this.bT();
        }
        return super.attackEntityAsMob(target);
    }

    public GroupDataEntity a(GroupDataEntity par1EntityLivingData) {
        par1EntityLivingData = super.a(par1EntityLivingData);
        int skeleton_type = this.forced_skeleton_type >= 0 ? this.forced_skeleton_type : this.getRandomSkeletonType(super.q);
        if (MITEITEMod.CONFIG.get(Config.ConfigEntry.COMPRESSED_SKELETON)){
            this.ah.b(DATA_OBJ_ID_COMPRESSED,(byte) (this.ab.nextInt(50) == 0 ? 1 : 0));
        }
        if (skeleton_type == 1) {
            this.c.a(4, this.bq);
            this.a(1);
            this.c(0, (new ItemStack(Item.swordAncientMetal)).setQuality(EnumQuality.poor).randomizeForMob(this, super.q.getDayOfWorld() > 160));
            this.a(GenericAttributes.e).a(4.0D);
            this.setEntityAttribute(GenericAttributes.a,this.getEntityAttributeValue(GenericAttributes.a) * 2d);
            this.g(24f);
        } else {
            if (skeleton_type == 2) {
                this.a(2);
                this.c.a(4, this.bq);
            } else if (skeleton_type == 0) {
                this.c.a(4, this.bp);
                this.ah.b(DATA_OBJ_ID_CAN_USE_FIRE_ARROW,(byte)((this.ab.nextInt(10)>5) ? 1 : 0));
                this.ah.b(DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW,(byte)((this.ab.nextInt(10)>6) ? 1 : 0));
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

    public void a(EntityLiving target, float par2) {
        EntityArrow center = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().r * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);

        this.processArrow(center,par2);
        if (MITEITEMod.CONFIG.get(Config.ConfigEntry.SKELETON_TRIPLE_SHOT) && this.ah.a(DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) > 1){
            EntityArrow left = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().r * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
            left.x *= 1.5;
            EntityArrow right = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().r * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
            right.x /= 1.5;
            this.getWorld().d(left);
            this.getWorld().d(right);
        }
        this.a("random.bow", 1.0F, 1.0F / (this.aD().nextFloat() * 0.4F + 0.8F));
        this.getWorld().d(center);
    }

    private void processArrow(EntityArrow var3,float par2){
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
    }


    @Marker
    //Get skeleton_type
    public int bV() {
        return this.ah.a(13);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.setEntityAttribute(GenericAttributes.b, 64D);
        if (this.bV() == WITHER_SKELETON_ID){
            this.setEntityAttribute(GenericAttributes.a, 50.0D + day / 12D);
            this.setEntityAttribute(GenericAttributes.d, 0.25F);
            this.setEntityAttribute(GenericAttributes.e, 10D + day / 48D);
        }else {
            this.setEntityAttribute(GenericAttributes.a, 15d + day / 14D);
            this.setEntityAttribute(GenericAttributes.d, 0.3F);
            this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e) * 3.6d);
        }
    }

    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        if (par1NBTTagCompound.b("SkeletonType")) {
            byte var2 = par1NBTTagCompound.c("SkeletonType");
            this.a(var2);
        }
        if (par1NBTTagCompound.b("TripleShot")){
            if (par1NBTTagCompound.n("TripleShot")){
                this.ah.b(DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW,(byte)1);
            }
        }
        if (par1NBTTagCompound.b("FireBow")){
            if (par1NBTTagCompound.n("FireBow")){
                this.ah.b(DATA_OBJ_ID_CAN_USE_FIRE_ARROW, (byte) 1);
            }
        }

        if (par1NBTTagCompound.b("Compressed")){
            if (par1NBTTagCompound.n("Compressed")){
                this.ah.b(DATA_OBJ_ID_COMPRESSED,(byte) 1);
            }
        }

        this.bT();
    }

    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.a("SkeletonType", (byte)this.bV());
        par1NBTTagCompound.a("TripleShot", this.ah.a(DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) != 0);
        par1NBTTagCompound.a("FireBow", this.ah.a(DATA_OBJ_ID_CAN_USE_FIRE_ARROW) != 0);
        par1NBTTagCompound.a("Compressed", this.isCompressed());
    }
}