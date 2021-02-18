package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class EntityZombiePigmanLord extends EntityPigZombie {
    private int fx_counter;
    public EntityZombiePigmanLord(World par1World) {
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

    private void generateRandomParticles(EnumParticle particle) {
        for(int var2 = 0; var2 < 5; ++var2) {
            double var3 = this.ab.nextGaussian() * 0.02D;
            double var5 = this.ab.nextGaussian() * 0.02D;
            double var7 = this.ab.nextGaussian() * 0.02D;
            this.q.spawnParticle(particle, this.u + (double)(this.ab.nextFloat() * this.O * 2.0F) - (double)this.O, this.v + (double)(this.ab.nextFloat() * this.P), this.w + (double)(this.ab.nextFloat() * this.O ) - (double)this.O, var3, var5, var7);
        }

    }

    @Override
    public void handleHealthUpdate(EnumEntityState par1) {
        super.handleHealthUpdate(par1);
        if (par1 == EnumEntityState.unused0){
            this.generateRandomParticles(EnumParticle.lava);
        }
    }

    @Override
    public void l_() {
        super.l_();
        if (!this.getWorld().I){
            if (fx_counter > 0){
                fx_counter--;
            }else {
                this.fx_counter = 60;
                this.q.setEntityState(this,EnumEntityState.unused0);
            }
        }
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            int diamond_count = 3;
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.p);
            }
        }
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void addRandomEquipment() {
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
