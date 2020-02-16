package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import java.lang.reflect.Field;
import java.util.Random;

import static net.xiaoyu233.mitemod.miteite.liar.util.Constant.ARMORS;

@Deceive(EntityMonster.class)
public class EntityMonsterLiar extends EntityInsentient implements IMonster {
    @Liar
    private static Field picked_up_a_held_item_field;
    @Liar
    private static Field bt_field;

    static {
        try {
            picked_up_a_held_item_field = EntityInsentient.class.getDeclaredField("picked_up_a_held_item");
            bt_field = EntityInsentient.class.getDeclaredField("bt");
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    @Stealing
    public EntityMonsterLiar(World par1World) {
        super(par1World);
    }



    protected void bw() {
        int hour = this.getWorld().getHourOfDay();
        int day = this.getWorld().getDayOfWorld();
        if (day > 32 && ((day % 2 == 0 && hour>=18)||((day-1) % 2 == 0 && hour <= 6))) {
            this.c(new MobEffect(1, 999999, 0, true));
            Random rand = this.aD();
            if (rand.nextInt(5) == 0) {
                this.c(new MobEffect(5, 999999, 0, true));
            }
            addDefaultArmor(day);

        }

    }

    protected void az() {
        super.az();

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

    private int getRandomItemTier(Random random,int maxTier,int minTier){
        int now = minTier;
        while (now < maxTier && random.nextInt(2 * now + 1) == 0) {
            now++;
        }
        return now;
    }

    private void addDefaultArmor(int day_count){
        Random rand = this.aD();
        if (rand.nextInt(3 - Math.min(day_count/128,2)) == 0||day_count>365){
            int minTier = rand.nextInt(2 + Math.min(day_count/64,6))+1;
            for (int index = 4;index > 1;index--){
                if (rand.nextInt(5-Math.min(day_count/32,4)) == 0){
                    this.c(index,new ItemStack(ARMORS[index-1][getRandomItemTier(rand,11,minTier)]).randomizeForMob(this,day_count > 64));
                }
            }
        }
    }

    @Override
    protected void a(boolean recently_hit_by_player, int par2) {
        for(int var3 = 0; var3 < this.ae().length; ++var3) {
            ItemStack var4 = this.n(var3);
            if (var4 != null) {
                try {
                    if (!var4.g()||((boolean) picked_up_a_held_item_field.get(this) && var4.getRemainingDurability() > var4.l()/4)) {
                        this.dropItemStack(var4, 0.0F);
                        this.c(var3, null);
                    }
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
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
