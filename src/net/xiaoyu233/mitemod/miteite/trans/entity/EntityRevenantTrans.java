package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityRevenant.class)
public class EntityRevenantTrans extends EntityZombie {
   public EntityRevenantTrans(World world) {
      super(world);
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld().getDayOfWorld();
      if (day < 128) {
         this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }

   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.2800000011920929D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 12.0D);
      this.setEntityAttribute(EntityZombie.field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
      this.setEntityAttribute(GenericAttributes.maxHealth, 38.0D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
