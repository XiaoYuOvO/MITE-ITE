package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityBat.class)
public class EntityBatTrans extends EntityAmbient {
    private boolean spawnedByWitch;
    private EntityWanderingWitch spawnerWitch;

    public EntityBatTrans(World par1World) {
        super(par1World);
    }

    @Override
    public void addPotionEffect(MobEffect par1PotionEffect) {
        if (!this.spawnedByWitch){
            super.addPotionEffect(par1PotionEffect);
        }
    }

    @Override
    public int getExperienceValue() {
        if (this.spawnedByWitch){
            return 0;
        }
        return super.getExperienceValue();
    }

    @Override
    public float getNaturalDefense(DamageSource damage_source) {
        if (spawnedByWitch){
            return damage_source.hasMagicAspect() && damage_source.isIndirect() ? Integer.MAX_VALUE : super.getNaturalDefense(damage_source);
        }
        return super.getNaturalDefense(damage_source);
    }

    @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
    private void injectReadNBT(NBTTagCompound nbt, CallbackInfo callbackInfo){
        if (nbt.hasKey("SpawnedByWitch")){
            this.spawnedByWitch = nbt.getBoolean("SpawnedByWitch");
            Entity entityByID = this.worldObj.getAsWorldServer().getEntityByID(nbt.getInteger("WitchSpawner"));
            this.spawnerWitch = (EntityWanderingWitch) entityByID;
        }
    }

    @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
    private void injectWriteNBT(NBTTagCompound nbt, CallbackInfo callbackInfo){
        if (this.spawnedByWitch){
            nbt.setBoolean("SpawnedByWitch", true);
            nbt.setInteger("WitchSpawner",this.spawnerWitch.entityId);
        }
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (this.spawnerWitch != null && this.spawnerWitch.isEntityAlive()){
            this.spawnerWitch.onAllyBatsDeath();
        }
    }

    public void setSpawnedByWitch(boolean spawnedByWitch,EntityWanderingWitch witch) {
        this.spawnedByWitch = spawnedByWitch;
        this.spawnerWitch = witch;
    }
}
