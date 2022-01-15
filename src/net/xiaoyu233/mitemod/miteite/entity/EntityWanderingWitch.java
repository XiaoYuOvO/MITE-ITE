package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.util.Random;
import java.util.UUID;

public class EntityWanderingWitch extends EntityWitch {
    private static final AttributeModifier field_110185_bq = (new AttributeModifier(UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E"), "Drinking speed penalty", -0.25D, 0)).setSaved(false);

    private int aliveBatsCount;
    private final int maxBatsCount = 8;
    private boolean cursedPlayer;
    private int throwingPotionCooldown;

    public EntityWanderingWitch(World par1World) {
        super(par1World);
        this.tasks.removeTask(this.tasks.getTask(PathfinderGoalArrowAttack.class));
        this.tasks.addTask(2, new PathfinderGoalArrowAttack(this, 1.0D, 42, 10.0F){
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !this.getEntityHost().fleeing;
            }
        });
        this.tasks.addTask(1,new PathfinderGoalAvoidPlayer(this,EntityPlayer.class,3,1.1,1.4));
    }

    public void cursePlayer(ServerPlayer player) {

    }

    @Override
    public boolean canBeTargetTo(EntityLiving from) {
        return false;
    }

    public void cursePlayerSpecial(ServerPlayer player) {
        if (!this.worldObj.isRemote) {
            if (!(this.getHealth() <= 0.0F) && !player.is_cursed && !player.hasCursePending()) {
                int username_hash = 0;

                for(int i = 0; i < player.getEntityName().length(); ++i) {
                    username_hash += player.getEntityName().charAt(i) * i;
                }

                ((WorldServer)this.worldObj).addCurse(player, this, Curse.getRandomCurse(new Random(this.getCurse_random_seed() + username_hash)), 0);
            }
        }
    }

    public boolean getAggressive() {
        if (this.getSummon_wolf_countdown() > 0) {
            this.setSummon_wolf_countdown(this.getSummon_wolf_countdown() - 1);
            if (this.getSummon_wolf_countdown() == 0) {
                this.setHas_summoned_wolves(this.summonWolvesP() > 0 || this.isHas_summoned_wolves());
            }
        } else if (this.getLastHarmingEntity() instanceof EntityPlayer) {
            this.setSummon_wolf_target((EntityLiving)this.getLastHarmingEntity());
            this.setSummon_wolf_countdown(160);
        }
        return true;
    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        boolean chanceSpawn = false;
        if (this.worldObj.isOverworld()){
            chanceSpawn = this.rand.nextInt(100) < Configs.GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_CHANCE_OVERWORLD.get();
        }
        if (this.worldObj.isUnderworld()){
            chanceSpawn = this.rand.nextInt(100) < Configs.GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_CHANCE_UNDERWORLD.get();
        }
        if (chanceSpawn){
            if (this.getWorld().isOverworld()){
                return super.getCanSpawnHere(perform_light_check) && this.worldObj.getDayOfOverworld() > Configs.GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_LIMIT_DAY_OVERWORLD.get();
            }else {
                return super.getCanSpawnHere(perform_light_check) && this.worldObj.getDayOfOverworld() > Configs.GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_LIMIT_DAY_OTHER.get();
            }
        }
        return false;
    }

    public void onAllyBatsDeath(){
        this.aliveBatsCount--;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        EntityPlayer closestPlayerToEntity1 = this.worldObj.getClosestPlayerToEntity(this, 4, true);
        this.fleeing = closestPlayerToEntity1 != null;
        if (this.onServer()) {
            if (this.throwingPotionCooldown > 0){
                this.throwingPotionCooldown--;
            }
            if (this.fleeing){
                if (this.throwingPotionCooldown <= 0) {
                    closestPlayerToEntity1.addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, rand.nextInt(30) + 20,0));
                    this.attackEntityWithRangedAttack(closestPlayerToEntity1,1.0f);
                    this.summonWolvesP();
                    this.throwingPotionCooldown = 80;
                    this.worldObj.setEntityState(this,EnumEntityState.witch_magic);
                }
            }
            if (!this.cursedPlayer) {
                EntityPlayer closestPlayerToEntity = this.worldObj.getClosestPlayerToEntity(this, 32, true);
                if (closestPlayerToEntity != null) {
                    if (closestPlayerToEntity.canSeeEntity(this)) {
                        this.cursePlayerSpecial((ServerPlayer) closestPlayerToEntity);
                        this.cursedPlayer = true;
                    }
                }
            }
        }
    }


    @Override
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLivingBase, float par2) {
        EntityPotion var3 = new EntityPotion(this.worldObj, this, 32732);
        var3.rotationPitch -= -20.0F;
        double var4 = par1EntityLivingBase.posX + par1EntityLivingBase.motionX - this.posX;
        double var6 = par1EntityLivingBase.posY + (double)par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - this.posY;
        double var8 = par1EntityLivingBase.posZ + par1EntityLivingBase.motionZ - this.posZ;
        float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        if (par1EntityLivingBase.getHealth() >= 8.0F && !par1EntityLivingBase.isPotionActive(MobEffectList.poison)) {
            var3.setPotionType(32660);
        }

        float distance_squared = (float)(var4 * var4 + var8 * var8);
        if (par1EntityLivingBase instanceof ServerPlayer) {
            float lead = (float)Math.pow(distance_squared, 0.5D);
            lead *= 0.5F + this.rand.nextFloat();
            var4 = par1EntityLivingBase.getPredictedPosX(lead) - this.posX;
            var8 = par1EntityLivingBase.getPredictedPosZ(lead) - this.posZ;
        }

        var3.setThrowableHeading(var4, var6 + (double)(var10 * 0.2F), var8, 0.75F, 8.0F);
        double y_correction = distance_squared * 0.001F - 0.025F;
        var3.motionY += y_correction;
        var3.motionX *= 1.2000000476837158D;
        var3.motionY *= 0.7000000476837158D;
        var3.motionZ *= 1.2000000476837158D;
        this.worldObj.spawnEntityInWorld(var3);
    }

    @Override
    public int summonWolvesP() {
        EntityLiving summon_wolf_target = this.getSummon_wolf_target();
        if (summon_wolf_target != null){
            EntityLiving target = (EntityLiving)this.worldObj.getEntityByID(summon_wolf_target.entityId);
            if (target != null && !target.isDead) {
                int target_x = (target.getBlockPosX() + this.getBlockPosX()) / 2;
                int target_y = (target.getBlockPosY() + this.getFootBlockPosY()) / 2;
                int target_z = (target.getBlockPosZ() + this.getBlockPosZ()) / 2;
                int batCounts = this.maxBatsCount - this.aliveBatsCount;

                for(int attempts = 0; attempts < batCounts; ++attempts) {
                    EntityBat bat = rand.nextInt(10) > 8 ? new EntityNightwing(this.worldObj) : new EntityGiantVampireBat(this.worldObj);
                    bat.addPotionEffect(new MobEffect(MobEffectList.damageBoost.id,Integer.MAX_VALUE,1));
                    bat.setSpawnedByWitch(true,this);
                    bat.setPosition(target_x,target_y,target_z);
                    this.worldObj.spawnEntityInWorld(bat);
                    this.aliveBatsCount++;
                    Navigation navigator = bat.getNavigator();
                    PathEntity path = this.worldObj.getEntityPathToXYZ(bat, target_x, target_y, target_z, 32.0F, navigator.canPassOpenWoodenDoors, false, navigator.avoidsWater, navigator.canSwim);
                    if (path != null) {
                        PathPoint final_point = path.getFinalPathPoint();
                        if (!(World.getDistanceSqFromDeltas((float)(final_point.xCoord - target_x), (float)(final_point.yCoord - target_y), (float)(final_point.zCoord - target_z)) > 2.0D)) {
                            bat.refreshDespawnCounter(-9600);
                            bat.onSpawnWithEgg(null);
                            bat.setAttackTarget(target);
                            bat.entityFX(EnumEntityFX.summoned);
                        }
                    }
                }

                return batCounts;
            } else {
                return 0;
            }
        }
        return 0;
    }

    public boolean considerStopFleeing() {
        return this.worldObj.getClosestPlayerToEntity(this, 3, true) == null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.maxHealth,80d);
        this.setEntityAttribute(GenericAttributes.movementSpeed,0.3d);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("AliveBatsCount",this.aliveBatsCount);
        par1NBTTagCompound.setBoolean("CursedPlayer",this.cursedPlayer);
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        super.dropFewItems(recently_hit_by_player, damage_source);
        this.dropItemStack(new ItemStack(Item.adamantiumNugget,rand.nextInt(3) + 1));
        this.dropItemStack(new ItemStack(Item.netherStalkSeeds,4));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("AliveBatsCount")){
            this.aliveBatsCount = par1NBTTagCompound.getInteger("AliveBatsCount");
        }
        if (par1NBTTagCompound.hasKey("CursedPlayer")){
            this.cursedPlayer = par1NBTTagCompound.getBoolean("CursedPlayer");
        }
    }
}
