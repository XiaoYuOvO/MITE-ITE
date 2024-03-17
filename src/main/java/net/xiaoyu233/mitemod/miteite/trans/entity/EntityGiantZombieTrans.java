package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITELivingEntity;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EntityGiantZombie.class)
public class EntityGiantZombieTrans extends EntityMob {
   @Unique
   private int spawnCounter;

   public EntityGiantZombieTrans(World var1) {
      super(var1);
   }

   @Override
   public boolean canBeKnockedBack() {
      return false;
   }

   @Unique
   private static void addDefaultArmor(int day_count, EntityLiving monster, boolean haveAll) {
      Random rand = monster.getRNG();
      if (rand.nextInt(3 - Math.min(day_count / 128, 2)) == 0 || day_count > 365 || haveAll) {
         int minTier = rand.nextInt(2 + Math.min(day_count / 64, 6)) + 1;

         for(int index = 4; index > 0; --index) {
            if (rand.nextInt(5 - Math.min(day_count / 32, 4)) == 0 || day_count > 192 || haveAll) {
               monster.setCurrentItemOrArmor(
                       index,
                       new ItemStack(
                               Constant.ARMORS[index - 1][Math.min(
                                       getRandomItemTier(rand, Math.min(10, day_count / 16), minTier, day_count) + (day_count > 365 ? 1 : 0),
                                       Constant.ARMORS[index - 1].length - 1
                               )]
                       )
                               .randomizeForMob(monster, day_count > 64)
               );
            }
         }
      }
   }

   @Unique
   private static int getRandomItemTier(Random random, int maxTier, int minTier, int dayCount) {
      int now = minTier;
      while (now < maxTier && random.nextInt(Math.max(2 * now + 1 - dayCount / 12, 1)) == 0) {
         ++now;
      }

      return now;
   }

   @Override
   protected void addRandomArmor() {
      int hour = this.getWorld().getHourOfDay();
      int day = this.getWorld().getDayOfOverworld();
      if (day > 32 && ((day % 2 == 0 || day > 64) && hour >= 18 || ((day - 1) % 2 == 0 || day > 64) && hour <= 6)) {
         this.addPotionEffect(new PotionEffect(1, 999999, this.getRNG().nextInt(Math.max((day - 32) / 96, 1)), true));
         Random rand = this.getRNG();
         if (rand.nextInt(5) == 0) {
            this.addPotionEffect(new PotionEffect(5, 999999, this.getRNG().nextInt(Math.max((day - 32) / 128, 1)), true));
         }

         addDefaultArmor(day, this, false);
      } else if (day > 128) {
         Random rand = this.getRNG();
         if (rand.nextInt(4) < (day - 96) / 32) {
            this.addPotionEffect(new PotionEffect(1, 999999, this.getRNG().nextInt(Math.max((day - 32) / 96, 1)), true));
         }

         if (rand.nextInt(5) < (day - 96) / 32) {
            this.addPotionEffect(new PotionEffect(5, 999999, this.getRNG().nextInt(Math.max((day - 32) / 128, 1)), true));
         }

         addDefaultArmor(day, this, false);
      }
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(25.0D + (double)day / 16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(100.0D + (double)day / 16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
   }

   @Override
   public boolean considerFleeing() {
      return false;
   }

   @Override
   protected void dropEquipment(boolean recently_hit_by_player, int par2) {
      for(int var3 = 0; var3 < this.getLastActiveItems().length; ++var3) {
         ItemStack var4 = this.getCurrentItemOrArmor(var3);
         if (var4 != null
                 && (!var4.isItemStackDamageable() || ((ITELivingEntity) this).getPickedUpAHeldItemArray()[var3] && var4.getRemainingDurability() > var4.getMaxDamage() / 4)) {
            this.dropItemStack(var4, 0.0F);
            this.setWornItem(var3, null);
         }
      }
   }

   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player) {
         if (this.getWorld().getDayOfOverworld() < 128) {
            this.dropItemStack(new ItemStack(Item.ancientMetalNugget, 4 + this.rand.nextInt(2)));
         } else {
            this.dropItemStack(new ItemStack(Item.ingotMithril, 1));
         }
      }

      super.dropFewItems(recently_hit_by_player, damage_source);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.1D + (double) this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld()
                 .getDayOfOverworld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Override
   protected EntityPlayer findPlayerToAttack(float max_distance) {
      EntityPlayer target = super.findPlayerToAttack(max_distance * 2.0F);
      if (target != null) {
         this.setAttackTarget(target);
      }

      return target;
   }

   public boolean getCanSpawnHere(boolean perform_light_check) {
      Vec3 pos = this.getFootPos();
      return !this.getWorld().anySolidBlockIn(this.boundingBox.addCoord(0.0, 1.0, 0.0))
              && this.getWorld().getClosestEntityLivingBase(this, new Class[]{this.getClass()}, 96.0, false, false) == null
              && this.getWorld().getBlock(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ()) != Block.waterMoving;
   }
   @Override
   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   @Override
   protected String getDeathSound() {
      return "mob.zombie.death";
   }

   @Override
   protected String getHurtSound() {
      return "mob.zombie.hurt";
   }

   @Override
   protected String getLivingSound() {
      return "mob.zombie.say";
   }

   @Override
   public float getReach() {
      return super.getReach() * 2.5F;
   }

   @Inject(
           method = {"<init>(Lnet/minecraft/World;)V"},
           at = {@At("RETURN")}
   )
   private void injectAI(CallbackInfo c) {
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.1, true));
      this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityAnimal.class, 1.0, true));
      this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.tasks.addTask(6, new EntityAIWander(this, 1.0));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
      this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 10, true));
      this.setSize(3.66F, 10.799999F);
   }

   @Override
   protected boolean isAIEnabled() {
      return true;
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (!this.worldObj.isRemote) {
         if (this.spawnCounter < (Configs.Entities.GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN.get())) {
            ++this.spawnCounter;
         } else {
            EntityZombie zombie = new EntityZombie(this.worldObj);
            zombie.setPosition(this.posX, this.posY, this.posZ);
            zombie.refreshDespawnCounter(-9600);
            this.worldObj.spawnEntityInWorld(zombie);
            zombie.onSpawnWithEgg(null);
            zombie.setAttackTarget(this.getTarget());
            zombie.entityFX(EnumEntityFX.summoned);
            this.spawnCounter = 0;
         }
      }

   }

   @Override
   protected void playStepSound(int par1, int par2, int par3, int par4) {
      this.makeSound("mob.zombie.step", 0.15F, 1.0F);
   }
}
