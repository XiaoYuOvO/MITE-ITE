package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.Random;

import static net.xiaoyu233.mitemod.miteite.util.Constant.ARMORS;

@Transform(EntityGiantZombie.class)
public class EntityGiantZombieTrans extends EntityMonster {
    private int spawnCounter;
    public EntityGiantZombieTrans(World var1) {
        super(var1);
        this.N *= 6.0F;
        this.a(this.O * 2.0f, this.P * 6.5F);
        this.c.a(0, new PathfinderGoalFloat(this));
        this.c.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.1D, true));
        this.c.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
        this.c.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.c.a(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.c.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.c.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.c.a(7, new PathfinderGoalRandomLookaround(this));
        this.d.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.d.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.c.a(4, new PathfinderGoalMeleeAttack(this, EntityAnimal.class, 1.0D, true));
        this.d.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityAnimal.class, 10, true));
    }

    protected String r() {
        return "mob.zombie.say";
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player) {
            if (this.getWorld().getDayOfWorld() < 128) {
                this.dropItemStack(new ItemStack(Item.ancientMetalNugget, 4 + this.ab.nextInt(2)));
            } else {
                this.dropItemStack(new ItemStack(Item.ingotMithril, 1));
            }
        }
        super.dropFewItems(recently_hit_by_player, damage_source);
    }

    @Override
    public void c() {
        super.c();
        if (this.spawnCounter < MITEITEMod.CONFIG.get(Config.ConfigEntry.GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN)){
            this.spawnCounter++;
        }else {
            EntityZombie zombie = new EntityZombie(this.q);
            zombie.a((double) this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ());
            zombie.refreshDespawnCounter(-9600);
            this.q.d(zombie);
            zombie.a((GroupDataEntity) null);
            zombie.d(this.getTarget());
            zombie.entityFX(EnumEntityFX.summoned);
            this.spawnCounter = 0;
        }
    }

    protected String aO() {
        return "mob.zombie.hurt";
    }

    protected String aP() {
        return "mob.zombie.death";
    }

    protected void a(int par1, int par2, int par3, int par4) {
        this.makeSound("mob.zombie.step", 0.15F, 1.0F);
    }

    public EnumMonsterType aY() {
        return EnumMonsterType.b;
    }

    public float getReach() {
        return super.getReach() * 2f;
    }

    @Override
    protected boolean bf() {
        return true;
    }

    @Override
    public boolean considerFleeing() {
        return false;
    }

    @Override
    protected EntityHuman findPlayerToAttack(float max_distance) {
        EntityHuman target = super.findPlayerToAttack(max_distance * 2);
        if (target != null){
            this.c(target);
        }
        return target;
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.a(GenericAttributes.e).a(30.0D + (double)day / 20D);
        this.a(GenericAttributes.a).a(100.0D + (double)day / 16.0D * 10);
        this.a(GenericAttributes.d).a(0.3D);
    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        Vec3D pos = this.getFootPos();
        return !this.getWorld().anySolidBlockIn(this.E.a(0.0,1.0,0.0)) && this.getWorld().getClosestEntityLivingBase(this,new Class[]{this.getClass()},96,false,false) == null && this.getWorld().getBlock(pos.getBlockX(),pos.getBlockY(),pos.getBlockZ()) != Block.F;
    }

    protected void bw() {
        int hour = this.getWorld().getHourOfDay();
        int day = this.getWorld().getDayOfWorld();
        if ((day > 32 && (((day % 2 == 0 || day > 64) && hour>=18)||(((day-1) % 2 == 0 || day > 64) && hour <= 6)) )) {
            this.c(new MobEffect(1, 999999, this.aD().nextInt(Math.max(((day-32)/96),1)), true));
            Random rand = this.aD();
            if (rand.nextInt(5) == 0) {
                this.c(new MobEffect(5, 999999, this.aD().nextInt(Math.max(((day-32)/128),1)), true));
            }
            addDefaultArmor(day,this,false);
        }else if ( day > 128){
            Random rand = this.aD();
            if (rand.nextInt(4)< (day-96)/32) {
                this.c(new MobEffect(1, 999999, this.aD().nextInt(Math.max(((day - 32) / 96), 1)), true));
            }
            if (rand.nextInt(5)< (day-96)/32) {
                this.c(new MobEffect(5, 999999, this.aD().nextInt(Math.max(((day-32)/128),1)), true));
            }
            addDefaultArmor(day,this,false);
        }
    }

    private static int getRandomItemTier(Random random,int maxTier,int minTier,int dayCount){
        int now = minTier;
        while (now < maxTier && random.nextInt(Math.max((2 * now + 1)-(dayCount/12),1)) == 0) {
            now++;
        }
        return now;
    }

    public static void addDefaultArmor(int day_count,EntityInsentient monster,boolean haveAll){
        Random rand = monster.aD();
        if (rand.nextInt(3 - Math.min(day_count/128,2)) == 0||day_count>365 || haveAll){
            int minTier = rand.nextInt(2 + Math.min(day_count/64,6))+1;
            for (int index = 4;index > 0;index--){
                if (rand.nextInt(5-Math.min(day_count/32,4)) == 0||day_count>192 || haveAll){
                    monster.c(index,new ItemStack(ARMORS[index-1][Math.min(getRandomItemTier(rand,Math.min(10,day_count/16),minTier,day_count) + (day_count > 365 ? 1 : 0),ARMORS[index-1].length-1)]).randomizeForMob(monster,day_count > 64));
                }
            }
        }
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.1d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }

    @Override
    protected void a(boolean recently_hit_by_player, int par2) {
        for(int var3 = 0; var3 < this.ae().length; ++var3) {
            ItemStack var4 = this.n(var3);
            if (var4 != null) {
                if (!var4.g()||(picked_up_a_held_item_array[var3] && var4.getRemainingDurability() > var4.l()/4)) {
                    this.dropItemStack(var4, 0.0F);
                    this.c(var3, null);
                }
            }
        }
    }
}
