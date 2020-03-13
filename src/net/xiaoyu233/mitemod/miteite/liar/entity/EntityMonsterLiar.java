package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import java.util.Random;

import static net.xiaoyu233.mitemod.miteite.util.Constant.ARMORS;

@Deceive(EntityMonster.class)
public class EntityMonsterLiar extends EntityInsentient implements IMonster {
    @Liar
    public boolean[] picked_up_a_held_item_array;
    @Stealing
    public EntityMonsterLiar(World par1World) {
        super(par1World);
    }
    protected void bw() {
        int hour = this.getWorld().getHourOfDay();
        int day = this.getWorld().getDayOfWorld();
        if ((day > 32 && ((day % 2 == 0 && hour>=18)||((day-1) % 2 == 0 && hour <= 6)) )|| day > 365) {
            this.c(new MobEffect(1, 999999, this.aD().nextInt(Math.max(((day-32)/96),1)), true));
            Random rand = this.aD();
            if (rand.nextInt(5) == 0) {
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

    @Stealing
    public boolean canSpawnInShallowWater() {
        return false;
    }
}
