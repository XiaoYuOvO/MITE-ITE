package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityAncientBoneLord.class)
public class EntityAncientBoneLordTrans extends EntityBoneLord {
   public EntityAncientBoneLordTrans(World world) {
      super(world);
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
      if (day < 192) {
         this.setBoots((new ItemStack(Item.bootsAncientMetal)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsAncientMetal)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateAncientMetal)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetAncientMetal)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }

   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      boolean boneLordTweak = Configs.Entities.BONE_LORD_TWEAK.get();
      this.setEntityAttribute(GenericAttributes.followRange, 48.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.30000001192092896D);
      this.setEntityAttribute(GenericAttributes.attackDamage, boneLordTweak ? 20.0D : 10.0D);
      this.setEntityAttribute(GenericAttributes.maxHealth, boneLordTweak ? 50.0D : 25.0D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   public boolean getCanSpawnHere(boolean perform_light_check) {
      if (!this.getWorld().isOverworld()) {
         return super.getCanSpawnHere(perform_light_check);
      } else {
         Vec3D pos = this.getFootPos();
         return this.getWorld().isBloodMoon24HourPeriod() && this.getWorld().getDayOfWorld() >= Configs.GameMechanics.MobSpawning.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY.get() && !(this.getWorld().getClosestEntityLivingBase(this, new Class[]{EntityAncientBoneLord.class}, 64.0D, false, false) instanceof EntityAncientBoneLord) && this.getWorld().getBlock(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ()) != Block.waterMoving && !this.getWorld().anySolidBlockIn(this.getBoundingBox());
      }
   }

   @Overwrite
   public int getExperienceValue() {
      return Configs.Entities.BONE_LORD_TWEAK.get()? super.getExperienceValue() * 4 : super.getExperienceValue() * 2;
   }
}
