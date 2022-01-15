package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.IUpgradableItem;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ModifierUtils;
import net.xiaoyu233.mitemod.miteite.util.ItemUtil;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(ItemArmor.class)
public abstract class ItemArmorTrans extends Item implements IDamageableItem, IUpgradableItem {
   @Shadow
   @Final
   public int armorType;
   @Shadow
   private Material effective_material;
   @Shadow
   @Final
   private boolean is_chain_mail;

   @Final
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

   @Overwrite
   public static float getTotalArmorProtection(ItemStack[] armors, DamageSource damage_source, boolean include_enchantments, EntityLiving owner) {
      float total_defense = 0.0F;
      if (damage_source != null && damage_source.isUnblockable()) {
         return total_defense;
      } else {
         if (damage_source == null || !damage_source.bypassesMundaneArmor()) {
            ItemStack[] var5 = armors;
            int var6 = armors.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ItemStack item_stack = var5[var7];
               if (item_stack != null) {
                  Item item = item_stack.getItem();
                  if (item instanceof ItemHorseArmor) {
                     ItemHorseArmor barding = (ItemHorseArmor)item;
                     total_defense += (float)barding.getProtection();
                  } else if (item.isArmor()) {
                     ItemArmor armor = (ItemArmor)item_stack.getItem();
                     if (damage_source != null) {
                        if (damage_source.getResponsibleEntity() instanceof EntityLiving && damage_source.getResponsibleEntity().isEntityUndead()) {
                           total_defense += ArmorModifierTypes.BLESSED_MODIFIER.getModifierValue(item_stack.stackTagCompound);
                        }

                        total_defense += ItemUtil.getProtectionForDamage(item_stack, damage_source, owner);
                     }

                     total_defense += armor.getProtectionAfterDamageFactor(item_stack, owner);
                  }
               }
            }
         }

         if (include_enchantments) {
            total_defense += EnchantmentProtection.getTotalProtectionOfEnchantments(armors, damage_source, owner);
         }

         total_defense = MathHelper.tryFitToNearestInteger(total_defense, 1.0E-4F);
         return total_defense;
      }
   }

   @Overwrite
   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
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
                  info.add("装备经验" + EnumChatFormat.WHITE + itemStack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(toolLevel, this.armorType, ReflectHelper.dyCast(this)));
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
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  ArmorModifierTypes value = var9[var11];
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
            }
         }
      }

      if (extended_info) {
         info.add("");
         float protection = this.getProtectionAfterDamageFactor(itemStack, player);
         forgingGrade = protection < 1.0F ? 2 : 1;
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("item.tooltip.protectionBonus", new Object[]{StringHelper.formatFloat(protection, forgingGrade, forgingGrade)}));
      }

   }

   @Shadow
   public abstract void func_82813_b(ItemStack par1ItemStack, int par2);

   @Override
   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      super.addExpForTool(stack, player, (int) (exp * (this.getEquipmentExpBounce(stack) + 1)) * 2);
   }

   public int getExpReqForLevel(int tool_level, int slotIndexl, ItemArmor armor) {
      switch(slotIndexl) {
          case 0:
             return 2 * this.expForLevel.apply(tool_level);
          case 1:
             return 4 * this.expForLevel.apply(tool_level);
          case 2:
             return 3 * this.expForLevel.apply(tool_level);
          case 3:
             return this.expForLevel.apply(tool_level);
          default:
             return 64 * tool_level;
      }
   }

   private float getEnhancedProtection(ItemStack itemStack) {
      return (float)(itemStack.getEnhanceFactor() * (double)this.getRawProtection() * 0.68f + (double)((float)itemStack.getForgingGrade() / 3.0F)) * (0.75f);
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return this.getExpReqForLevel(i, this.armorType, ReflectHelper.dyCast(this));
   }

   @Overwrite
   public int getMaterialProtection() {
      int protection;
      if (this.effective_material == Material.leather) {
         protection = 2;
      } else if (this.effective_material == Material.rusted_iron) {
         protection = 6;
      } else if (this.effective_material == Material.copper) {
         protection = 7;
      } else if (this.effective_material == Material.silver) {
         protection = 7;
      } else if (this.effective_material == Material.gold) {
         protection = 6;
      } else if (this.effective_material != Material.iron && this.effective_material != Material.ancient_metal) {
         if (this.effective_material == Material.mithril) {
            protection = 9;
         } else if (this.effective_material == Material.adamantium) {
            protection = 10;
         } else {
            if (this.effective_material != Materials.vibranium) {
               return 0;
            }

            protection = 15;
         }
      } else {
         protection = 8;
      }

      if (this.is_chain_mail) {
         protection -= 2;
      }

      return protection;
   }

   @Override
   public int getMaxDamage(ItemStack item_stack) {
      return super.getMaxDamage(item_stack);
   }

   @Overwrite
   public float getMultipliedProtection(ItemStack item_stack) {
      float multiplied_protection = this.getRawProtection();
      if (item_stack != null && item_stack.hasEnchantment(Enchantment.protection, false)) {
         multiplied_protection += multiplied_protection * item_stack.getEnchantmentLevelFraction(Enchantment.protection) * 0.5F;
      }

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
   private float getProtectionAfterDamageFactor(ItemStack itemStack, EntityLiving owner) {
      return 0.0F;
   }

   private float getRawProtection() {
      return (float)(this.getNumComponentsForDurability() * this.getMaterialProtection()) / 24.0F;
   }

   @Shadow
   public int getRepairCost() {
      return 0;
   }

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
            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers, modifierType) + "级"
            ));
         } else {
            this.addModifierLevelFor(modifiers, modifierType);
            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
         }
      }

      player.suppressNextStatIncrement();
   }
}
