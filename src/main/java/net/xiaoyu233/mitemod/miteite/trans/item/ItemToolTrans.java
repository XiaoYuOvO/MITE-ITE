package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.IUpgradableItem;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ModifierUtils;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.BiFunction;

@Mixin(ItemTool.class)
public abstract class ItemToolTrans extends Item implements IUpgradableItem {
   @Shadow
   protected List blocks_effective_against;
   @Shadow
   protected List materials_effective_against;
   @Shadow
   protected float damageVsEntity;
   @Shadow
   private Material effective_material;
   @Unique
   private BiFunction<Integer,Boolean,Integer> expForLevel;

   @Override
   public int getExpReqForLevel(int level, boolean isSword) {
      return this.expForLevel.apply(level, isSword);
   }

   @Override
   public float getMeleeDamageBonus(ItemStack stack) {
      return this.getCombinedDamageVsEntity() + ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound) + this.getEnhancedDamage(stack);
   }

   @Unique
   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer player) {
      return this.isEffectiveAgainstBlock(block, metadata) ? this.getMultipliedHarvestEfficiency(block, itemStack, player) : super.getStrVsBlock(block, metadata);
   }

   @Override
   public boolean hasExpAndLevel() {
      return true;
   }

   @Override
   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      super.addExpForTool(stack, player, (int) (exp * (this.getEquipmentExpBounce(stack) + 1)));
   }

   @Override
   public boolean isMaxToolLevel(ItemStack itemStack) {
      return this.getMaxToolLevel(itemStack) <= this.getToolLevel(itemStack);
   }

   @Override
   public int getMaxToolLevel(ItemStack itemStack){
      return 15 + itemStack.getForgingGrade();
   }

   @Override
   public boolean isWeapon(Item item) {
      return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer;
   }

   @Override
   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
      NBTTagCompound modifiers = tagCompound.getCompoundTag("modifiers");
      ToolModifierTypes modifierType = ModifierUtils.getModifierWithWeight(ModifierUtils.getAllCanBeAppliedToolModifiers(stack),player.getRNG());
      if (modifierType != null) {
         if (modifiers.hasKey(modifierType.nbtName)) {
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers, modifierType)+ "级"));
         } else {
            this.addModifierLevelFor(modifiers, modifierType);
            player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
         }
      }

   }

   @Override
   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      Multimap<String, AttributeModifier> var1 = super.getAttrModifiers(stack);
      var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Tool modifier", (double)this.damageVsEntity + (double)this.getAttackDamageBounce(stack) + (double)this.getEnhancedDamage(stack), 0));
      return var1;
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInitExpForLevel(int par1, Material material,CallbackInfo callbackInfo){
      if (material == Material.copper || material == Material.silver){
         this.expForLevel = createExpForLevel(100,50);
      }else if (material == Material.gold){
         this.expForLevel = createExpForLevel(125,60);
      }else if (material == Material.iron || material == Material.ancient_metal){
         this.expForLevel = createExpForLevel(150,75);
      } else if (material == Material.mithril) {
         this.expForLevel = createExpForLevel(200,100);
      }else if (material == Material.adamantium){
         this.expForLevel = createExpForLevel(200,120);
      }else if (material == Materials.vibranium){
         this.expForLevel = createExpForLevel(200,150);
      }else {
         this.expForLevel = createExpForLevel(150,75);
      }
   }

   @Unique
   private static BiFunction<Integer, Boolean, Integer> createExpForLevel(int base, int increase){
      return (level, isWeapon) -> base + level * increase * (isWeapon ? 2 : 1 );
   }

   @SuppressWarnings("unchecked")
   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      super.addInformation(item_stack, player, info, extended_info, slot);
      if (item_stack.hasTagCompound()) {
         if (item_stack.getTagCompound().hasKey("tool_level")) {
            int tool_level = item_stack.getTagCompound().getInteger("tool_level");
            int maxToolLevel = this.getMaxToolLevel(item_stack);
            if (this.isMaxToolLevel(item_stack)) {
               info.add("工具等级:§6已达到最高级" + maxToolLevel);
            } else {
               info.add("工具等级:" + tool_level + "/" + maxToolLevel);
               if (item_stack.getTagCompound().hasKey("tool_exp")) {
                  info.add("工具经验" + EnumChatFormatting.WHITE + item_stack.getTagCompound().getInteger("tool_exp") + "/" + this.getExpReqForLevel(tool_level, this.isWeapon(item_stack.getItem())));
               }
            }
         }

         int forgingGrade;
         if (item_stack.getTagCompound().hasKey("forging_grade") && (forgingGrade = item_stack.getTagCompound().getInteger("forging_grade")) != 0) {
            info.add("§5强化等级:§6" + StringUtil.intToRoman(forgingGrade));
            if (extended_info) {
               info.add("  §7装备经验增加:§a" + this.getEquipmentExpBounce(item_stack) * 100 + "%");
               if (this.isWeapon(item_stack.getItem())){
                  info.add("  §9攻击力增加:§6" + ItemStack.field_111284_a.format(this.getEnhancedDamage(item_stack)));
               }else {
                  info.add("  §9挖掘速度加:§6" + ItemStack.field_111284_a.format(item_stack.getEnhanceFactor() * 100) + "%");
               }
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

   @Unique
   private int applyCalculateDurabilityModifier(int origin, ItemStack stack) {
      NBTTagCompound compound = stack.getTagCompound();
      return (int)((float)origin * Math.max(0.0F, 1.0F - ToolModifierTypes.DURABILITY_MODIFIER.getModifierValue(compound)));
   }

   @Unique
   protected float getAttackDamageBounce(ItemStack stack) {
      return ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.stackTagCompound);
   }

   @Shadow
   public float getBaseHarvestEfficiency(Block block) {
      return 0.0F;
   }

   @Shadow
   public float getCombinedDamageVsEntity() {
      return 0.0F;
   }

   @Unique
   private float getEnhancedDamage(ItemStack itemStack) {
      return this.isWeapon(itemStack.getItem()) ? (float) (itemStack.getEnhanceFactor() * (double) this.getCombinedDamageVsEntity()) : 0.0f;
   }

   public EnumItemInUseAction getItemInUseAction(ItemStack par1ItemStack, EntityPlayer player) {
      return EnchantmentHelper.hasEnchantment(par1ItemStack, Enchantments.DEFENCED) ? EnumItemInUseAction.BLOCK : null;
   }

   @Inject(method = "getMaterialHarvestEfficiency", at = @At("HEAD"), cancellable = true)
   public void getMaterialHarvestEfficiency(CallbackInfoReturnable<Float> cir) {
      if (this.effective_material == Materials.vibranium) {
         cir.setReturnValue(3.5F);
      }
   }

   @ModifyConstant(method = "getMaxItemUseDuration", constant = @Constant(intValue = 72000))
   public int modifyMaxItemUseDuration(int constant) {
      return Configs.GameMechanics.PLAYER_DEFENCE_MAX_TIME.get();
   }

   @Unique
   public final float getMultipliedHarvestEfficiency(Block block, ItemStack itemStack, EntityPlayer player) {
      float commonModifierValue = ToolModifierTypes.EFFICIENCY_MODIFIER.getModifierValue(itemStack.getTagCompound());
      float unnaturalModifierValue = ToolModifierTypes.UNNATURAL_MODIFIER.getModifierValue(itemStack.getTagCompound());
      if (unnaturalModifierValue > 0.0F) {
         int deltaLevel = itemStack.getItem().getMaterialForRepairs().getMinHarvestLevel() - block.blockMaterial.getMinHarvestLevel();
         if (deltaLevel > 0) {
            commonModifierValue += (float)deltaLevel * unnaturalModifierValue;
         }
      }
      float enhanceModifierValue = (float) (1 + itemStack.getEnhanceFactor());

       if (!player.isInWater() && !player.isInRain())
           return this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue) * enhanceModifierValue;
       return this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue + ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierValue(itemStack.getTagCompound()));
   }

   @ModifyReturnValue(method = "getToolDecayFromAttackingEntity", at = @At("RETURN"))
   public final int getToolDecayFromAttackingEntity(int original, ItemStack item_stack, EntityLivingBase livingBase) {
      return this.applyCalculateDurabilityModifier(original, item_stack);
   }

   @Shadow
   public int getToolDecayFromBreakingBlock(BlockBreakInfo info) {
      return 0;
   }

   @Shadow
   public boolean isEffectiveAgainstBlock(Block block, int metadata) {
      return false;
   }

   @Shadow public abstract float getMaterialHarvestEfficiency();

   @Inject(method = "onBlockDestroyed", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockBreakInfo;getHarvesterItemStack()Lnet/minecraft/ItemStack;", ordinal = 0, shift = At.Shift.BEFORE))
   public void injectTriggerGeology(BlockBreakInfo info, CallbackInfoReturnable<Boolean> cir) {
      Block block = info.block;
      if (!(block instanceof BlockOre && info.getMetadata() == 1) && block != Block.oreDiamond && block != Block.oreCoal && block != Block.oreEmerald && block != Block.oreRedstone && block != Block.oreLapis && block != Block.oreNetherQuartz){
         float expReward = ToolModifierTypes.GEOLOGY.getModifierValue(info.getHarvesterItemStack().getTagCompound());
         if (expReward != 0){
            ItemStack dropItemStack = new ItemStack(info.block);
            ItemStack smeltingResult = FurnaceRecipes.smelting().getSmeltingResult(dropItemStack, 5);
            if (smeltingResult != null){
               info.world.spawnEntityInWorld(new EntityXPOrb(info.world, info.drop_x, info.drop_y + 0.5D, info.drop_z, (int) (smeltingResult.getExperienceReward() * expReward)));
            }
         }
      }
   }

   @Inject(method = "onBlockDestroyed", at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockBreakInfo;getHarvesterItemStack()Lnet/minecraft/ItemStack;", ordinal = 1, shift = At.Shift.BEFORE))
   private void injectAddToolExp(@NotNull BlockBreakInfo info, CallbackInfoReturnable<Boolean> cir){
      ItemStack item_stack = info.getHarvesterItemStack();
      if (!(item_stack.getItem() instanceof ItemSword) && this.isEffectiveAgainstBlock(info.block, info.getMetadata()) &&!item_stack.getItem().isMaxToolLevel(item_stack)) {
         this.addExpForTool(info.getHarvesterItemStack(), info.getResponsiblePlayer(), this.getExpForBlockBreak(info));
      }
   }

   @Unique
   protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo){
      return blockBreakInfo.block.blockMaterial.getMinHarvestLevel();
   }

   //Client+Server
   @Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;setHeldItemInUse()Z"), cancellable = true)
   public void onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> cir) {
      if (this.getItemInUseAction(player.getHeldItemStack(), player) == EnumItemInUseAction.BLOCK) {
         if (player.canDefense()){
            player.setHeldItemInUse();
            cir.setReturnValue(true);
            return;
         }
      }
      cir.setReturnValue(false);
   }

   @Override
   public void onItemUseFinish(ItemStack item_stack, World world, EntityPlayer player) {
//      super.onItemUseFinish(item_stack, world, player);
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
