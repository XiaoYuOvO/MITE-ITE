package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemTool.class)
public class ItemToolTrans extends Item {
   @Shadow
   protected List blocks_effective_against = new ArrayList();
   @Shadow
   protected List materials_effective_against = new ArrayList();
   @Shadow
   private float damageVsEntity;
   @Shadow
   private Material effective_material;

   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      super.addInformation(item_stack, player, info, extended_info, slot);
      if (item_stack.hasTagCompound()) {
         if (item_stack.getTagCompound().hasKey("tool_level")) {
            if (this.isMaxToolLevel(item_stack)) {
               info.add("工具等级:§6已达到最高级" + item_stack.getTagCompound().getInteger("tool_level"));
            } else {
               info.add("工具等级:" + item_stack.getTagCompound().getInteger("tool_level"));
               if (item_stack.getTagCompound().hasKey("tool_exp")) {
                  info.add("工具经验" + EnumChatFormat.WHITE + item_stack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(item_stack.getTagCompound().getInteger("tool_level") + 1, this.isWeapon(item_stack.getItem())));
               }
            }
         }

         int forgingGrade;
         if (item_stack.getTagCompound().hasKey("forging_grade") && (forgingGrade = item_stack.getTagCompound().getInteger("forging_grade")) != 0) {
            info.add("§5强化等级:§6" + StringUtil.intToRoman(forgingGrade));
            if (extended_info) {
               info.add("  §7耐久增加:§a" + 10 * forgingGrade + "%");
               info.add("  §9攻击力增加:§6" + ItemStack.field_111284_a.format(this.getEnhancedDamage(item_stack)));
            }
         }

         if (extended_info) {
            NBTTagCompound compound = item_stack.stackTagCompound.getCompoundTag("modifiers");
            if (!compound.hasNoTags()) {
               info.add("工具强化:");
               ToolModifierTypes[] var8 = ToolModifierTypes.values();

               for (ToolModifierTypes value : var8) {
                  if (compound.hasKey(value.nbtName)) {
                     info.add("  " + value.color.toString() + value.displayName + "§r " + StringUtil.intToRoman(compound.getInteger(value.nbtName)));
                  }
               }
            }
         }
      }

   }

   private int applyCalculateDurabilityModifier(int origin, ItemStack stack) {
      NBTTagCompound compound = stack.getTagCompound();
      return (int)((float)origin * Math.max(0.0F, 1.0F - ToolModifierTypes.DURABILITY_MODIFIER.getModifierValue(compound)));
   }

   @Overwrite
   public boolean canBlock() {
      return false;
   }

   private float getAttackDamageBounce(ItemStack stack) {
      return ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound);
   }

   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      Multimap<String, AttributeModifier> var1 = super.getAttrModifiers(stack);
      var1.put(GenericAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Tool modifier", (double)this.damageVsEntity + (double)this.getAttackDamageBounce(stack) + (double)this.getEnhancedDamage(stack), 0));
      return var1;
   }

   @Shadow
   private float getBaseHarvestEfficiency(Block block) {
      return 0.0F;
   }

   @Shadow
   private float getCombinedDamageVsEntity() {
      return 0.0F;
   }

   private float getEnhancedDamage(ItemStack itemStack) {
      return
              (float)
                      (itemStack.getEnhanceFactor() *
                              (double) this.getCombinedDamageVsEntity());
   }

   public int getExpReqForLevel(int level, boolean isSword) {
      return isSword ? 400 * level : 200 * level;
   }

   public EnumItemInUseAction getItemInUseAction(ItemStack par1ItemStack, EntityPlayer player) {
      return EnchantmentManager.hasEnchantment(par1ItemStack, Enchantments.DEFENCED) ? EnumItemInUseAction.BLOCK : null;
   }

   @Overwrite
   public float getMaterialHarvestEfficiency() {
      if (this.effective_material == Material.wood) {
         return 1.0F;
      } else if (this.effective_material == Material.flint) {
         return 1.25F;
      } else if (this.effective_material == Material.obsidian) {
         return 1.5F;
      } else if (this.effective_material == Material.rusted_iron) {
         return 1.25F;
      } else if (this.effective_material == Material.copper) {
         return 1.75F;
      } else if (this.effective_material == Material.silver) {
         return 1.75F;
      } else if (this.effective_material == Material.gold) {
         return 1.75F;
      } else if (this.effective_material == Material.iron) {
         return 2.0F;
      } else if (this.effective_material == Material.mithril) {
         return 2.5F;
      } else if (this.effective_material == Material.adamantium) {
         return 3.0F;
      } else if (this.effective_material == Material.diamond) {
         return 2.5F;
      } else if (this.effective_material == Material.ancient_metal) {
         return 2.0F;
      } else if (this.effective_material == Materials.vibranium) {
         return 3.5F;
      } else {
         Minecraft.setErrorMessage("getMaterialHarvestEfficiency: tool material not handled");
         return 0.0F;
      }
   }

   @Shadow
   public int getMaterialHarvestLevel() {
      return 0;
   }

   public int getMaxDamage(ItemStack item_stack) {
      return Math.round((float)super.getMaxDamage(item_stack) * (1.0F + 0.1F * (float)item_stack.getForgingGrade()));
   }

   @Overwrite
   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return Configs.GameMechanics.PLAYER_DEFENCE_MAX_TIME.get();
   }

   public float getMeleeDamageBonus(ItemStack stack) {
      return this.getCombinedDamageVsEntity() + ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound) + this.getEnhancedDamage(stack);
   }

   public final float getMultipliedHarvestEfficiency(Block block, ItemStack itemStack, EntityPlayer player) {
      float commonModifierValue = ToolModifierTypes.EFFICIENCY_MODIFIER.getModifierValue(itemStack.getTagCompound());
      float unnaturalModifierValue = ToolModifierTypes.UNNATURAL_MODIFIER.getModifierValue(itemStack.getTagCompound());
      if (unnaturalModifierValue > 0.0F) {
         int deltaLevel = itemStack.getItem().getMaterialForRepairs().getMinHarvestLevel() - block.blockMaterial.getMinHarvestLevel();
         if (deltaLevel > 0) {
            commonModifierValue += (float)deltaLevel * unnaturalModifierValue;
         }
      }

      return !player.isInWater() && !player.isInRain() ? this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue) : this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue + ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierValue(itemStack.getTagCompound()));
   }

   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer player) {
      return this.isEffectiveAgainstBlock(block, metadata) ? this.getMultipliedHarvestEfficiency(block, itemStack, player) : super.getStrVsBlock(block, metadata);
   }

   @Overwrite
   public final int getToolDecayFromAttackingEntity(ItemStack item_stack, EntityLiving entity_living_base) {
      return this.applyCalculateDurabilityModifier(Math.max((int)(100.0F * ReflectHelper.dyCast(ItemTool.class, this).getBaseDecayRateForAttackingEntity(item_stack)), 1), item_stack);
   }

   @Shadow
   private int getToolDecayFromBreakingBlock(BlockBreakInfo info) {
      return 0;
   }

   public boolean hasExpAndLevel() {
      return true;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLivingBase, EntityLiving par3EntityLivingBase) {
      if (par2EntityLivingBase.onClient()) {
         Minecraft.setErrorMessage("ItemTool.hitEntity: called on client?");
      }

      if (par3EntityLivingBase instanceof EntityPlayer && ((EntityPlayer)par3EntityLivingBase).inCreativeMode()) {
         return false;
      } else {
         par1ItemStack.tryDamageItem(DamageSource.generic, this.getToolDecayFromAttackingEntity(par1ItemStack, par2EntityLivingBase), par3EntityLivingBase);
         return true;
      }
   }

   @Shadow
   public boolean isEffectiveAgainstBlock(Block block, int metadata) {
      return false;
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return this.getMaterialHarvestLevel() * 4 <= this.getToolLevel(itemStack);
   }

   public boolean isWeapon(Item item) {
      return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer;
   }

   @Overwrite
   public boolean onBlockDestroyed(BlockBreakInfo info) {
      if (info.world.isRemote) {
         Minecraft.setErrorMessage("ItemTool.onBlockDestroyed: called on client?");
      }

      Block block = info.block;
      ItemStack item_stack = info.getHarvesterItemStack();
      if (item_stack.isItemStackDamageable() && !block.isPortable(info.world, info.getHarvester(), info.x, info.y, info.z) && !info.isResponsiblePlayerInCreativeMode() && info.getBlockHardness() > 0.0F && this.getStrVsBlock(block, info.getMetadata()) > 1.0F) {
         if (!(item_stack.getItem() instanceof ItemSword) && this.isEffectiveAgainstBlock(info.block, info.getMetadata()) &&!item_stack.getItem().isMaxToolLevel(item_stack)) {
            this.addExpForTool(info.getHarvesterItemStack(), info.getResponsiblePlayer(), 1);
         }

         info.getHarvesterItemStack().tryDamageItem(DamageSource.generic, this.applyCalculateDurabilityModifier(this.getToolDecayFromBreakingBlock(info), info.getHarvesterItemStack()), info.getHarvester());
         return true;
      } else {
         return false;
      }
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      ToolModifierTypes modifierType = ToolModifierTypes.getModifierWithWeight(player.getRNG(), stack);
      if (modifierType != null) {
         if (modifiers.hasKey(modifierType.nbtName)) {
            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers, modifierType)+ "级"));
         } else {
            this.addModifierLevelFor(modifiers, modifierType);
            player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
         }
      }

   }

   //Client+Server
   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if (this.getItemInUseAction(player.getHeldItemStack(), player) == EnumItemInUseAction.BLOCK) {
         if (player.canDefense()){
            player.setHeldItemInUse();
            return true;
         }
      }
      return false;
   }

   @Override
   public void onItemUseFinish(ItemStack item_stack, World world, EntityPlayer player) {
      super.onItemUseFinish(item_stack, world, player);
      if (player.onServer()){
         player.setDefenseCooldown(Configs.GameMechanics.PLAYER_DEFENSE_COOLDOWN.get());
      }
   }

   @Override
   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
      super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
      if (par3EntityPlayer.onServer()){
         par3EntityPlayer.setDefenseCooldown(Configs.GameMechanics.PLAYER_DEFENSE_COOLDOWN.get());
      }
   }
}
