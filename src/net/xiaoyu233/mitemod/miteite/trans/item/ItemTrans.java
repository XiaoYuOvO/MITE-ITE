package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemTrans {
   @Shadow private int sugar_content;

   @Shadow public abstract boolean hasQuality();

   @Shadow private int maxDamage;

   @Shadow public abstract boolean isDamageable();

   @Shadow
   private static Item[] itemsList;
   @Shadow
   @Final
   private int itemID;

   @Shadow
   protected List materials;

   public void setSugarContent(int sugarContent){
      this.sugar_content = sugarContent;
   }

   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      stack.fixNBT();
      NBTTagCompound tagCompound = stack.stackTagCompound;
      if (tagCompound != null) {
         if (tagCompound.hasKey("tool_exp")) {
            tagCompound.setInteger("tool_exp", tagCompound.getInteger("tool_exp") + exp);
            if (tagCompound.hasKey("tool_level")) {
               int currentLevel = tagCompound.getInteger("tool_level");
               int nextLevelExpReq = this.getExpReqForLevel(currentLevel, this.isWeapon(stack.getItem()));
               if (tagCompound.getInteger("tool_exp") >= nextLevelExpReq) {
                  tagCompound.setInteger("tool_level", currentLevel + 1);
                  tagCompound.setInteger("tool_exp", 0);
                  if (!player.worldObj.isRemote) {
                     player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "已升级,当前等级:" + (currentLevel + 1)).setColor(EnumChatFormat.DARK_AQUA));
                  }

                  if (!tagCompound.hasKey("modifiers")) {
                     tagCompound.setCompoundTag("modifiers", new NBTTagCompound());
                  }

                  this.onItemLevelUp(tagCompound, player, stack);
               }
            }
         }
      } else {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("tool_exp", 0);
         compound.setInteger("tool_level", 0);
         stack.stackTagCompound = compound;
      }

   }

   public List getMaterials() {
      return materials;
   }

   public int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes modifierType) {
      int effectLevel = modifiers.getInteger(modifierType.getNbtName()) + 1;
      modifiers.setInteger(modifierType.getNbtName(), effectLevel);
      return effectLevel;
   }

   @Overwrite
   public final int getMaxDamage(EnumQuality quality) {
      if (!this.isDamageable()) {
         Minecraft.setErrorMessage("getMaxDamage: item is not damageable, " + this);
      }
      return this.maxDamage;
   }

   @Shadow
   public ItemBlock getAsItemBlock() {
      return null;
   }

   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      return HashMultimap.create();
   }

   @Shadow
   private int getBurnTime(ItemStack item_stack) {
      return 0;
   }

   public int getCookTime() {
      if (this.itemID == Block.blockMithril.blockID) {
         return (Configs.Item.Recipes.MITHRIL_TO_ADAMANTIUM_COOK_TIME.get());
      } else if (this.itemID == Block.blockIron.blockID) {
         return (Configs.Item.Recipes.IRON_TO_MITHRIL_COOK_TIME.get());
      } else if (this.itemID == Block.coalBlock.blockID) {
         return 2000;
      } else {
         return this.isBlock() ? 200 * (this.getAsItemBlock().getBlock().getMinHarvestLevel(-1) + 1) : 200;
      }
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return 0;
   }

   @Overwrite
   public int getHeatLevel(ItemStack item_stack) {
      if (ReflectHelper.dyCast(this) == Items.BLAZE_COAL_POWDER) {
         return 5;
      } else if (ReflectHelper.dyCast(this) == Item.blazeRod) {
         return 4;
      } else {
         return this.getBurnTime(item_stack) > 0 ? 1 : 0;
      }
   }

   @Shadow
   private Material getMaterialForRepairs() {
      return null;
   }

   public float getMeleeDamageBonus(ItemStack stack) {
      return 0.0F;
   }

   public Item getRepairItem() {
      Material material_for_repairs = this.getMaterialForRepairs();
      if (material_for_repairs == Material.copper) {
         return Item.copperNugget;
      } else if (material_for_repairs == Material.silver) {
         return Item.silverNugget;
      } else if (material_for_repairs == Material.gold) {
         return Item.goldNugget;
      } else if (material_for_repairs != Material.iron && material_for_repairs != Material.rusted_iron) {
         if (material_for_repairs == Material.mithril) {
            return Item.mithrilNugget;
         } else if (material_for_repairs == Material.adamantium) {
            return Item.adamantiumNugget;
         } else if (material_for_repairs == Material.ancient_metal) {
            return Item.ancientMetalNugget;
         } else {
            return material_for_repairs == Materials.vibranium ? Items.VIBRANIUM_NUGGET : null;
         }
      } else {
         return Item.ironNugget;
      }
   }

   public String getResourceLocationPrefix() {
      return "";
   }

   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer user) {
      return 0.0F;
   }

   public int getToolLevel(ItemStack itemStack) {
      return itemStack.stackTagCompound != null ? itemStack.getTagCompound().getInteger("tool_level") : 0;
   }

   public boolean hasExpAndLevel() {
      return false;
   }

   @Shadow
   public boolean isBlock() {
      return false;
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return false;
   }

   public boolean isWeapon(Item b) {
      return false;
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
   }

   @Shadow
   public Item setCreativeTab(CreativeModeTab table) {
      return null;
   }

   public void setCreativeTable(CreativeModeTab table) {
      this.setCreativeTab(table);
   }

   @Shadow
   public Item setMaxStackSize(int maxStackSize) {
      return null;
   }

   public void setResourceLocation(String location) {
      this.setTextureName(location);
   }

   @Shadow
   public Item setTextureName(String location) {
      return null;
   }
}
