package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(EntityMonster.class)
public abstract class EntityMonsterTrans extends EntityInsentient implements IMonster {
   public boolean[] picked_up_a_held_item_array;
   public EntityMonsterTrans(World par1World) {
      super(par1World);
   }

   protected void addRandomArmor() {
      int hour = this.getWorld().getHourOfDay();
      int day = this.getWorld().getDayOfWorld();
      Random rand;
      if (day > 32 && ((day % 2 == 0 || day > 64) && hour >= 18 || ((day - 1) % 2 == 0 || day > 64) && hour <= 6)) {
         this.addPotionEffect(new MobEffect(1, 999999, this.getRNG().nextInt(Math.max((day - 32) / 96, 1)), true));
         rand = this.getRNG();
         if (rand.nextInt(5) == 0) {
            this.addPotionEffect(new MobEffect(5, 999999, this.getRNG().nextInt(Math.max((day - 32) / 128, 1)), true));
         }

         MonsterUtil.addDefaultArmor(day, this, false);
      } else if (day > 128) {
         rand = this.getRNG();
         if (rand.nextInt(4) < (day - 96) / 32) {
            this.addPotionEffect(new MobEffect(1, 999999, this.getRNG().nextInt(Math.max((day - 32) / 96, 1)), true));
         }

         if (rand.nextInt(5) < (day - 96) / 32) {
            this.addPotionEffect(new MobEffect(5, 999999, this.getRNG().nextInt(Math.max((day - 32) / 128, 1)), true));
         }

         MonsterUtil.addDefaultArmor(day, this, false);
      }

   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.picked_up_a_held_item_array = super.picked_up_a_held_item_array;

      try {
         this.setEntityAttribute(GenericAttributes.maxHealth, this.getEntityAttributeValue(GenericAttributes.maxHealth) * 2.0D);
         this.setEntityAttribute(GenericAttributes.attackDamage, this.getEntityAttributeValue(GenericAttributes.attackDamage) * 2.0D);
      } catch (NullPointerException var2) {
         this.setEntityAttribute(GenericAttributes.maxHealth, GenericAttributes.maxHealth.getDefaultValue() * 2.0D);
         this.setEntityAttribute(GenericAttributes.attackDamage, GenericAttributes.attackDamage.getDefaultValue() * 2.0D);
      }

      this.setEntityAttribute(GenericAttributes.followRange, 32.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage);
   }

   @Shadow
   public boolean canSpawnInShallowWater() {
      return false;
   }

   public void dropContainedItems() {
   }

   protected void dropEquipment(boolean recently_hit_by_player, int par2) {
      for(int var3 = 0; var3 < this.getInventory().length; ++var3) {
         ItemStack var4 = this.getEquipmentInSlot(var3);
         if (var4 != null && (!var4.isItemStackDamageable() || this.picked_up_a_held_item_array[var3] && var4.getRemainingDurability() > var4.getMaxDamage() / 4)) {
            this.dropItemStack(var4, 0.0F);
            this.setWornItem(var3, null);
         }
      }

   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.1D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "attackEntityAsMob(Lnet/minecraft/Entity;)Lnet/minecraft/EntityDamageResult;",
           at = @At(value = "INVOKE_ASSIGN",shift = At.Shift.AFTER,
                   target = "Lnet/minecraft/Damage;<init>(Lnet/minecraft/DamageSource;F)V"))
   private void injectCirtAttack(CallbackInfo ci,Damage damage){
      float critBouns = 0.0F;
      boolean critical = false;
      if (EnchantmentManager.hasEnchantment(this.getHeldItemStack(), Enchantments.CRIT)) {
         int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, this.getHeldItemStack());
         critical = this.rand.nextInt(10) < (Configs.Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL.get()) * critLevel;
         critBouns = (float)critLevel * (Configs.Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL.get())
                 .floatValue();
      }
      if (critical) {
         damage.scaleAmount(1.5F);
         damage.addAmount(critBouns);
      }
   }
}
