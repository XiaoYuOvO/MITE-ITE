package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.Utils;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;

public class EntityAnnihilationSkeleton extends EntitySkeleton {
    private boolean attackedByPlayer;
    private int despawnCount;
    private final ItemStack weapon = Utils.safeMake(()-> {
        ItemStack itemStack = new ItemStack(Items.VIBRANIUM_DAGGER);
        itemStack.addEnchantment(Enchantment.knockback,5);
        return itemStack;
    }, null);
    private int particleCount;
    public EntityAnnihilationSkeleton(World par1World) {
        super(par1World);
        this.setHeldItemStack(weapon);
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }

    @Override
    public void calcFallDamage(float fall_distance, float[] damages) {
        damages[0] = 0.0F;
        damages[1] = 0.0F;
    }

    @Override
    public void setAttackTarget(EntityLiving par1EntityLivingBase) {
        super.setAttackTarget(par1EntityLivingBase);
        this.attackedByPlayer = true;
    }

    @Override
    public void setEntityToAttack(Entity par1Entity) {
        super.setEntityToAttack(par1Entity);
        this.attackedByPlayer = true;
    }

    @Override
    public void addPotionEffect(MobEffect par1PotionEffect) {

    }

    @Override
    public void addRandomWeapon() {
        this.setHeldItemStack(weapon);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("AttackedByPlayer",attackedByPlayer);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("AttackedByPlayer")) {
            this.attackedByPlayer = par1NBTTagCompound.getBoolean("AttackedByPlayer");
        }
    }

    @Override
    protected void addRandomArmor() {

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.onServer())
            if (this.attackedByPlayer) {
                if (despawnCount < Configs.Entities.ANNIHILATION_SKELETON_DESPAWN_TIME.get()) {
                    despawnCount++;
                } else {
                    this.entityFX(EnumEntityFX.summoned);
                    this.setDead();
                }
            }
        if (particleCount > 0) {
            particleCount--;
        } else {
            this.particleCount = 20;
            this.generateRandomParticles(EnumParticle.largesmoke);
            this.generateRandomParticles(EnumParticle.largesmoke);
        }
    }

    @Override
    public boolean isHarmedByLava() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void setInWeb() {

    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    public boolean canBeKnockedBack() {
        return false;
    }

    @Override
    public boolean handleLavaMovement() {
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.maxHealth,30);
        this.setEntityAttribute(GenericAttributes.attackDamage,Integer.MAX_VALUE);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.2772D);
    }

    @Override
    public EntityDamageResult attackEntityFrom(Damage damage) {
        if (damage.isIndirect() || !damage.isMelee() || damage.isExplosion() || damage.isArrowDamage() || !(damage.getResponsibleEntityP() instanceof EntityPlayer)){
            damage.setAmount(0f);
        }else if (damage.getResponsibleEntityP() instanceof EntityPlayer){
            attackedByPlayer = true;
            if (Math.abs(this.getFootPosY() - ((EntityPlayer) damage.getResponsibleEntityP()).getFootPosY()) >= 3.0D){
                damage.setAmount(0f);
                return null;
            }
        }
        return super.attackEntityFrom(damage);

    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            this.dropItemStack(new ItemStack(Items.VIBRANIUM_NUGGET,5));
            this.dropItemStack(new ItemStack(Item.diamond,2));
        }
    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        return !this.worldObj.isOverworld() || (this.worldObj.getDayOfOverworld() > 32 && this.rand.nextInt(4) < 1 && this.worldObj.getClosestPlayerToEntity(this,24,true) == null && (Configs.GameMechanics.MobSpawning.ANNIHILATION_SKELETON_SPAWN_IN_LIGHT.get() || this.isValidLightLevel()));
    }

    @Override
    public GroupDataEntity onSpawnWithEgg(GroupDataEntity par1EntityLivingData) {
        return null;
    }

    @Override
    protected void addRandomEquipment() {
        this.addRandomWeapon();
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
    }
}
