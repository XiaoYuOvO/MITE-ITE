package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.api.ITELivingBase;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntityPigZombie.class)
public class EntityZombiePigmanTrans extends EntityZombie implements IRangedAttackMob, ITELivingBase {
   @Unique
   private EntityAIArrowAttack arrowAttack;
   @Unique
   private EntityAIAttackOnCollide meleeAttack;
   @Unique
   private int DATA_OBJ_ID_IS_BOOSTED;
   @Unique
   private int effectCooldown;
   @Inject(method = "<init>", at = @At("RETURN"))
   private void injectInit(World par1World, CallbackInfo ci){
      arrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
      meleeAttack = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false);
   }

   public EntityZombiePigmanTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 50.0D + (double)day / 16.0D);
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.25D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 12.0D + (double)day / 20.0D);
      this.setEntityAttribute(EntityZombie.field_110186_bp, this.rand.nextDouble() * 0.10000000149011612D);
   }

   @Override
   public EntityDamageResult attackEntityAsMob(Entity target) {
      if (target != null && target.isEntityAlive() && rand.nextFloat() < Configs.Entities.NETHER_MOD_ATTACK_FIRE_CHANCE.get()){
         target.setFire(5);
      }
      return super.attackEntityAsMob(target);
   }

   public void attackEntityWithRangedAttack(EntityLivingBase entityLiving, float par2) {
      EntityArrow var3 = new EntityArrow(this.getWorld(), this, entityLiving, 1.6F, (float)(14 - this.getWorld().difficultySetting * 4), Item.arrowRustedIron, false);
      int rawDay = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      int day = Math.max(rawDay - 64, 0);
      int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItemStack()) + 1;
      int var5 = (int)((double)EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItemStack()) + Math.min(1.0D + Math.floor((float)day / 48.0F), 5.0D));
      double damage = (double)(par2 * 2.0F) + this.getRNG().nextGaussian() * 0.0D + (double)((float)this.getWorld().difficultySetting * 0.11F);
      var3.setDamage(damage);
      if (var4 > 0) {
         var3.setDamage(var3.getDamage() + (double)var4 * 2.0D + 1.0D);
      }

      if (var5 > 0) {
         var3.setKnockbackStrength(var5);
      }

      if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemStack()) > 0 || this.isInFire() && this.getRNG().nextInt(3) == 0 || rawDay > 196) {
         var3.setFire(100);
      }

      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.getWorld().spawnEntityInWorld(var3);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo) {
      this.tasks.clear();
      this.tasks.addTask(1, new EntityAIWatchAnimal(this));
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIBreakDoor(this));
      this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
      this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.tasks.addTask(6, new EntityAIWander(this, 1.0));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
      this.tasks.addTask(2, new EntityAIMoveToFoodItem(this, 1.0F, true));
      if (Configs.Entities.ZOMBIE_PIGMAN_ATTACK_ANIMAILS.get()) {
         this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityAnimal.class, 1.0, true));
         this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 10, true));
      }

      this.tasks.addTask(3, new EntityAIMoveToTree(this, 1.0F));
   }

   @Inject(method = "addRandomEquipment",at = @At("HEAD"))
   private void injectInitArmor(CallbackInfo callbackInfo){
      super.addRandomArmor();
   }

   public boolean canCatchFire() {
      return false;
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.DATA_OBJ_ID_IS_BOOSTED = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
   }

   protected boolean isAIEnabled() {
      return true;
   }

   @Inject(method = "onSpawnWithEgg", at = @At("RETURN"))
   public void onSpawnWithEgg(EntityLivingData par1EntityLivingData, CallbackInfoReturnable<EntityLivingData> cir) {
      this.dataWatcher.updateObject(this.DATA_OBJ_ID_IS_BOOSTED, (byte)((double)this.rand.nextFloat() < (Configs.Entities.ZOMBIE_PIGMAN_BOOST_CHANCE.get()) ? 1 : 0));
      if (this.getWorld() != null && !this.getWorld().isRemote) {
         if (this.randomUseBow() && (Configs.Entities.ZOMBIE_PIGMAN_USE_BOW.get())) {
            this.tasks.addTask(4, this.arrowAttack);
            this.tasks.addTask(3, new EntityAISeekFiringPosition(this, 1.0F, true));
            this.setCurrentItemOrArmor(0, (new ItemStack(Item.bow)).randomizeForMob(this, true));
         } else {
            this.tasks.addTask(4, this.meleeAttack);
            this.tasks.addTask(2, this.meleeAttack);
         }
      }
      this.reCalcProfession();
   }

   @Inject(method = "onUpdate", at = @At("RETURN"))
   public void onUpdate(CallbackInfo ci) {
      if (this.getWorld().isWorldServer() && this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_IS_BOOSTED) > 0) {
         if (this.effectCooldown <= 0) {
            int day = MinecraftServer.getServer().getOverworld().getWorld().getDayOfWorld();
            if (day > 128) {
               List<EntityPigZombie> nearbyZombie = this.worldObj.getEntitiesWithinAABB(EntityPigZombie.class, this.boundingBox.expand(16.0D, 8.0D, 16.0D));

               for (EntityPigZombie entityPigZombie : nearbyZombie) {
                  entityPigZombie.addPotionEffect(new PotionEffect(1, 40 + day / 32 * 10, this.getRNG()
                          .nextInt(Math.max((day - 96) / 96, 1)), false));
                  entityPigZombie.addPotionEffect(new PotionEffect(5, day / 32 * 10, this.getRNG()
                          .nextInt(Math.max((day - 128) / 96, 1)), false));
               }

               this.entityFX(EnumEntityFX.smoke_and_steam);
               this.effectCooldown = 30;
            }
         } else {
            --this.effectCooldown;
         }
      }

   }

   public boolean randomUseBow() {
      return this.rand.nextInt(10) > 8;
   }

   public void reCalcProfession() {
      this.tasks.removeTask(this.tasks.getTask(EntityAIArrowAttack.class));
      this.tasks.removeTask(this.tasks.getTask(EntityAIAttackOnCollide.class));
      ItemStack var1 = this.getHeldItemStack();
      if (var1 != null && var1.getItem() instanceof ItemBow) {
         this.tasks.addTask(4, this.arrowAttack);
         this.tasks.addTask(3, new EntityAISeekFiringPosition(this, 1.0F, true));
      } else {
         this.tasks.addTask(4, this.meleeAttack);
      }

   }


   public boolean canBeTargetTo(EntityLiving from) {
      return !(from instanceof EntityPigZombie);
   }
}
