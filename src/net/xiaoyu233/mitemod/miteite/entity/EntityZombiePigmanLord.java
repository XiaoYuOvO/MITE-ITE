package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class EntityZombiePigmanLord extends EntityPigZombie {
    private int fx_counter;
    public EntityZombiePigmanLord(World par1World) {
        super(par1World);
    }

    @Override
    public void addRandomEquipment() {
        int day = this.getWorld().getDayOfWorld();
        this.setCurrentItemOrArmor(0,new ItemStack(Items.VIBRANIUM_SWORD,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(1,new ItemStack(Items.VIBRANIUM_HELMET,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(2,new ItemStack(Items.VIBRANIUM_CHESTPLATE,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(3,new ItemStack(Items.VIBRANIUM_LEGGINGS,1).randomizeForMob(this,day > 64));
        this.setCurrentItemOrArmor(4,new ItemStack(Items.VIBRANIUM_BOOTS,1).randomizeForMob(this,day > 64));
        this.addPotionEffect(new MobEffect(1,Integer.MAX_VALUE,0));
        this.addPotionEffect(new MobEffect(5,Integer.MAX_VALUE,0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfWorld();
        this.setEntityAttribute(GenericAttributes.attackDamage, 12.0D + (double)day / 48.0D);
        this.setEntityAttribute(GenericAttributes.maxHealth, 50.0D + (double)day / 14.0D);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3D);
    }

    @Override
    public void handleHealthUpdate(EnumEntityState par1) {
        super.handleHealthUpdate(par1);
        if (par1 == EnumEntityState.unused0){
            this.generateRandomParticles(EnumParticle.lava);
        }
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            int diamond_count = 3;
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.diamond);
            }
        }
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }

    private void generateRandomParticles(EnumParticle particle) {
        for(int var2 = 0; var2 < 5; ++var2) {
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(particle, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
        }

    }

    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            if (fx_counter > 0){
                fx_counter--;
            }else {
                this.fx_counter = 60;
                this.worldObj.setEntityState(this,EnumEntityState.unused0);
            }
        }
    }
}
