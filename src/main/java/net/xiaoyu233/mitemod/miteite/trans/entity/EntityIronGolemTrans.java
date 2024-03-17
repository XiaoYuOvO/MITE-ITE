package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEIronGolem;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityIronGolem.class)
public class EntityIronGolemTrans extends EntityGolem implements ITEIronGolem {
   @Unique
   public int DATA_OBJ_IS_BOOSTED;

   public EntityIronGolemTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(200.0D + day / 26D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 0.0D);
   }

   @Redirect(method = "attackEntityAsMob", at = @At(value = "NEW", target = "(Lnet/minecraft/DamageSource;F)Lnet/minecraft/Damage;"))
   private Damage redirectModifyDamage(DamageSource source, float amount){
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      return new Damage(DamageSource.causeMobDamage(this), (float) (25f + day / 16f + this.getEntityAttributeValue(SharedMonsterAttributes.attackDamage)));
   }

   @Inject(method = "entityInit", at = @At("RETURN"))
   protected void entityInit(CallbackInfo ci) {
      this.DATA_OBJ_IS_BOOSTED = this.dataWatcher.addObject(this.dataWatcher.getNextAvailableId(), (byte)0);
   }

   @Unique
   public boolean isBoosted() {
      return this.dataWatcher.getWatchableObjectByte(this.DATA_OBJ_IS_BOOSTED) != 0;
   }

   @Override
   public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
      if ((Configs.Entities.CAN_BOOST_IRON_GOLEM.get()) && !this.isBoosted() && item_stack != null && item_stack.isBlock() && item_stack.getItemAsBlock().getBlock() == Block.blockMithril) {
         this.setSize(1.9F, 3.5F);
         this.setEntityAttribute(SharedMonsterAttributes.maxHealth, this.getEntityAttributeValue(SharedMonsterAttributes.maxHealth) + 20.0D);
         this.setEntityAttribute(SharedMonsterAttributes.attackDamage, this.getEntityAttributeValue(SharedMonsterAttributes.attackDamage) + 3.0D);
         this.setHealth((float)this.getEntityAttributeValue(SharedMonsterAttributes.maxHealth));
         this.dataWatcher.updateObject(this.DATA_OBJ_IS_BOOSTED, (byte)1);
         if (!this.getWorld().isRemote) {
            player.convertOneOfHeldItem(null);
         }

         return true;
      } else {
         return super.onEntityRightClicked(player, item_stack);
      }
   }

   @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
      if (par1NBTTagCompound.hasKey("Boosted")) {
         this.dataWatcher.updateObject(this.DATA_OBJ_IS_BOOSTED, (byte)(par1NBTTagCompound.getBoolean("Boosted") ? 1 : 0));
      }
      if (this.isBoosted()) {
         this.setSize(1.9F, 3.0F);
      }
   }

   @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
      par1NBTTagCompound.setBoolean("Boosted", this.isBoosted());
   }
}
