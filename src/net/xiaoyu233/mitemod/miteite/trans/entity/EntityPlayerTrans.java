package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.network.SPacketCraftingBoost;
import net.xiaoyu233.mitemod.miteite.network.SPacketOverlayMessage;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerTrans extends EntityLiving implements ICommandListener {
   private final Map<Entity, Integer> attackCountMap = new HashMap();
   @Shadow
   public ChunkCoordinates bed_location;
   @Shadow
   public long block_placement_tick;
   @Shadow
   public boolean collided_with_gelatinous_cube;
   @Shadow
   public EnumConsciousState conscious_state;
   @Shadow
   public int countdown_to_mark_all_nearby_chunks_for_render_update;
   @Shadow public PlayerInventory inventory;
   @Shadow
   public HashMap stats;
   @Shadow
   public boolean suppress_next_arm_swing;
   @Shadow
   public float vision_dimming;
   @Shadow
   List tentative_bounding_boxes;
   private float craftingBoostFactor;
   private int craftingBoostTimer;
   private BlockPos currentEffectedBeaconPos;
   private int emergencyCooldown;
   private int inRainCounter;
   private int netherDebuffTime;
   private int underworldDebuffTime;
   private int underworldRandomTeleportTime;
   private volatile boolean waitForItemSync;
   public EntityPlayerTrans(World par1World, String par2Str) {
      super(par1World);
   }

   @Overwrite
   public static int getHealthLimit(int level) {
      return Math.max(Math.min(6 + level / 5 * 2, 40), 6);
   }

   private void activeEmergency() {
      this.addPotionEffect(new MobEffect(11, 60, 1));
      this.entityFX(EnumEntityFX.smoke_and_steam);
      this.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
      this.makeSound("random.anvil_land", 0.4F, 0.4F);
      this.emergencyCooldown = (Configs.Item.Enchantment.EMERGENCY_COOLDOWN.get());
   }

   @Shadow
   public void addHungerServerSide(float hunger) {
   }

   @Shadow public abstract void addStat(Statistic par1StatBase, int par2);

   @Overwrite
   public void attackTargetEntityWithCurrentItem(Entity target) {
      if (!this.isImmuneByGrace() && target.canAttackWithItem()) {
         boolean critical = this.willDeliverCriticalStrike();
         float critBouns = 0.0F;
         if (EnchantmentManager.hasEnchantment(this.getHeldItemStack(), Enchantments.CRIT)) {
            int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, this.getHeldItemStack());
            critical = this.rand.nextInt(10) < (Configs.Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL.get()) * critLevel;
            if (critical) {
               critBouns = (float)critLevel * (Configs.Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL.get())
                       .floatValue();
            }
         }

         float damage = critBouns + this.calcRawMeleeDamageVs(target, critical, this.isSuspendedInLiquid());
         if (damage <= 0.0F) {
            return;
         }

         int knockback = 0;
         if (target instanceof EntityLiving) {
            knockback += EnchantmentManager.getKnockbackModifier(this, (EntityLiving)target);
         }

         if (this.isSprinting()) {
            ++knockback;
         }

         boolean was_set_on_fire = false;
         int fire_aspect = EnchantmentManager.getFireAspectModifier(this);
         if (target instanceof EntityLiving && fire_aspect > 0 && !target.isBurning()) {
            was_set_on_fire = true;
            target.setFire(1);
         }

         if (this.onServer() && target instanceof EntityLiving) {
            EntityLiving entity_living_base = (EntityLiving)target;
            ItemStack item_stack_to_drop = entity_living_base.getHeldItemStack();
            if (item_stack_to_drop != null && this.rand.nextFloat() < EnchantmentManager.getEnchantmentLevelFraction(Enchantment.disarming, this.getHeldItemStack()) && entity_living_base instanceof EntityInsentient) {
               EntityInsentient entity_living = (EntityInsentient)entity_living_base;
               if (entity_living.canBeDisarmed()) {
                  entity_living.dropItemStack(item_stack_to_drop, entity_living.height / 2.0F);
                  entity_living.clearMatchingEquipmentSlot(item_stack_to_drop);
                  entity_living.ticks_disarmed = 40;
               }
            }
         }

         EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this).setFireAspectP(fire_aspect > 0), damage));
         boolean target_was_harmed = result != null && result.entityWasNegativelyAffected();
         target.onMeleeAttacked(this, result);
         if (target_was_harmed) {
            if (target instanceof EntityLiving) {
               int stunning = EnchantmentManager.getStunModifier(this, (EntityLiving)target);
               if ((double)stunning > Math.random() * 10.0D) {
                  ((EntityLiving)target).addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, stunning * 50, stunning * 5));
               }

               this.heal((float)EnchantmentManager.getVampiricTransfer(this, (EntityLiving)target, damage), EnumEntityFX.vampiric_gain);
            }

            if (knockback > 0) {
               target.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * (float)knockback * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * (float)knockback * 0.5F);
               this.motionX *= 0.6D;
               this.motionZ *= 0.6D;
               this.setSprinting(false);
            }

            if (critical) {
               this.onCriticalHit(target);
            }

            if (target instanceof EntityLiving && EnchantmentWeaponDamage.getDamageModifiers(this.getHeldItemStack(), (EntityLiving)target) > 0.0F) {
               this.onEnchantmentCritical(target);
            }

            if (damage >= 18.0F) {
               this.triggerAchievement(AchievementList.overkill);
            }

            this.setLastAttackTarget(target);
            if (target instanceof EntityLiving) {
               if (this.worldObj.isRemote) {
                  System.out.println("EntityPlayer.attackTargetEntityWithCurrentItem() is calling EnchantmentThorns.func_92096_a() on client");
                  Minecraft.temp_debug = "player";
               }

               EnchantmentThorns.func_92096_a(this, (EntityLiving)target, this.rand);
            }
         }

         ItemStack held_item_stack = this.getHeldItemStack();
         Object var10 = target;
         if (target instanceof EntityComplexPart) {
            IComplex var11 = ((EntityComplexPart)target).entityDragonObj;
            if (var11 != null && var11 instanceof EntityLiving) {
               var10 = var11;
            }
         }

         if (target_was_harmed && held_item_stack != null && var10 instanceof EntityLiving) {
            held_item_stack.hitEntity((EntityLiving)var10, ReflectHelper.dyCast(this));
         }

         if (target instanceof EntityLiving) {
            this.addStat(StatisticList.damageDealtStat, Math.round(damage * 10.0F));
            if (fire_aspect > 0 && target_was_harmed) {
               target.setFire(fire_aspect * 4);
            } else if (was_set_on_fire) {
               target.extinguish();
            }
         }

         if (this.onServer()) {
            this.addHungerServerSide(0.3F * EnchantmentManager.getEnduranceModifier(this));
         }
      }

   }

   @Shadow
   protected abstract float calcRawMeleeDamageVs(Entity var1, boolean var2, boolean var3);

   protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {
      if (this.emergencyCooldown <= 0 && result.entityWasDestroyed()) {
         ItemStack[] var3 = this.getWornItems();

         for (ItemStack wornItem : var3) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               if (result.entityWasDestroyed()) {
                  result.setEntity_was_destroyed(false);
                  this.setHealth(this.getMaxHealth() * 0.2F, true, this.getHealFX());
               }

               this.activeEmergency();
               break;
            }
         }
      }

   }

   @Overwrite
   private void checkForArmorAchievements() {
      boolean wearing_leather = false;
      boolean wearing_full_suit_plate = true;
      boolean wearing_full_suit_adamantium_plate = true;
      boolean wearing_full_suit_vibranium_plate = true;

      for(int i = 0; i < 4; ++i) {
         if (this.inventory.armorInventory[i] != null && this.inventory.armorInventory[i].getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)this.inventory.armorInventory[i].getItem();
            Material material = armor.getArmorMaterial();
            if (material == Material.leather) {
               wearing_leather = true;
            }

            if (material != Material.copper && material != Material.silver && material != Material.gold && material != Material.iron && material != Material.mithril && material != Material.adamantium && material != Material.ancient_metal) {
               wearing_full_suit_plate = false;
            }

            if (material != Material.adamantium) {
               wearing_full_suit_adamantium_plate = false;
            }

            if (material != Materials.vibranium) {
               wearing_full_suit_vibranium_plate = false;
            }
         } else {
            wearing_full_suit_plate = false;
            wearing_full_suit_adamantium_plate = false;
            wearing_full_suit_vibranium_plate = false;
         }
      }

      if (wearing_leather) {
         this.triggerAchievement(AchievementList.wearLeather);
      }

      if (wearing_full_suit_plate) {
         this.triggerAchievement(AchievementList.wearAllPlateArmor);
      }

      if (wearing_full_suit_adamantium_plate) {
         this.triggerAchievement(AchievementList.wearAllAdamantiumPlateArmor);
      }

      if (wearing_full_suit_vibranium_plate) {
         this.triggerAchievement(Achievements.wearAllVibraniumPlateArmor);
      }

   }

   @Shadow
   public abstract void displayGUIAnvil(int x, int y, int z);

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
   }

   @Shadow
   protected abstract void fall(float par1);
   
   @Shadow public abstract float getAIMoveSpeed();

//   @Overwrite
//   public EntityDamageResult attackEntityFrom(Damage damage) {
//      if (this.ac < 1000 && Damage.wasCausedByPlayer(damage) && this.isWithinTournamentSafeZone()) {
//         return null;
//      } else if (this.bG.disableDamage && !damage.canHarmInCreative()) {
//         return null;
//      } else {
//         if (this.inBed()) {
//            this.wakeUpPlayer(true, damage.getResponsibleEntityP());
//         }
//
//         if (damage.isExplosion()) {
//            damage.scaleAmount(1.5F);
//         }
//
//
//         //End of injection
//
//         return entityDamageResult;
//      }
//   }

   public float getCraftingBoostFactor() {
      return this.craftingBoostFactor;
   }

   @Shadow
   public ItemStack getHeldItemStack() {
      return null;
   }

   @Shadow
   public void setHeldItemStack(ItemStack itemStack) {
   }

   @Shadow
   private float getLevelModifier(EnumLevelBonus harvesting) {
      return 0.0F;
   }

   @Overwrite
   public final float getReach(Block block, int metadata) {
      if (this.hasExtendedReach()) {
         return 5.0F;
      } else {
         float block_reach = 2.75F;
         ItemStack item_stack = this.getHeldItemStack();
         int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
         return item_stack == null ? block_reach : block_reach + item_stack.getItem().getReachBonus(block, metadata) + (float)enchantmentLevel * 0.25F;
      }
   }

   @Overwrite
   public float getReach(EnumEntityReachContext context, Entity entity) {
      if (this.hasExtendedReach()) {
         return 5.0F;
      } else {
         float elevation_difference = (float)(this.posY - (double)this.yOffset - (entity.posY - (double)entity.yOffset));
         float height_advantage;
         if (elevation_difference < -0.5F) {
            height_advantage = (elevation_difference + 0.5F) * 0.5F;
            if (height_advantage < -1.0F) {
               height_advantage = -1.0F;
            }
         } else if (elevation_difference > 0.5F) {
            height_advantage = (elevation_difference - 0.5F) * 0.5F;
            if (height_advantage > 1.0F) {
               height_advantage = 1.0F;
            }
         } else {
            height_advantage = 0.0F;
         }

         ItemStack item_stack = this.getHeldItemStack();
         int enchantmentLevel;
         if (context == EnumEntityReachContext.FOR_MELEE_ATTACK) {
            enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
            return entity.adjustPlayerReachForAttacking(ReflectHelper.dyCast(this), 1.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.getItem().getReachBonus())) + (float)enchantmentLevel * 0.25F;
         } else if (context == EnumEntityReachContext.FOR_INTERACTION) {
            enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
            return entity.adjustPlayerReachForInteraction(ReflectHelper.dyCast(this), 2.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.getItem().getReachBonus(entity)) + (float)enchantmentLevel * 0.25F);
         } else {
            Minecraft.setErrorMessage("getReach: invalid context");
            return 0.0F;
         }
      }
   }

   public int[] getRunegateDestinationCoords(WorldServer world, int x, int y, int z) {
      int seed = this.rand.nextInt();
      BlockRunestone block_runestone = Block.runestoneMithril;
      int chunk_z;
      int runegate_domain_radius;
      if (seed == 0) {
         x = 0;
         z = 0;
      } else {
         Random random = new Random(seed);

         for(chunk_z = 0; chunk_z < 4; ++chunk_z) {
            runegate_domain_radius = world.getRunegateDomainRadius(block_runestone == Block.runestoneAdamantium ? Material.adamantium : Material.mithril);
            x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;

            for(z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius; block_runestone == Block.runestoneAdamantium && WorldServer.getDistanceFromDeltas(x, z) < (double)(runegate_domain_radius / 2); z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius) {
               x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;
            }

            if (world.getBiomeGenForCoords(x, z) != BiomeBase.ocean) {
               break;
            }
         }
      }

      int chunk_x = x >> 4;
      chunk_z = z >> 4;

      for(runegate_domain_radius = -1; runegate_domain_radius <= 1; ++runegate_domain_radius) {
         for(int dz = -1; dz <= 1; ++dz) {
            world.getChunkProvider().provideChunk(chunk_x + runegate_domain_radius, chunk_z + dz);
         }
      }

      if (world.isTheNether()) {
         y = 0;

         while(true) {
            ++y;
            if (y >= 254) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               ++y;
               if (world.isAirOrPassableBlock(x, y, z, false) && !world.isAirOrPassableBlock(x, y - 2, z, false) && !world.isLavaBlock(x, y - 2, z) && !world.isLavaBlock(x, y - 1, z)) {
                  return new int[]{x, y - 1, z};
               }
            }
         }
      } else if (world.isUnderworld()) {
         y = 254;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (BlockPortal.isGoodSpotForPlayerToAppearAt(world, x, y, z)) {
               return new int[]{x, y, z};
            }
         }
      } else {
         y = 256;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               --y;
               if (world.isAirOrPassableBlock(x, y, z, false)) {
                  while(y > 0 && world.isAirOrPassableBlock(x, y - 1, z, false)) {
                     --y;
                  }

                  if (y == 0) {
                     y = 64;
                  }

                  return new int[]{x, y, z};
               }
            }
         }
      }

      if (!world.isAirOrPassableBlock(x, 64, z, true)) {
         world.setBlockToAir(x, 64, z);
      }

      if (!world.isAirOrPassableBlock(x, 65, z, true)) {
         world.setBlockToAir(x, 65, z);
      }

      return new int[]{x, 64, z};
   }

   @Shadow
   public ItemStack[] getWornItems() {
      return new ItemStack[0];
   }

   @Shadow
   protected abstract boolean hasExtendedReach();

   @Shadow
   private boolean hasFoodEnergy() {
      return false;
   }

   private void initiateRunegateTeleport(WorldServer world, int x, int y, int z, ServerPlayer player) {
      int[] runegate_destination_coords = this.getRunegateDestinationCoords(world, x, y, z);
      player.runegate_destination_coords = runegate_destination_coords;
      player.playerNetServerHandler.sendPacket(new Packet85SimpleSignal(EnumSignal.runegate_start));
      player.setPositionAndUpdate(runegate_destination_coords[0], runegate_destination_coords[1], runegate_destination_coords[2]);
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   public void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci) {
      this.underworldRandomTeleportTime = par1NBTTagCompound.getInteger("UnderWorldTeleportTime");
      this.underworldDebuffTime = par1NBTTagCompound.getInteger("UnderWorldDebuffTime");
      this.netherDebuffTime = par1NBTTagCompound.getInteger("NetherDebuffTime");
      this.inRainCounter = par1NBTTagCompound.getInteger("InRainCounter");
      this.emergencyCooldown = par1NBTTagCompound.getInteger("EmergencyCooldown");
      this.vision_dimming = par1NBTTagCompound.getFloat("vision_dimming");
      if (par1NBTTagCompound.hasKey("AttackCountMap")) {
         NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackCountMap");
         int count = attackCountMap.tagCount();

         for(int i = 0; i < count; ++i) {
            NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
            Entity attacker = this.getWorldServer().getEntityByID(a.getInteger("Attacker"));
            if (attacker != null) {
               this.attackCountMap.put(attacker, a.getInteger("Count"));
            }
         }
      }

   }

   @Inject(method = "onLivingUpdate",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityLiving;onLivingUpdate()V",
                   shift = At.Shift.AFTER))
   private void injectTick(CallbackInfo c){
      if (!this.worldObj.isRemote) {
         if (this.craftingBoostTimer > 0) {
            --this.craftingBoostTimer;
         } else if (this.craftingBoostTimer == 0) {
            this.craftingBoostFactor = 0.0F;
            this.sendPacket(new SPacketCraftingBoost((float)this.craftingBoostTimer));
            this.craftingBoostTimer = -1;
         }
         if (this.isInRain()) {
            if (this.inRainCounter < (Configs.GameMechanics.IN_RAIN_DEBUFF_TIME.get())) {
               ++this.inRainCounter;
            } else {
               this.addPotionEffect(new MobEffect(18, 3600, 0));
            }
         } else if (this.inRainCounter > 0) {
            --this.inRainCounter;
         }

         int debuff_time;
         if (!this.worldObj.isUnderworld()) {
            if (this.worldObj.isTheNether()) {
               if ((Configs.GameMechanics.Nether.NETHER_DEBUFF.get())) {
                  debuff_time = (Configs.GameMechanics.Nether.NETHER_DEBUFF_TIME.get());
                  if (this.netherDebuffTime > debuff_time) {
                     this.addPotionEffect(new MobEffect(4, 3600, 1));
                  } else if (this.netherDebuffTime == debuff_time) {
                     this.sendPacket(new SPacketOverlayMessage("§n---你在下界中感到疲惫---", EnumChatFormat.DARK_RED.rgb, 200));
                  }
               }
            } else if (this.netherDebuffTime > 0) {
               --this.netherDebuffTime;
            } else if (this.netherDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地狱的疲惫中恢复---", EnumChatFormat.DARK_GREEN.rgb, 200));
            }

            if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }

            if (this.underworldDebuffTime > 0) {
               --this.underworldDebuffTime;
            } else if (this.underworldDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地底世界的疲惫中恢复---", EnumChatFormat.DARK_GREEN.rgb, 200));
            }
         } else {
            if (Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF.get()) {
               ++this.underworldDebuffTime;
               debuff_time = Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD1.get();
               int period2 = Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD2.get();
               int period3 = Configs.GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD3.get();
               if (this.underworldDebuffTime > debuff_time && this.underworldDebuffTime < period2) {
                  if (this.underworldDebuffTime == debuff_time + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到有些疲惫---", EnumChatFormat.GRAY.rgb, 400));
                  }

                  this.addPotionEffect(new MobEffect(2, 1200, 0));
               } else if (this.underworldDebuffTime > period2 && this.underworldDebuffTime < period3) {
                  if (this.underworldDebuffTime == period2 + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到更加疲惫---", EnumChatFormat.YELLOW.rgb, 400));
                  }

                  this.addPotionEffect(new MobEffect(2, 2400, 1));
               } else if (this.underworldDebuffTime > period3) {
                  if (this.underworldDebuffTime == period3 + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l§n---你在地底世界中感到非常疲惫---", EnumChatFormat.DARK_RED.rgb, 400));
                  }

                  this.addPotionEffect(new MobEffect(2, 3600, 2));
               }
            }

            if (Configs.GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT.get()) {
               double randomTeleportTime = Configs.GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT_TIME.get();
               ++this.underworldRandomTeleportTime;
               double timeToTeleport = randomTeleportTime - (double)this.underworldRandomTeleportTime;
               if (timeToTeleport == 1200.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于一分钟后被随机传送,请做好准备!!!---", EnumChatFormat.YELLOW.rgb, 400));
               }

               if (timeToTeleport == 6000.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于五分钟后被随机传送,请做好准备!!!---", EnumChatFormat.DARK_AQUA.rgb, 400));
               }

               if (timeToTeleport <= 200.0D && this.underworldRandomTeleportTime % 20 == 0) {
                  this.sendPacket(new SPacketOverlayMessage("§l§n!!!你将于" + (int)timeToTeleport / 20 + "秒后被随机传送!!!", EnumChatFormat.RED.rgb, 200));
               }

               if ((double)this.underworldRandomTeleportTime > randomTeleportTime) {
                  if (ReflectHelper.dyCast(EntityPlayer.class, this) instanceof EntityPlayer) {
                     this.initiateRunegateTeleport(this.worldObj.getAsWorldServer(), this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ(), ReflectHelper.dyCast(this));
                  }

                  this.underworldRandomTeleportTime = 0;
               }
            } else if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }
         }

         if (this.emergencyCooldown > 0) {
            --this.emergencyCooldown;
         }
      }
   }

   @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
   public void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callback) {
      par1NBTTagCompound.setInteger("UnderWorldTeleportTime", this.underworldRandomTeleportTime);
      par1NBTTagCompound.setInteger("UnderWorldDebuffTime", this.underworldDebuffTime);
      par1NBTTagCompound.setInteger("NetherDebuffTime", this.netherDebuffTime);
      par1NBTTagCompound.setInteger("InRainCounter", this.inRainCounter);
      par1NBTTagCompound.setInteger("EmergencyCooldown", this.emergencyCooldown);
      par1NBTTagCompound.setFloat("vision_dimming", this.vision_dimming);
      NBTTagList nbtTagList = new NBTTagList();
      for (Entry<Entity, Integer> integerEntry : this.attackCountMap.entrySet()) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("Attacker", ( integerEntry).getKey().entityId);
         compound.setInteger("Count", (integerEntry).getValue());
         nbtTagList.appendTag(compound);
      }

      par1NBTTagCompound.setTag("AttackCountMap", nbtTagList);
   }

   @Shadow
   protected abstract boolean isImmuneByGrace();

   @Shadow
   public boolean isWearing(ItemStack itemStack) {
      return false;
   }

   public void itemsSynced() {
      this.waitForItemSync = false;
   }

   @Override
   public boolean knockBack(Entity attacker, float amount) {
      ItemStack[] itemStacks = this.getWornItems();
      int i = 0;

      for(int itemStacksLength = itemStacks.length; i < itemStacksLength; ++i) {
         ItemStack stack = itemStacks[i];
         if (stack != null) {
            amount *= Math.max(1.0F - ArmorModifierTypes.STEADY_MODIFIER.getModifierValue(stack.stackTagCompound), 0.0F);
         }
      }

      return super.knockBack(attacker, amount);
   }

   @Shadow
   protected abstract void onCriticalHit(Entity target);

   @Shadow
   protected abstract void onEnchantmentCritical(Entity target);

//   @Shadow
   //Seems like nothing need to be changed
//   public float getCurrentPlayerStrVsBlock(int x, int y, int z, boolean apply_held_item) {
//      Block block = Block.blocksList[super.worldObj.getBlockId(x, y, z)];
//      if (block == null) {
//         return 0.0F;
//      } else {
//         float block_hardness = super.worldObj.getBlockHardness(x, y, z);
//         if (block_hardness == 0.0F) {
//            return 1.0F;
//         } else {
//            float min_str_vs_block = -3.4028235E38F;
//            Item held_item = super.getHeldItem();
//            float str_vs_block;
//            if (block.isPortable(super.worldObj, this, x, y, z)) {
//               str_vs_block = min_str_vs_block = 4.0F * block_hardness;
//            } else {
//               int metadata;
//               if (apply_held_item && held_item != null) {
//                  metadata = super.worldObj.getBlockMetadata(x, y, z);
//                  str_vs_block = held_item.getStrVsBlock(block, metadata, this.getHeldItemStack(), (ServerPlayer)ReflectHelper.dyCast(this));
//                  if (str_vs_block < 1.0F) {
//                     return this.getCurrentPlayerStrVsBlock(x, y, z, false);
//                  }
//
//                  int var4 = EnchantmentManager.getEfficiencyModifier(this);
//                  if (var4 > 0) {
//                     float var6 = (float)(var4 * var4 + 1);
//                     str_vs_block += var6;
//                  }
//               } else {
//                  metadata = super.worldObj.getBlockMetadata(x, y, z);
//                  if (block.blockMaterial.requiresTool(block, metadata)) {
//                     str_vs_block = 0.0F;
//                  } else {
//                     str_vs_block = 1.0F;
//                  }
//               }
//            }
//
//            if (block == Block.web) {
//               boolean decrease_strength = true;
//               if (apply_held_item && held_item != null && held_item.isTool() && held_item.getAsTool().isEffectiveAgainstBlock(block, 0)) {
//                  decrease_strength = false;
//               }
//
//               if (decrease_strength) {
//                  str_vs_block *= 0.2F;
//               }
//            }
//
//            if (super.isPotionActive(MobEffectList.digSpeed)) {
//               str_vs_block *= 1.0F + (float)(super.getActivePotionEffect(MobEffectList.digSpeed).getAmplifier() + 1) * 0.2F;
//            }
//
//            if (super.isPotionActive(MobEffectList.digSlowdown)) {
//               str_vs_block *= 1.0F - (float)(super.getActivePotionEffect(MobEffectList.digSlowdown).getAmplifier() + 1) * 0.2F;
//            }
//
//            if (super.isInsideOfMaterial(Material.water) && !EnchantmentManager.getAquaAffinityModifier(ReflectHelper.dyCast(this))) {
//               str_vs_block /= 5.0F;
//            }
//
//            if (!super.onGround) {
//               str_vs_block /= 5.0F;
//            }
//
//            if (!this.hasFoodEnergy()) {
//               str_vs_block /= 5.0F;
//            }
//
//            str_vs_block *= 1.0F + this.getLevelModifier(EnumLevelBonus.HARVESTING);
//            return Math.max(str_vs_block, min_str_vs_block);
//         }
//      }
//   }

   @Redirect(method = "attackEntityFrom",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult redirectEntityAttack(EntityLiving caller,Damage damage){
      double factor = (Configs.GameMechanics.STEPPED_MOB_DAMAGE_FACTOR.get());
      if (factor != 0.0D) {
         Entity responsibleEntity = damage.getSource().getResponsibleEntity();
         if (responsibleEntity != null && !(responsibleEntity instanceof EntityEnderDragon)) {
            if (this.attackCountMap.containsKey(responsibleEntity)) {
               this.attackCountMap.put(responsibleEntity, this.attackCountMap.get(responsibleEntity) + 1);
               damage.scaleAmount(1.0F + (float) this.attackCountMap.get(responsibleEntity) * (float)factor);
            } else {
               this.attackCountMap.put(responsibleEntity, 1);
            }
         }
      }
      EntityDamageResult entityDamageResult = super.attackEntityFrom(damage);
      if (entityDamageResult != null && this.emergencyCooldown <= 0 && (double)this.getHealthFraction() <= 0.3D && !entityDamageResult.entityWasDestroyed()) {
         ItemStack[] var5 = this.getWornItems();

         for (ItemStack wornItem : var5) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               this.activeEmergency();
               break;
            }
         }
      }
      return entityDamageResult;
   }

   @Shadow
   public void sendPacket(Packet packet) {
   }

   public void setCraftingBoostFactor(float craftingBoostFactor, @Nullable BlockPos currentEffectedBeaconPos) {
      if (!this.worldObj.isRemote) {
         float result = 0.0F;
         if (currentEffectedBeaconPos != null) {
            if (currentEffectedBeaconPos.equals(this.currentEffectedBeaconPos)) {
               result = craftingBoostFactor;
            } else {
               result = Math.max(craftingBoostFactor, this.craftingBoostFactor);
               if (result != this.craftingBoostFactor) {
                  this.currentEffectedBeaconPos = currentEffectedBeaconPos;
               }
            }
         }

         if (result != this.craftingBoostFactor) {
            this.craftingBoostFactor = result;
            this.sendPacket(new SPacketCraftingBoost(this.craftingBoostFactor));
         }
      } else {
         this.craftingBoostFactor = craftingBoostFactor;
      }

   }

   public void setCraftingBoostTimer(int craftingBoostTimer) {
      this.craftingBoostTimer = craftingBoostTimer;
   }

   @Shadow
   public boolean setWornItem(int i, ItemStack itemStack) {
      return false;
   }

   public void syncItemsAndWait() {
      this.sendPacket(new CPacketSyncItems());
      this.waitForItemSync = true;

      while(this.waitForItemSync) {
      }

   }

   @Shadow
   public abstract void triggerAchievement(Statistic par1StatBase);

   @Shadow
   public void wakeUpPlayer(boolean get_out_of_bed, Entity entity_to_look_at) {
   }

   @Shadow
   public boolean willDeliverCriticalStrike() {
      return false;
   }
}
