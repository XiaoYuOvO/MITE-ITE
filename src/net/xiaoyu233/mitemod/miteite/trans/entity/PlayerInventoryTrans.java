package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerInventory.class)
public class PlayerInventoryTrans {
   @Shadow
   public ItemStack[] armorInventory;
   @Shadow public EntityPlayer player;

   @Shadow
   private int getNumberOfArmorPiecesEquipped() {
      return 0;
   }

   //TODo Need to be tested
   @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
           method = "tryDamageArmor",
           at = @At(
                   shift = At.Shift.BEFORE,
                   value = "INVOKE",
                   target = "Lnet/minecraft/EntityDamageResult;applyArmorDamageResult(Lnet/minecraft/ItemDamageResult;)Lnet/minecraft/EntityDamageResult;"))
   private void injectDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result, CallbackInfo ci, int amount_remaining, int armor_index, ItemStack item_stack, int portion){
      float durability_modifier = 0.0F;
      if (item_stack.getTagCompound() != null) {
         durability_modifier = ArmorModifierTypes.DURABILITY_MODIFIER.getModifierValue(item_stack.stackTagCompound);
      }

      result.applyArmorDamageResult(item_stack.tryDamageItem(damage_source, (int)Math.max((1.0F - durability_modifier) * (float)portion, 1.0F), this.player));
      Item item = item_stack.getItem();
      if (damage_source != null && damage_source.getResponsibleEntity() != null
              && !item.isMaxToolLevel(item_stack)
      ) {
         item.addExpForTool(item_stack, this.player, (int) MathHelper.clamp_float(result.getAmountOfHealthLost(),1f,4f) * 2);
      }
   }

   @Redirect(
           method = "tryDamageArmor",
   at = @At(
           value = "INVOKE",
           target = "Lnet/minecraft/EntityDamageResult;applyArmorDamageResult(Lnet/minecraft/ItemDamageResult;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult removeApplyArmorDamageResult(EntityDamageResult caller,ItemDamageResult itemDamageResult){
      return null;
   }

   @Redirect(
           method = "tryDamageArmor",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/ItemStack;tryDamageItem(Lnet/minecraft/DamageSource;ILnet/minecraft/EntityLiving;)Lnet/minecraft/ItemDamageResult;"))
   private ItemDamageResult removeTryDamageItemStack(ItemStack caller,DamageSource damage_source, int damage, EntityLiving owner){
      return null;
   }
}
