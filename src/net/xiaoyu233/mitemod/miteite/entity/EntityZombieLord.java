package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class EntityZombieLord extends EntityZombie {
    private int fx_counter;
    public EntityZombieLord(World par1World) {
        super(par1World);
    }

    @Override
    protected void az() {
        super.az();
        int day = this.getWorld().getDayOfWorld();
        this.setEntityAttribute(GenericAttributes.e, 12.0D + (double)day / 48.0D);
        this.setEntityAttribute(GenericAttributes.a, 50.0D + (double)day / 14.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.3D);
    }

    @Override
    public void l_() {
        super.l_();
        if (!this.getWorld().I){
            if (fx_counter > 0){
                fx_counter--;
            }else {
                this.fx_counter = 60;
                this.entityFX(EnumEntityFX.summoned);
            }
        }
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        int diamond_count = 3;
        for (int i1 = 0; i1 < diamond_count; i1++) {
            this.dropItem(Item.p);
        }
    }

    @Override
    protected void addRandomEquipment() {
        int day = this.getWorld().getDayOfWorld();
        this.c(0,new ItemStack(Items.VIBRANIUM_SWORD,1).randomizeForMob(this,day > 64));
        this.c(1,new ItemStack(Items.VIBRANIUM_HELMET,1).randomizeForMob(this,day > 64));
        this.c(2,new ItemStack(Items.VIBRANIUM_CHESTPLATE,1).randomizeForMob(this,day > 64));
        this.c(3,new ItemStack(Items.VIBRANIUM_LEGGINGS,1).randomizeForMob(this,day > 64));
        this.c(4,new ItemStack(Items.VIBRANIUM_BOOTS,1).randomizeForMob(this,day > 64));
        this.c(new MobEffect(1,Integer.MAX_VALUE,0));
        this.c(new MobEffect(5,Integer.MAX_VALUE,0));
    }
}
