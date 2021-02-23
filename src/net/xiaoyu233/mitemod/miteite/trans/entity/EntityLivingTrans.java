package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
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
    public int aB;
    @Link
    private EntityLiving i;
    @Link
    private EntityLiving lastAttackTarget;
    @Link
    private int knockback_resistant_ticks;
    @Link
    public boolean has_taken_massive_fall_damage;
    @Link
    protected int bb;
    @Link
    public float aA;
    @Link
    public float aF;
    @Link
    public float aG;
    @Link
    public int aI;

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

    protected void aB() {
        ++this.aB;
        if (dyCast(this) instanceof EntityShadow || dyCast(this) instanceof EntityFireElemental || dyCast(this) instanceof EntityNightwing) {
            this.aB = 20;
        }

        if (this.aB == 20) {
            int var1;
            EntityExperienceOrb xp_orb;
            if (!this.q.I && (this.aT > 0 || this.isEntityPlayer()) && !this.g_() && this.q.O().b("doMobLoot")) {
                for(var1 = this.getExperienceValue(); var1 > 0; this.q.d(xp_orb)) {
                    int var2 = EntityExperienceOrb.a(var1);
                    var1 -= var2;
                    xp_orb = new EntityExperienceOrb(this.q, this.u, this.v + 0.5D, this.w, Math.max(var2,0));
                    if (this.isEntityPlayer()) {
                        EntityHuman player = dyCast(this);
                        xp_orb.setPlayerThisBelongsTo(player.an());
                        xp_orb.b = -18000;
                    }
                }
            }

            this.x();

            for(var1 = 0; var1 < 20; ++var1) {
                double var8 = this.ab.nextGaussian() * 0.02D;
                double var4 = this.ab.nextGaussian() * 0.02D;
                double var6 = this.ab.nextGaussian() * 0.02D;
                this.q.spawnParticle(EnumParticle.explode, this.u + (double)(this.ab.nextFloat() * this.O * 2.0F) - (double)this.O, this.v + (double)(this.ab.nextFloat() * this.P), this.w + (double)(this.ab.nextFloat() * this.O * 2.0F) - (double)this.O, var8, var4, var6);
            }
        }

    }

    public void a(DamageSource par1DamageSource) {
        Entity var2 = par1DamageSource.getResponsibleEntity();
        EntityLiving var3 = this.aS();
        if (this.bb >= 0 && var3 != null) {
            var3.b(this, this.bb);
        }

        if (var2 != null) {
            var2.a(dyCast(EntityLiving.class,this));
        }

        if (!this.q.I) {
            int var4 = 0;
            if (var2 instanceof EntityHuman) {
                var4 = EnchantmentManager.g((EntityLiving)var2);
                ItemStack heldItemStack = ((EntityHuman) var2).getHeldItemStack();
                if (heldItemStack != null){
                    float modifierValue = ToolModifierTypes.BEHEADING_MODIFIER.getModifierValue(heldItemStack.q());
                    if (modifierValue > 0){
                        boolean dropHead = this.ab.nextInt(100) < modifierValue * 100;
                        if (dropHead){
                            EntityLiving thisLiving = dyCast(this);
                            ItemStack headItemStack = null;
                            if (thisLiving instanceof EntityCreeper){
                                headItemStack = new ItemStack(Item.bS,1,4);
                            }
                            if (thisLiving instanceof EntityZombie){
                                headItemStack = new ItemStack(Item.bS,1,2);
                            }
                            if (thisLiving instanceof EntitySkeleton){
                                headItemStack = new ItemStack(Item.bS,1,0);
                            }
                            if (thisLiving instanceof EntityHuman){
                                headItemStack = new ItemStack(Item.bS,1,3);
                            }
                            if (headItemStack != null){
                                this.dropItemStack(headItemStack);
                            }
                        }
                    }
                }
            }

            if (!this.g_() && this.q.O().b("doMobLoot")) {
                if (!this.has_taken_massive_fall_damage || this.ab.nextFloat() < 0.1F) {
                    this.dropFewItems(this.aT > 0, par1DamageSource);
                }

                this.dropContainedItems();
                this.a(this.aT > 0, var4);
            }
        }

        this.q.setEntityState(this, EnumEntityState.dead);
    }

    @Marker
    public void a(boolean b, int var4) {

    }

    @Marker
    public void dropContainedItems() {

    }

    @Marker
    public void dropFewItems(boolean recentlyHitByPlayer, DamageSource damageSource) {

    }

    @Marker
    public EntityLiving aS() {
        return null;
    }

    @Marker
    public int getExperienceValue() {
        return 0;
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
    public boolean a(MobEffectList par1Potion){
        return false;
    }

    public EntityDamageResult attackEntityFrom(Damage damage) {
        EntityDamageResult result = super.attackEntityFrom(damage);
        if (result != null && !result.entityWasDestroyed()) {
            if (this.aN() <= 0.0F) {
                return null;
            } else if (damage.isFireDamage() && this.a(MobEffectList.n)) {
                return null;
            } else {
                if (damage.isAnvil() || damage.isFallingBlock()) {
                    ItemStack head_armor = this.getHelmet();
                    if (head_armor != null) {
                        result.applyArmorDamageResult(head_armor.tryDamageItem(damage.getSource(), (int)(damage.getAmount() * 4.0F + this.ab.nextFloat() * damage.getAmount() * 2.0F), dyCast(this)));
                        damage.scaleAmount(0.75F);
                    }
                }

                if (damage.isNil()) {
                    return result;
                } else {
                    if (damage.isLessThanHalfAHeart()) {
                        damage.setAmount(1.0F);
                    }

                    boolean treat_as_completely_new_attack = this.af * 2 <= this.aI;
                    this.attackEntityFromHelper(damage, result);
                    this.checkForAfterDamage(damage,result);
                    boolean make_death_sound = result.entityWasDestroyed();
                    boolean make_hurt_sound = !make_death_sound && (result.entityWasKnockedBack() || result.entityLostHealth());
                    if (result.entityWasNegativelyAffected()) {
                        if (treat_as_completely_new_attack) {
                            boolean refresh_red_tint = result.entityLostHealth();
                            if (refresh_red_tint && damage.isSunlight() && (dyCast(this) instanceof EntityShadow || dyCast(this) instanceof EntityNightwing)) {
                                refresh_red_tint = false;
                            }

                            this.q.setEntityState(this, refresh_red_tint ? EnumEntityState.hurt_with_red_tint_refreshed : EnumEntityState.hurt_without_red_tint_refreshed);
                            if (make_death_sound) {
                                this.makeSound(this.aP());
                            } else if (make_hurt_sound) {
                                this.makeSound(this.aO());
                            }
                        }

                        if (!damage.isDrowning()) {
                            this.K();
                        }

                        this.aG = 1.5F;
                        this.aA = 0.0F;
                        Entity responsible_entity = damage.getSource().getResponsibleEntity();
                        if (responsible_entity == null) {
                            this.aA = (float)((int)(Math.random() * 2.0D) * 180);
                        } else {
                            this.refreshDespawnCounter(-1200);
                            responsible_entity.refreshDespawnCounter(-1200);
                            if (responsible_entity instanceof EntityLiving) {
                                this.b((EntityLiving)responsible_entity);
                                this.setLastHarmingEntity(responsible_entity);
                                this.considerFleeing();
                            }

                            if (!(responsible_entity instanceof EntityHuman)) {
                                if (responsible_entity instanceof EntityWolf) {
                                    EntityWolf var5 = (EntityWolf)responsible_entity;
                                    if (var5.bT()) {
                                        this.aT = 100;
                                        this.aS = null;
                                    }
                                }
                            } else {
                                if (!(damage.getSource().getImmediateEntity() instanceof EntitySnowball) || this.canTakeDamageFromPlayerThrownSnowballs()) {
                                    this.aT = 100;
                                }

                                this.aS = (EntityHuman)responsible_entity;
                                this.refreshDespawnCounter(-9600);
                            }
                        }

                        if (result.entityWasDestroyed()) {
                            this.a(damage.getSource());
                        }
                    }

                    return result;
                }
            }
        } else {
            return result;
        }
    }

    protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {

    }

    @Marker
    protected String aO() {
        return "damage.hit";
    }

    @Marker
    protected String aP() {
        return "damage.hit";
    }


    @Marker
    private EntityDamageResult attackEntityFromHelper(Damage damage, EntityDamageResult result){
        return null;
    }
    @Marker
    public void makeSound(String sound) {

    }
    @Marker
    public boolean considerFleeing() {
        return false;
    }

    @Marker
    public ItemStack getHelmet() {
        return null;
    }

    @Marker
    public void setLastHarmingEntity(Entity entity){

    }

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
