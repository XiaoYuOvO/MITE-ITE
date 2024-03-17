package net.xiaoyu233.mitemod.miteite.trans.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.api.IUpgradableItem;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ModifierUtils;
import net.xiaoyu233.mitemod.miteite.util.ItemUtil;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(ItemArmor.class)
public abstract class ItemArmorTrans extends Item implements IDamageableItem, IUpgradableItem {
   @Shadow
   @Final
   public int armorType;
   @Shadow
   protected Material effective_material;

   @Unique
   private Function<Integer,Integer> expForLevel;

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInitExpForLevel(int par1, Material material,int par2,boolean is_chain_mail, CallbackInfo callbackInfo){
      if (material == Material.copper || material == Material.silver){
         this.expForLevel = this.createExpForLevel(16,8);
      }else if (material == Material.gold){
         this.expForLevel = this.createExpForLevel(18,9);
      }else if (material == Material.iron || material == Material.ancient_metal){
         this.expForLevel = this.createExpForLevel(20,10);
      } else if (material == Material.mithril) {
         this.expForLevel = this.createExpForLevel(24,12);
      }else if (material == Material.adamantium){
         this.expForLevel = this.createExpForLevel(28,14);
      }else if (material == Materials.vibranium){
         this.expForLevel = this.createExpForLevel(32,16);
      }else {
         this.expForLevel = this.createExpForLevel(150,75);
      }
   }

   private Function<Integer, Integer> createExpForLevel(int base,int increase){
      return (level) -> base + level * increase;
   }

   @Redirect(method = "getTotalArmorProtection", at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemArmor;getProtectionAfterDamageFactor(Lnet/minecraft/ItemStack;Lnet/minecraft/EntityLivingBase;)F"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/Item;isArmor()Z")))
   private static float redirectAddTotalDefense(ItemArmor instance, ItemStack item_stack, EntityLivingBase owner, ItemStack[] armors, DamageSource damage_source, boolean include_enchantments, EntityLivingBase ownerPar) {
      float additionalTotalDefense = instance.getProtectionAfterDamageFactor(item_stack, owner);
      if (damage_source != null) {
         if (damage_source.getResponsibleEntity() instanceof EntityLiving && damage_source.getResponsibleEntity().isEntityUndead()) {
            additionalTotalDefense += ArmorModifierTypes.BLESSED_MODIFIER.getModifierValue(item_stack.stackTagCompound);
         }

         additionalTotalDefense += ItemUtil.getProtectionForDamage(item_stack, damage_source, owner);
      }
      return additionalTotalDefense;
   }

   @Inject(method = "addInformation", at = @At("HEAD"))
   public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> info, boolean extended_info, Slot slot, CallbackInfo callbackInfo) {
      int forgingGrade;
      if (itemStack.hasTagCompound()) {
         int toolLevel = itemStack.getTagCompound().getInteger("tool_level");
         if (itemStack.getTagCompound().hasKey("tool_level")) {
            int maxArmorLevel = this.getMaxToolLevel(itemStack);
            if (this.isMaxToolLevel(itemStack)) {
               info.add("装备等级:§6已达到最高级" + maxArmorLevel);
            } else {
               info.add("装备等级:" + toolLevel + "/" + maxArmorLevel);
               if (itemStack.getTagCompound().hasKey("tool_exp")) {
                  info.add("装备经验" + EnumChatFormatting.WHITE + itemStack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(toolLevel, this.armorType, ReflectHelper.dyCast(this)));
               }
            }
         }

         if (itemStack.getTagCompound().hasKey("forging_grade") && (forgingGrade = itemStack.getTagCompound().getInteger("forging_grade")) != 0) {
            info.add("§5强化等级:§6" + StringUtil.intToRoman(forgingGrade));
            if (extended_info) {
               info.add("  §7装备经验增加:§a" +  ItemStack.field_111284_a.format(this.getEquipmentExpBounce(itemStack) * 100) + "%");
               info.add("  §9护甲增加:§6" + ItemStack.field_111284_a.format(this.getEnhancedProtection(itemStack)));
            }
         }

         if (extended_info) {
            NBTTagCompound compound = itemStack.stackTagCompound.getCompoundTag("modifiers");
            if (!compound.hasNoTags()) {
               info.add("装备强化:");
               ArmorModifierTypes[] var9 = ArmorModifierTypes.values();

                for (ArmorModifierTypes value : var9) {
                    if (compound.hasKey(value.nbtName)) {
                        info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                    }
                }
            }
         }
      }
   }

   @Override
   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      super.addExpForTool(stack, player, (int) (exp * (this.getEquipmentExpBounce(stack) + 1)) * 2);
   }

   @Unique
   public int getExpReqForLevel(int tool_level, int slotIndexl, ItemArmor armor) {
       return switch (slotIndexl) {
           case 0 -> 2 * this.expForLevel.apply(tool_level);
           case 1 -> 4 * this.expForLevel.apply(tool_level);
           case 2 -> 3 * this.expForLevel.apply(tool_level);
           case 3 -> this.expForLevel.apply(tool_level);
           default -> 64 * tool_level;
       };
   }

   @Unique
   private float getEnhancedProtection(ItemStack itemStack) {
      return (float)(itemStack.getEnhanceFactor() * (double)this.getRawProtection() * 0.68f + (double)((float)itemStack.getForgingGrade() / 3.0F)) * (0.75f);
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return this.getExpReqForLevel(i, this.armorType, ReflectHelper.dyCast(this));
   }

   @Inject(method = "getMaterialProtection", at = @At("HEAD"), cancellable = true)
   public void injectVibraniumProtections(CallbackInfoReturnable<Integer> cir) {
      if (this.effective_material == Materials.vibranium){
         cir.setReturnValue(15);
      }
   }

   @Override
   public int getMaxDamage(ItemStack item_stack) {
      return super.getMaxDamage(item_stack);
   }

   @ModifyReturnValue(method = "getMultipliedProtection", at = @At("RETURN"))
   public final float getMultipliedProtection(float multiplied_protection, ItemStack item_stack) {
      if (item_stack != null && item_stack.stackTagCompound != null) {
         float protection_modifier = ArmorModifierTypes.PROTECTION_MODIFIER.getModifierValue(item_stack.stackTagCompound);
         if (protection_modifier > 0.0F) {
            multiplied_protection += protection_modifier;
         }
      }

      if (item_stack.getForgingGrade() != 0) {
         multiplied_protection += this.getEnhancedProtection(item_stack);
      }

      return multiplied_protection;
   }

   @Shadow
   public abstract float getProtectionAfterDamageFactor(ItemStack itemStack, EntityLivingBase owner);

   @Unique
   private float getRawProtection() {
      return (float)(this.getNumComponentsForDurability() * this.getMaterialProtection()) / 24.0F;
   }

   @Shadow
   public abstract int getRepairCost();

   @Shadow public abstract int getMaterialProtection();

   public boolean hasExpAndLevel() {
      return true;
   }

   public int getMaxToolLevel(ItemStack itemStack){
      return this.getMaterialForDurability().getMinHarvestLevel() * 3 + itemStack.getForgingGrade();
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return getMaxToolLevel(itemStack) <= this.getToolLevel(itemStack);
   }

   public boolean isWeapon(Item b) {
      return false;
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      ArmorModifierTypes modifierType = ModifierUtils.getModifierWithWeight(ModifierUtils.getAllCanBeAppliedArmorModifiers(stack),player.getRNG());
      if (modifierType != null) {
         if (modifiers.hasKey(modifierType.nbtName)) {
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers, modifierType) + "级"
            ));
         } else {
            this.addModifierLevelFor(modifiers, modifierType);
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
         }
      }

      player.suppressNextStatIncrement();
   }
}
