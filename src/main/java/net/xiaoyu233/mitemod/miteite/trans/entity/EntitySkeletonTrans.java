package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.api.ITESkeleton;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySkeleton.class)
public abstract class EntitySkeletonTrans extends EntityMob implements IRangedAttackMob, ITESkeleton {
   private static final int DATA_OBJ_ID_SKELETON_TYPE = 13;
   private static final int WITHER_SKELETON_ID = 1;
   private static final int ARROW_SKELETON_ID = 0;
   private static final int MELEE_ATTACK_SKELETON_ID = 2;
   @Shadow
   private EntityAIArrowAttack aiArrowAttack;
   @Shadow
   private EntityAIAttackOnCollide aiAttackOnCollide;
   @Unique
   private int DATA_OBJ_ID_CAN_USE_FIRE_ARROW;
   @Unique
   private int DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW;
   @Unique
   private int DATA_OBJ_ID_COMPRESSED;
   @Unique
   protected ItemStack stowed_item_stack;
   @Unique
   private boolean forceMeleeAttack;
   @Unique
   private boolean willChangeWeapon;

   @Shadow
   protected void addRandomEquipment(){
   }

   public EntitySkeletonTrans(World par1World) {
      super(par1World);
   }


   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
      if (this.worldObj.isUnderworld() && this.worldObj.getDayOfOverworld() < 64) {
         MonsterUtil.addDefaultArmor(64, this, true);
      }
   }

   @Inject(method = "addRandomWeapon", at = @At("HEAD"), cancellable = true)
   public void addRandomWeapon(CallbackInfo ci) {
      int day_of_world = MinecraftServer.getServer().getOverworld().getDayOfWorld();
      if (this.getSkeletonType() == 2 && day_of_world >= 64) {
         if (this.getRNG().nextInt(Math.max(2, 20 - day_of_world / 48)) == 1) {
            super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, day_of_world >= 96));
         } else if (!this.getRNG().nextBoolean()) {
            super.setCurrentItemOrArmor(0, (new ItemStack(Item.daggerIron)).randomizeForMob(this, day_of_world >= 128));
         } else {
            super.setCurrentItemOrArmor(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, day_of_world >= 96));
         }

      } else if (this.getSkeletonType() == 2 && this.getRNG().nextInt(20) == 0 && day_of_world >= 10) {
         if (!this.getRNG().nextBoolean()) {
            if (day_of_world >= 20) {
               super.setCurrentItemOrArmor(0, (new ItemStack(this.getWeapon(day_of_world))).randomizeForMob(this, false));
            } else {
               super.setCurrentItemOrArmor(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, false));
            }
         } else {
            super.setCurrentItemOrArmor(0, (new ItemStack(Item.daggerRustedIron)).randomizeForMob(this, false));
         }

      } else {
         this.setCombatTask();
         super.setCurrentItemOrArmor(0, (new ItemStack(this.getSkeletonType() == 2 ? Item.clubWood : Item.bow)).randomizeForMob(this, true));
      }
      ci.cancel();
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
      if (this.getSkeletonType() == WITHER_SKELETON_ID) {
         this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 45D + (double)day / 16.0D);
         this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.25D);
         this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 13.0D + (double)day / 24.0D);
      } else {
         this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 15.0D + (double)day / 14.0D);
         this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.30000001192092896D);
         this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 8 + day / 24d);
      }

   }

   /**
    * @author XiaoYu233
    * @reason TODO Inject this later...
    */
   @Overwrite
   public void attackEntityWithRangedAttack(EntityLivingBase target, float par2) {
      EntityArrow center = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().difficultySetting * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
      this.processArrow(center, par2);
      if ((Configs.Entities.SKELETON_TRIPLE_SHOT.get()) && this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) > 0) {
         EntityArrow left = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().difficultySetting * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
         left.motionX *= 1.5D;
         EntityArrow right = new EntityArrow(this.getWorld(), this, target, 1.6F, (float)(14 - this.getWorld().difficultySetting * 4), this.isLongdead() ? Item.arrowAncientMetal : Item.arrowRustedIron, false);
         right.motionX /= 1.5D;
         this.processArrow(left, par2);
         this.getWorld().spawnEntityInWorld(left);
         this.processArrow(right, par2);
         this.getWorld().spawnEntityInWorld(right);
      }

      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.getWorld().spawnEntityInWorld(center);
   }

   @SoftOverride //ITEMob
   public float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f,0.25f);
   }

   @ModifyConstant(method = "getRandomSkeletonType", constant = @org.spongepowered.asm.mixin.injection.Constant(doubleValue = 0.25D))
   public double modifyNormalSkeletonMeleeAttackChance(double constant) {
      return 0.2D;
   }

   @Unique
   private Item getWeapon(int day){
      return Constant.SWORDS[Math.max(Math.min(day / 64,Constant.SWORDS.length - 1),0)];
   }

   @SoftOverride //ITELivingEntity
   public float getWeaponDamageBoost() {
      return 1.15f;
   }

   @Inject(method = "addRandomEquipment",at = @At("RETURN"))
   private void injectAddWeapon(CallbackInfo callbackInfo) {
      this.initStockedWeapon();
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(World world,CallbackInfo callbackInfo){
      this.forceMeleeAttack = (Configs.Entities.SKELETON_FORCE_MELEE_ATTACK.get()) && this.rand.nextInt(100) < 15;
   }

   @Inject(method = "onLivingUpdate",at = @At("RETURN"))
   public void injectLivingUpdate(CallbackInfo callbackInfo) {
      if (this.willChangeWeapon){
         if (this.stowed_item_stack != null && (this.getHeldItemStack() == null || this.getTicksExistedWithOffset() % 10 == 0)) {
            if (this.getHeldItemStack() == null) {
               this.swapHeldItemStackWithStowed();
            } else {
               Entity target = this.getTarget();
               if (target != null && this.canSeeTarget(true)) {
                  double distance = this.getDistanceToEntity(target);
                  if (this.isHoldingRangedWeapon()) {
                     if (distance < 5.0D) {
                        this.swapHeldItemStackWithStowed();
                     }
                  } else if (distance > 6.0D) {
                     this.swapHeldItemStackWithStowed();
                  }
               }
            }
         }
      }
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   private void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci){
      if (par1NBTTagCompound.hasKey("TripleShot") && par1NBTTagCompound.getBoolean("TripleShot")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW, (byte)1);
      }

      if (par1NBTTagCompound.hasKey("FireBow") && par1NBTTagCompound.getBoolean("FireBow")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW, (byte)1);
      }

      if (par1NBTTagCompound.hasKey("Compressed") && par1NBTTagCompound.getBoolean("Compressed")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_COMPRESSED, (byte)1);
      }
      if (par1NBTTagCompound.hasKey("stowed_item_stack")) {
         this.stowed_item_stack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("stowed_item_stack"));
      } else {
         this.stowed_item_stack = null;
      }
      if (par1NBTTagCompound.hasKey("willChangeWeapon")){
         this.willChangeWeapon = par1NBTTagCompound.getBoolean("willChangeWeapon");
      }

      this.setCombatTask();
   }

   @Unique
   public void initStockedWeapon(){
      this.willChangeWeapon = this.willChangeWeapon();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      if (this.getHeldItem() instanceof ItemBow) {
         this.stowed_item_stack = (new ItemStack(this.getWeapon(day))).randomizeForMob(this, true);
      }else if (this.getHeldItem() instanceof ItemSword){
         this.stowed_item_stack = (new ItemStack(this.getWeapon(day))).randomizeForMob(this, true);
      }
   }

   @Inject(method = "writeEntityToNBT",at = @At("RETURN"))
   private void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci){
      par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
      par1NBTTagCompound.setBoolean("TripleShot", this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW) != 0);
      par1NBTTagCompound.setBoolean("FireBow", this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW) != 0);
      par1NBTTagCompound.setBoolean("Compressed", this.isCompressed());
      if (this.stowed_item_stack != null) {
         NBTTagCompound compound = new NBTTagCompound();
         this.stowed_item_stack.writeToNBT(compound);
         par1NBTTagCompound.setCompoundTag("stowed_item_stack", compound);
      }
      par1NBTTagCompound.setBoolean("willChangeWeapon",this.willChangeWeapon);
   }

   @Inject(method = "entityInit", at = @At("RETURN"))
   protected void entityInit(CallbackInfo ci) {
      this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
      this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
      this.DATA_OBJ_ID_COMPRESSED = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
   }

   @Unique
   public boolean isHoldingRangedWeapon() {
      return this.getHeldItem() instanceof ItemBow;
   }

   @Shadow
   public int getSkeletonType() {
      return this.dataWatcher.getWatchableObjectByte(DATA_OBJ_ID_SKELETON_TYPE);
   }

   @Shadow
   public void setSkeletonType(int par1) {
      this.dataWatcher.updateObject(DATA_OBJ_ID_SKELETON_TYPE, (byte)par1);
      if (par1 == WITHER_SKELETON_ID) {
         this.setSize(0.72F, 2.34F);
      } else {
         this.setSize(0.6F, 1.8F);
      }

   }

   @Inject(method = "onDeath", at = @At("HEAD"))
   public void onDeath(DamageSource par1DamageSource, CallbackInfo ci) {
      if (!this.worldObj.isRemote && this.isCompressed()) {
         for(int integer = 0; integer < (Configs.Entities.COMPRESSED_SKELETON_EXPAND_COUNT.get()); ++integer) {
            EntitySkeleton skeleton = new EntitySkeleton(this.worldObj);
            skeleton.setPosition(this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ());
            skeleton.forced_skeleton_type = skeleton.getRandomSkeletonType(this.worldObj);
            skeleton.refreshDespawnCounter(-9600);
            this.worldObj.spawnEntityInWorld(skeleton);
            skeleton.onSpawnWithEgg(null);
            skeleton.setAttackTarget(this.getTarget());
            skeleton.entityFX(EnumEntityFX.summoned);
            int dayOfWorld = this.worldObj.getDayOfOverworld();
            if (dayOfWorld > 64) {
               skeleton.setCurrentItemOrArmor(1, new ItemStack(Constant.HELMETS[MathHelper.clamp_int(1,MonsterUtil.getRandomItemTier(this.rand, dayOfWorld),Constant.HELMETS.length - 1)]).randomizeForMob(skeleton,true));
            }
            int standTime = Configs.Entities.COMPRESSED_SKELETON_CRACK_STAND_TIME.get();
            skeleton.addPotionEffect(new PotionEffect(Potion.weakness.id,standTime,127,true));
            skeleton.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,standTime,127,true));
            skeleton.addPotionEffect(new PotionEffect(Potion.resistance.id,standTime,127,true));
         }
      }
   }

   @Inject(method = "onSpawnWithEgg", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityAITasks;addTask(ILnet/minecraft/EntityAIBase;)V", ordinal = 2, shift = At.Shift.AFTER))
   private void injectOnArrowSkeletonSpawn(EntityLivingData par1EntityLivingData, CallbackInfoReturnable<EntityLivingData> cir) {
      this.setSkeletonType(ARROW_SKELETON_ID);
      this.tasks.addTask(4, this.aiArrowAttack);
      this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW, (byte)(this.rand.nextInt(10) > 5 ? 1 : 0));
      this.dataWatcher.updateObject(this.DATA_OBJ_ID_CAN_USE_TRIPLE_ARROW, (byte)(this.rand.nextInt(100) > 85 ? 1 : 0));
   }

   @Inject(method = "onSpawnWithEgg", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntitySkeleton;setCurrentItemOrArmor(ILnet/minecraft/ItemStack;)V", ordinal = 0, shift = At.Shift.AFTER))
   private void injectOnWitherSkeletonSpawn(EntityLivingData par1EntityLivingData, CallbackInfoReturnable<EntityLivingData> cir) {
      this.setCurrentItemOrArmor(0, (new ItemStack(Item.swordAncientMetal)).setQuality(EnumQuality.poor).randomizeForMob(this, super.worldObj.getDayOfOverworld() > 160));
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(4.0D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, this.getEntityAttributeValue(SharedMonsterAttributes.maxHealth) * 2.0D);
      this.setHealth(24.0F);
   }

   @Inject(method = "onSpawnWithEgg", at = @At(value = "HEAD"))
   private void injectCreateCompressedSkeleton(EntityLivingData par1EntityLivingData, CallbackInfoReturnable<EntityLivingData> cir){
      if ((Configs.Entities.COMPRESSED_SKELETON.get())) {
         this.dataWatcher.updateObject(this.DATA_OBJ_ID_COMPRESSED, (byte)(this.rand.nextInt(100) < 3 ? 1 : 0));
      }
   }

   @Inject(method = "onSpawnWithEgg", at = @At(value = "RETURN"))
   private void injectUpdateCombatTask(EntityLivingData par1EntityLivingData, CallbackInfoReturnable<EntityLivingData> cir){
      this.setCombatTask();
   }

   @Unique
   private boolean isCompressed() {
      return this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_COMPRESSED) != 0;
   }

   @Shadow
   public boolean isLongdead() {
      return false;
   }

   @Unique
   public void setCombatTask() {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiArrowAttack);
      ItemStack var1 = this.getHeldItemStack();

      if (var1 != null && var1.getItem() instanceof ItemBow && !forceMeleeAttack) {
         this.tasks.addTask(4, this.aiArrowAttack);
         this.tasks.addTask(1, new EntityAISeekFiringPosition(this, 1.0F, true));
      } else {
         this.tasks.addTask(4, this.aiAttackOnCollide);
      }

   }

   @Unique
   public void swapHeldItemStackWithStowed() {
      ItemStack item_stack = this.stowed_item_stack;
      this.stowed_item_stack = this.getHeldItemStack();
      this.setHeldItemStack(item_stack);
   }

   @Unique
   private void processArrow(EntityArrow var3, float par2) {
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

      if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemStack()) > 0 || this.getSkeletonType() == 1 || this.isInFire() && this.getRNG().nextInt(3) == 0 || this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_ID_CAN_USE_FIRE_ARROW) > 0 && rawDay > 196) {
         var3.setFire(100);
      }

   }

   @Unique
   protected boolean willChangeWeapon(){
      return false;
   }

   @Inject(method = "setCurrentItemOrArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityMob;setCurrentItemOrArmor(ILnet/minecraft/ItemStack;)V"), cancellable = true)
   public void injectAlwaysReloadTasks(int par1, ItemStack par2ItemStack, CallbackInfo ci) {
      if (this.getHeldItemStack() != null) {
         this.setCombatTask();
      }
      ci.cancel();
   }
}
