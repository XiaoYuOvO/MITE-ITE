package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRevenant.class)
public class EntityRevenantTrans extends EntityZombie {
   public EntityRevenantTrans(World world) {
      super(world);
   }

   @Inject(method = "addRandomEquipment", at = @At("HEAD"), cancellable = true)
   protected void addRandomEquipment(CallbackInfo ci) {
      int day = this.getWorld().getDayOfOverworld();
      if (day < 128) {
         this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }
      ci.cancel();

   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.2800000011920929D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 12.0D + day / 24d);
      this.setEntityAttribute(EntityZombie.field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 35.0D + day / 16D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      int day = this.getWorld().getDayOfOverworld();
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double) day / 64.0D / 10.0D) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + day / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
