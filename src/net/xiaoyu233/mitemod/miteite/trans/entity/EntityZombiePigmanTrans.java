package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(EntityPigZombie.class)
public class EntityZombiePigmanTrans extends EntityZombie implements IRangedEntity {
   @Shadow
   @Final
   public static final AttributeModifier field_110190_br;
   @Shadow
   @Final
   private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");

   static {
      field_110190_br = (new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.1D, 0)).setSaved(false);
   }

   private final PathfinderGoalArrowAttack arrowAttack = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
   private final PathfinderGoalMeleeAttack meleeAttack = new PathfinderGoalMeleeAttack(this, EntityPlayer.class, 1.0D, false);
   private int DATA_OBJ_ID_IS_BOOSTED;
   private int effectCooldown;

   public EntityZombiePigmanTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.setEntityAttribute(GenericAttributes.maxHealth, 50.0D + (double)day / 16.0D);
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.25D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 12.0D + (double)day / 20.0D);
      this.setEntityAttribute(EntityZombie.field_110186_bp, this.rand.nextDouble() * 0.10000000149011612D);
   }

   @Override
   public EntityDamageResult attackEntityAsMob(Entity target) {
      if (target != null && target.isEntityAlive() && rand.nextFloat() < Configs.Entities.NETHER_MOD_ATTACK_FIRE_CHANCE.get()){
         target.setFire(5);
      }
      return super.attackEntityAsMob(target);
   }

   public void attackEntityWithRangedAttack(EntityLiving entityLiving, float par2) {
      EntityArrow var3 = new EntityArrow(this.getWorld(), this, entityLiving, 1.6F, (float)(14 - this.getWorld().difficultySetting * 4), Item.arrowRustedIron, false);
      int rawDay = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      int day = Math.max(rawDay - 64, 0);
      int var4 = EnchantmentManager.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItemStack()) + 1;
      int var5 = (int)((double)EnchantmentManager.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItemStack()) + Math.min(1.0D + Math.floor((float)day / 48.0F), 5.0D));
      double damage = (double)(par2 * 2.0F) + this.getRNG().nextGaussian() * 0.0D + (double)((float)this.getWorld().difficultySetting * 0.11F);
      var3.setDamage(damage);
      if (var4 > 0) {
         var3.setDamage(var3.getDamage() + (double)var4 * 2.0D + 1.0D);
      }

      if (var5 > 0) {
         var3.setKnockbackStrength(var5);
      }

      if (EnchantmentManager.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemStack()) > 0 || this.isInFire() && this.getRNG().nextInt(3) == 0 || rawDay > 196) {
         var3.setFire(100);
      }

      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.getWorld().spawnEntityInWorld(var3);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.tasks.clear();
      this.tasks.addTask(1, new EntityAIWatchAnimal(this));
      this.tasks.addTask(0, new PathfinderGoalFloat(this));
      this.tasks.addTask(1, new PathfinderGoalBreakDoor(this));
      this.tasks.addTask(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
      this.tasks.addTask(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
      this.tasks.addTask(6, new PathfinderGoalRandomStroll(this, 1.0D));
      this.tasks.addTask(7, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new PathfinderGoalRandomLookaround(this));
      this.targetTasks.addTask(1, new PathfinderGoalHurtByTarget(this, true));
      this.targetTasks.addTask(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.targetTasks.addTask(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
      this.tasks.addTask(2, new EntityAIMoveToFoodItem(this, 1.0F, true));
      if (Configs.Entities.ZOMBIE_PIGMAN_ATTACK_ANIMAILS.get()){
         this.tasks.addTask(4, new PathfinderGoalMeleeAttack(this, EntityAnimal.class, 1.0D, true));
         this.targetTasks.addTask(3, new PathfinderGoalNearestAttackableTarget(this, EntityAnimal.class, 10, true));
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

   public GroupDataEntity onSpawnWithEgg(GroupDataEntity par1EntityLivingData) {
      super.onSpawnWithEgg(par1EntityLivingData);
      this.setVillager(false, 0);
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
      return par1EntityLivingData;
   }

   @Overwrite
   public void onUpdate() {
      super.onUpdate();
      if (this.getWorld().isWorldServer() && this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_IS_BOOSTED) > 0) {
         if (this.effectCooldown <= 0) {
            int day = MinecraftServer.F().getOverworld().getWorld().getDayOfOverworld();
            if (day > 128) {
               List<EntityPigZombie> nearbyZombie = this.worldObj.getEntitiesWithinAABB(EntityPigZombie.class, this.boundingBox.expand(16.0D, 8.0D, 16.0D));

               for (EntityPigZombie entityPigZombie : nearbyZombie) {
                  entityPigZombie.addPotionEffect(new MobEffect(1, 40 + day / 32 * 10, this.getRNG()
                          .nextInt(Math.max((day - 96) / 96, 1)), false));
                  entityPigZombie.addPotionEffect(new MobEffect(5, day / 32 * 10, this.getRNG()
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
      this.tasks.removeTask(this.tasks.getTask(PathfinderGoalArrowAttack.class));
      this.tasks.removeTask(this.tasks.getTask(PathfinderGoalMeleeAttack.class));
      ItemStack var1 = this.getHeldItemStack();
      if (var1 != null && var1.getItem() instanceof ItemBow) {
         this.tasks.addTask(4, this.arrowAttack);
         this.tasks.addTask(3, new EntityAISeekFiringPosition(this, 1.0F, true));
      } else {
         this.tasks.addTask(4, this.meleeAttack);
      }

   }

   @Override
   public boolean canBeTargetTo(EntityLiving from) {
      return !(from instanceof EntityPigZombie);
   }

   public void setHeldItemStack(ItemStack item_stack) {
      super.setHeldItemStack(item_stack);
   }

   public boolean willPickupAsValuable(ItemStack item_stack) {
      return item_stack.getItemSubtype() == 0 && item_stack.getItemDamage() == 0 && item_stack.hasMaterial(Material.gold);
   }
}
