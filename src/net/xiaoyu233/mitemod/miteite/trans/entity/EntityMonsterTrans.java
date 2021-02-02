package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.Random;

import static net.xiaoyu233.mitemod.miteite.util.Constant.ARMORS;

@Transform(EntityMonster.class)
public class EntityMonsterTrans extends EntityInsentient implements IMonster {
    @Link
    public boolean[] picked_up_a_held_item_array;
    @Marker
    public EntityMonsterTrans(World par1World) {
        super(par1World);
    }

    public EntityDamageResult attackEntityAsMob(Entity target) {
        if (this.isDecoy()) {
            return null;
        } else if (target instanceof EntityHuman && target.getAsPlayer().isImmuneByGrace()) {
            return null;
        } else {
            ItemStack held_item = this.getHeldItemStack();
            float critBouns = 0;
            boolean critical = false;
            if (EnchantmentManager.hasEnchantment(this.getHeldItemStack(), Enchantments.CRIT)){
                int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, this.getHeldItemStack());
                critical = this.ab.nextInt(10) < MITEITEMod.CONFIG.get(Config.ConfigEntry.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL) * critLevel;
                critBouns = critLevel * MITEITEMod.CONFIG.get(Config.ConfigEntry.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL).floatValue();
            }
            Damage damage = new Damage(DamageSource.a(this), (float)this.a(GenericAttributes.e).e());
            if (critical){
                damage.scaleAmount(1.5f);
                damage.addAmount(critBouns);
            }
            if (this.isFrenzied()) {
                damage.addAmount((float)this.getEntityAttributeBaseValue(GenericAttributes.e) * 0.5F);
            }

            int knockback_bonus = 0;
            if (target.isEntityLivingBase()) {
                damage.addAmount(EnchantmentWeaponDamage.getDamageModifiers(held_item, target.getAsEntityLivingBase()));
                knockback_bonus += EnchantmentManager.b(this, target.getAsEntityLivingBase());
            }

            int fire_aspect = EnchantmentManager.a(this);
            EntityDamageResult result = target.attackEntityFrom(damage.setFireAspect(fire_aspect > 0));
            if (result == null) {
                return result;
            } else {
                if (result.entityWasNegativelyAffected()) {
                    if (knockback_bonus > 0) {
                        target.g(-MathHelper.a(this.A * 3.1415927F / 180.0F) * (float)knockback_bonus * 0.5F, 0.1D, MathHelper.b(this.A * 3.1415927F / 180.0F) * (float)knockback_bonus * 0.5F);
                        this.x *= 0.6D;
                        this.z *= 0.6D;
                    }

                    if (fire_aspect > 0) {
                        target.d(fire_aspect * 4);
                    }

                    if (this.af() && !this.hasHeldItem() && this.ab.nextFloat() < (float)this.q.r * 0.3F) {
                        target.d(2 * this.q.r);
                    }

                    if (target.isEntityLivingBase()) {
                        if (this.q.I) {
                            System.out.println("EntityMob.attackEntityAsMob() is calling EnchantmentThorns.func_92096_a() on client");
                            Minecraft.temp_debug = "mob";
                        }

                        EnchantmentThorns.a(this, target.getAsEntityLivingBase(), this.ab);
                        int stunning = EnchantmentManager.getStunModifier(this, target.getAsEntityLivingBase());
                        if ((double)stunning > Math.random() * 10.0D) {
                            target.getAsEntityLivingBase().c(new MobEffect(MobEffectList.d.H, stunning * 50, stunning * 5));
                        }

                        this.heal((float)EnchantmentManager.getVampiricTransfer(this, target.getAsEntityLivingBase(), result.getAmountOfHealthLost()), EnumEntityFX.vampiric_gain);
                    }

                    if (target instanceof EntityHuman) {
                        this.refreshDespawnCounter(-9600);
                    }
                }

                return result;
            }
        }
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

    protected void az() {
        super.az();
        this.picked_up_a_held_item_array = super.picked_up_a_held_item_array;
            try {
                this.setEntityAttribute(GenericAttributes.a, this.getEntityAttributeValue(GenericAttributes.a) * 2);
                this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e) * 2);
            } catch (NullPointerException e) {
                this.setEntityAttribute(GenericAttributes.a, GenericAttributes.a.b() * 2d);
                this.setEntityAttribute(GenericAttributes.e, GenericAttributes.e.b() * 2d);
            }
            this.setEntityAttribute(GenericAttributes.b, 32.0D);
            this.setEntityAttribute(GenericAttributes.e);

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

    @Override
    public void dropContainedItems() {
    }

    @Marker
    public boolean canSpawnInShallowWater() {
        return false;
    }
}
