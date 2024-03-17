package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEItem;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public class ItemTrans implements ITEItem {
   @Shadow private int sugar_content;

   @Shadow
   @Final
   public int itemID;
   @Shadow
   protected List<Material> materials;

   @Shadow
   public ItemBlock getAsItemBlock() {
      return null;
   }

   @Shadow
   public boolean isBlock() {
      return false;
   }

   @Shadow
   public Material getMaterialForRepairs() {
      return null;
   }

   @Override
   public void setSugarContent(int sugarContent){
      this.sugar_content = sugarContent;
   }

   @Override
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
                     player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "已升级,当前等级:" + (currentLevel + 1)).setColor(EnumChatFormatting.DARK_AQUA));
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

   @Override
   public List<Material> getMaterials() {
      return materials;
   }

   @Override
   public int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes modifierType) {
      int effectLevel = modifiers.getInteger(modifierType.getNbtName()) + 1;
      modifiers.setInteger(modifierType.getNbtName(), effectLevel);
      return effectLevel;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      return HashMultimap.create();
   }

   @Override
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

   @Override
   public int getExpReqForLevel(int i, boolean weapon) {
      return 0;
   }

   @Override
   public float getMeleeDamageBonus(ItemStack stack) {
      return 0.0F;
   }

   @Override
   public String getResourceLocationPrefix() {
      return "";
   }

   @Override
   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer user) {
      return 0.0F;
   }

   @Override
   public int getToolLevel(ItemStack itemStack) {
      return itemStack.stackTagCompound != null ? itemStack.getTagCompound().getInteger("tool_level") : 0;
   }

   @Override
   public boolean hasExpAndLevel() {
      return false;
   }

   @Override
   public boolean isMaxToolLevel(ItemStack itemStack) {
      return false;
   }

   @Override
   public int getMaxToolLevel(ItemStack itemStack) {
      return 0;
   }

   @Override
   public boolean isWeapon(Item b) {
      return false;
   }

   @Override
   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
   }

   @Inject(method = "getRepairItem", at = @At("HEAD"), cancellable = true)
   public void injectVibraniumRepair(CallbackInfoReturnable<Item> cir) {
      if (this.getMaterialForRepairs() == Materials.vibranium) cir.setReturnValue(Items.VIBRANIUM_NUGGET);
   }



   @Inject(method = "getHeatLevel", at = @At("HEAD"), cancellable = true)
   public void injectBlazeCoalHeat(ItemStack item_stack, CallbackInfoReturnable<Integer> cir) {
      if (ReflectHelper.dyCast(this) == Items.BLAZE_COAL_POWDER) {
         cir.setReturnValue(5);
      }
   }

   @Redirect(method = "getMaxDamage(Lnet/minecraft/EnumQuality;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;hasQuality()Z"))
   public final boolean redirectDisableQuality(Item instance) {
      return false;
   }
}
