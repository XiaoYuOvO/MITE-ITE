package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.List;

@Transform(EntityPigZombie.class)
public class EntityZombiePigmanTrans extends EntityZombie implements IRangedEntity {
    @Link
    protected static IAttribute bp;
    private final PathfinderGoalArrowAttack arrowAttack = new PathfinderGoalArrowAttack((this), 1.0D, 20, 60, 15.0F);
    private final PathfinderGoalMeleeAttack meleeAttack = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);
    private int DATA_OBJ_ID_IS_BOOSTED;
    private int effectCooldown;

    public void setHeldItemStack(ItemStack item_stack) {
        super.setHeldItemStack(item_stack);
        if (this.onServer()) {
            this.reCalcProfession();
        }

    }

    public void reCalcProfession() {
        this.c.a(this.arrowAttack);
        this.c.a(this.meleeAttack);
        ItemStack var1 = this.getHeldItemStack();
        if (var1 != null && var1.b() instanceof ItemBow) {
            this.c.a(4, this.arrowAttack);
            this.c.a(3, new EntityAISeekFiringPosition(this, 1.0F, true));
        } else {
            this.c.a(4, this.meleeAttack);
        }

    }

    public EntityZombiePigmanTrans(World par1World) {
        super(par1World);
        this.c.clear();
        this.c.a(1, new EntityAIWatchAnimal(this));
        this.c.a(0, new PathfinderGoalFloat(this));
        this.c.a(1, new PathfinderGoalBreakDoor(this));
//        this.c.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.c.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
        this.c.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.c.a(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.c.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.c.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.c.a(7, new PathfinderGoalRandomLookaround(this));
        this.d.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.c.a(2, new EntityAIMoveToFoodItem(this, 1.0F, true));
        this.c.a(4, new PathfinderGoalMeleeAttack(this, EntityAnimal.class, 1.0D, true));
        this.d.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityAnimal.class, 10, true));
        this.c.a(3, new EntityAIMoveToTree(this, 1.0F));

    }

    //Use new AI
    protected boolean bf() {
        return true;
    }

    public boolean canCatchFire() {
        return false;
    }

    public boolean willPickupAsValuable(ItemStack item_stack) {
        return item_stack.getItemSubtype() == 0 && item_stack.k() == 0 && item_stack.hasMaterial(Material.gold);
    }

    @Override
    public void a(EntityLiving entityLiving, float par2) {
        EntityArrow var3 = new EntityArrow(this.getWorld(), this, entityLiving, 1.6F, (float) (14 - this.getWorld().r * 4), Item.arrowRustedIron, false);
        int rawDay = this.getWorld() != null ? this.getWorld().getDayOfWorld() : 0;
        int day = Math.max(rawDay - 64, 0);
        int var4 = EnchantmentManager.a(Enchantment.v.z, this.getHeldItemStack()) + 1;
        int var5 = (int) (EnchantmentManager.a(Enchantment.w.z, this.getHeldItemStack()) + Math.min((1 + Math.floor(day / 48F)), 5));
        double damage = (double) (par2 * 2.0F) + this.aD().nextGaussian() * 0.0D + (double) ((float) this.getWorld().r * 0.11F);
        var3.b(damage);
        if (var4 > 0) {
            var3.b(var3.c() + (double) var4 * 2.0D + 1);
        }

        if (var5 > 0) {
            var3.a(var5);
        }

        if ((EnchantmentManager.a(Enchantment.x.z, this.getHeldItemStack()) > 0 || (this.af() && this.aD().nextInt(3) == 0)) || rawDay > 196) {
            var3.d(100);
        }

        this.a("random.bow", 1.0F, 1.0F / (this.aD().nextFloat() * 0.4F + 0.8F));
        this.getWorld().d(var3);
    }

    protected void a() {
        super.a();
        this.DATA_OBJ_ID_IS_BOOSTED = this.ah.addObject(this.ah.getNextAvailableId(), (byte) 0);
    }

    public GroupDataEntity a(GroupDataEntity par1EntityLivingData) {
        super.a(par1EntityLivingData);
        this.setVillager(false, 0);
        this.ah.b(DATA_OBJ_ID_IS_BOOSTED, (byte) ((this.ab.nextInt(10) < 1) ? 1 : 0));
        if (this.getWorld() != null && !this.getWorld().I) {
            if (this.randomUseBow() && MITEITEMod.CONFIG.get(Config.ConfigEntry.ZOMBIE_PIGMAN_USE_BOW)) {
                this.c.a(4, this.arrowAttack);
                this.c.a(3, new EntityAISeekFiringPosition(this, 1.0F, true));
                this.c(0, (new ItemStack(Item.m)).randomizeForMob(this, true));
            } else {
                this.c.a(4, this.meleeAttack);
                this.c.a(2, meleeAttack);
            }
        }
        return par1EntityLivingData;
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
        this.setEntityAttribute(GenericAttributes.a, 50.0D + day / 12D);
        this.setEntityAttribute(GenericAttributes.b, 64D);
        this.setEntityAttribute(GenericAttributes.d, 0.25F);
        this.setEntityAttribute(GenericAttributes.e, 10D + day / 48D);
        this.setEntityAttribute(EntityZombie.bp, this.ab.nextDouble() * (double) 0.1F);
    }

    public void c() {
        super.c();
        if (this.getWorld().isWorldServer() && this.ah.a(DATA_OBJ_ID_IS_BOOSTED) > 0) {
            if (effectCooldown <= 0) {
                int day = MinecraftServer.F().getOverworld().getWorld().getDayOfWorld();
                if (day > 128) {
                    List<EntityPigZombie> nearbyZombie = this.q.a(EntityPigZombie.class, this.E.b(16.0D, 8.0D, 16.0D));
                    for (EntityPigZombie entityPigZombie : nearbyZombie) {
                        entityPigZombie.c(new MobEffect(1, 40 + day / 32 * 10, this.aD().nextInt(Math.max(((day - 96) / 96), 1)), false));
                        entityPigZombie.c(new MobEffect(5, day / 32 * 10, this.aD().nextInt(Math.max(((day - 128) / 96), 1)), false));
                    }

                    entityFX(EnumEntityFX.smoke_and_steam);
                    effectCooldown = 30;
                }
            } else {
                effectCooldown--;
            }
        }
    }

    public boolean randomUseBow() {
        return this.ab.nextInt(10) > 2;
    }
}
