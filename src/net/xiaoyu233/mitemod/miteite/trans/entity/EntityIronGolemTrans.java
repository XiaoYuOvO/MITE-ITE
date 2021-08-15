package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityIronGolem.class)
public class EntityIronGolemTrans extends EntityGolem {
   public int DATA_OBJ_IS_BOOSTED;
   @Shadow
   private int attackTimer;
   
   public EntityIronGolemTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(200.0D + day / 26D);
      this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.25D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 0.0D);
   }

   public EntityDamageResult attackEntityAsMob(Entity target) {
      this.attackTimer = 10;
      this.worldObj.setEntityState(this, EnumEntityState.golem_throw);
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), (float) (25f + day / 16f + this.getEntityAttributeValue(GenericAttributes.attackDamage))));
      if (result != null) {
         if (result.entityWasKnockedBack()) {
            target.motionY += 0.4000000059604645D;
         }

         this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
      }

      return result;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
      this.DATA_OBJ_IS_BOOSTED = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
   }

   private boolean isBoosted() {
      return this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_IS_BOOSTED) != 0;
   }

   @Shadow
   private boolean isPlayerCreated() {
      return false;
   }

   @Shadow
   private void setPlayerCreated(boolean playerCreated) {
   }

   @Override
   public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
      if ((Configs.Entities.CAN_BOOST_IRON_GOLEM.get()) && !this.isBoosted() && item_stack != null && item_stack.isBlock() && item_stack.getItemAsBlock().getBlock() == Block.blockMithril) {
         this.setSize(1.9F, 3.5F);
         this.setEntityAttribute(GenericAttributes.maxHealth, this.getEntityAttributeValue(GenericAttributes.maxHealth) + 20.0D);
         this.setEntityAttribute(GenericAttributes.attackDamage, this.getEntityAttributeValue(GenericAttributes.attackDamage) + 3.0D);
         this.setHealth((float)this.getEntityAttributeValue(GenericAttributes.maxHealth));
         this.dataWatcher.updateObject(this.DATA_OBJ_IS_BOOSTED, (byte)1);
         if (!this.getWorld().isRemote) {
            player.convertOneOfHeldItem(null);
         }

         return true;
      } else {
         return super.onEntityRightClicked(player, item_stack);
      }
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("Boosted")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_IS_BOOSTED, (byte)(par1NBTTagCompound.getBoolean("Boosted") ? 1 : 0));
      }

      this.setPlayerCreated(par1NBTTagCompound.getBoolean("PlayerCreated"));
      if (this.isBoosted()) {
         this.setSize(1.9F, 3.0F);
      }

   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setBoolean("Boosted", this.isBoosted());
      par1NBTTagCompound.setBoolean("PlayerCreated", this.isPlayerCreated());
   }
}
