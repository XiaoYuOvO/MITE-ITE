package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Transform(ItemTool.class)
public class ItemToolTrans extends Item {
    @Link
    protected List blocks_effective_against = new ArrayList();
    @Link
    protected List materials_effective_against = new ArrayList();
    @Link
    private float d;
    @Link
    private Material effective_material;

    public int getExpReqForLevel(int level,boolean isSword) {
        return isSword ? (400 * level) : (200 * level);
    }

    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLivingBase, EntityLiving par3EntityLivingBase) {

        if (par2EntityLivingBase.onClient()) {
            Minecraft.setErrorMessage("ItemTool.hitEntity: called on client?");
        }

        if (par3EntityLivingBase instanceof EntityHuman && ((EntityHuman) par3EntityLivingBase).bG.d) {
            return false;
        } else {
            par1ItemStack.tryDamageItem(DamageSource.j,
                    this.getToolDecayFromAttackingEntity(par1ItemStack, par2EntityLivingBase), par3EntityLivingBase);
            return true;
        }
    }

    public EnumItemInUseAction getItemInUseAction(ItemStack par1ItemStack, EntityHuman player) {
        //Make tools cannot block damage
        return EnchantmentManager.hasEnchantment(par1ItemStack, Enchantments.DEFENCED) ? EnumItemInUseAction.BLOCK : null;
    }

    public boolean onItemRightClick(EntityHuman player, float partial_tick, boolean ctrl_is_down) {
        if (this.getItemInUseAction(player.getHeldItemStack(),player) == EnumItemInUseAction.BLOCK) {
            player.setHeldItemInUse();
            return true;
        } else {
            return false;
        }
    }

    private boolean canBlock() {
        return false;
    }

    //getUseDuration
    public int d_(ItemStack par1ItemStack) {
        return 72000;
    }

    @Override
    public float getStrVsBlock(Block block, int metadata,ItemStack itemStack,EntityHuman player) {
        return this.isEffectiveAgainstBlock(block, metadata) ? this.getMultipliedHarvestEfficiency(block,
                itemStack,player) : super.getStrVsBlock(block, metadata);
    }

    @Marker
    public boolean isEffectiveAgainstBlock(Block block, int metadata) {
        return false;
    }

    @Override
    public void addInformation(ItemStack item_stack, EntityHuman player, List info, boolean extended_info, Slot slot) {
        super.addInformation(item_stack, player, info, extended_info, slot);
        if (item_stack.p()) {
            if (item_stack.q().b("tool_level")) {
                if (this.isMaxToolLevel(item_stack)) {
                    info.add("工具等级:§6已达到最高级" + item_stack.q().e("tool_level"));
                } else {
                    info.add("工具等级:" + item_stack.q().e("tool_level"));
                    if (item_stack.q().b("tool_exp")) {
                        info.add("工具经验" + EnumChatFormat.p + item_stack.q().e("tool_exp") + "/" + getExpReqForLevel(
                                item_stack.q().e("tool_level") + 1, isWeapon(item_stack.b())));
                    }
                }
            }
            int forgingGrade;
            if (item_stack.q().b("forging_grade") && ((forgingGrade = item_stack.q().e("forging_grade")) != 0)){
                info.add("§5强化等级:§6" +  LocaleI18n.a("enchantment.level." + forgingGrade));
                if (extended_info){
                    info.add("  §7耐久增加:§a" + 10 * forgingGrade + "%");
                    info.add("  §9攻击力增加:§6" + ItemStack.a.format(this.getEnhancedDamage(item_stack)));
                }
            }

            if (extended_info) {
                NBTTagCompound compound = item_stack.e.l("modifiers");
                if (!compound.d()) {
                    info.add("工具强化:");
                    for (ToolModifierTypes value : ToolModifierTypes.values()) {
                        if (compound.b(value.nbtName)) {
                            info.add("  " + value.color.toString() + value.displayName + "§r " + LocaleI18n.a(
                                    "enchantment.level." + compound.e(value.nbtName)));
                        }
                    }
                }
            }
        }
    }

    private float getEnhancedDamage(ItemStack itemStack){
        return (float) (itemStack.getEnhanceFactor() * this.getCombinedDamageVsEntity());
    }

    @Override
    public int getMaxDamage(ItemStack item_stack) {
        return Math.round(super.getMaxDamage(item_stack) * (1 + 0.1f*item_stack.getForgingGrade()));
    }

    @Override
    public boolean isMaxToolLevel(ItemStack itemStack) {
        return this.getMaterialHarvestLevel() * 4 <= this.getToolLevel(itemStack);
    }

    public boolean isWeapon(Item item){
        return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer;
    }

    @Marker
    public int getMaterialHarvestLevel() {
        return 0;
    }

    public static boolean isWeapon(ItemStack stack){
        Item item = stack.b();
        return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer;
    }

    @Override
    public boolean onBlockDestroyed(BlockBreakInfo info) {
        if (info.world.I) {
            Minecraft.setErrorMessage("ItemTool.onBlockDestroyed: called on client?");
        }

        Block block = info.block;
        ItemStack item_stack = info.getHarvesterItemStack();
        if (item_stack.g() && !block.isPortable(info.world, info.getHarvester(), info.x, info.y,
                info.z) && !info.isResponsiblePlayerInCreativeMode() && info.getBlockHardness() > 0.0F && this.getStrVsBlock(
                block, info.getMetadata()) > 1.0F) {
            if (!(item_stack.b() instanceof  ItemSword) && this.isEffectiveAgainstBlock(info.block, info.getMetadata()) && !item_stack.b().isMaxToolLevel(item_stack)) {
                this.addExpForTool(info.getHarvesterItemStack(), info.getResponsiblePlayer(), 1);
            }
            info.getHarvesterItemStack().tryDamageItem(DamageSource.j,
                    applyCalculateDurabilityModifier(this.getToolDecayFromBreakingBlock(info),
                            info.getHarvesterItemStack()), info.getHarvester());
            return true;
        } else {
            return false;
        }
    }

    public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
        Multimap<String, AttributeModifier> var1 = super.getAttrModifiers(stack);
        var1.put(GenericAttributes.e.a(),
                new AttributeModifier(Item.e, "Tool modifier", (double) this.d + this.getAttackDamageBounce(stack) + this.getEnhancedDamage(stack), 0));
        return var1;
    }

    @Override
    public float getMeleeDamageBonus(ItemStack stack) {
        return this.getCombinedDamageVsEntity() + ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.e) + this.getEnhancedDamage(stack);
    }

    @Override
    public void onItemLevelUp(NBTTagCompound tagCompound, EntityHuman player, ItemStack stack) {
        NBTTagCompound modifiers = tagCompound.l("modifiers");
        ToolModifierTypes modifierType = ToolModifierTypes.getModifierWithWeight(player.aD(),stack);
        if (modifierType != null) {
            if (modifiers.b(modifierType.nbtName)) {
                player.a(ChatMessage.e(
                        "你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers,modifierType) + "级"));
            } else {
                this.addModifierLevelFor(modifiers, modifierType);
                player.a(ChatMessage.e("你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
            }
        }
    }

    private int applyCalculateDurabilityModifier(int origin, ItemStack stack) {
        NBTTagCompound compound = stack.q();
        return (int) (origin * Math.max(0, 1 - ToolModifierTypes.DURABILITY_MODIFIER.getModifierValue(compound)));
    }

    private float getAttackDamageBounce(ItemStack stack) {
        return ToolModifierTypes.DAMAGE_MODIFIER.getModifierValue(stack.e);
    }

    @Marker
    private float getBaseHarvestEfficiency(Block block) {
        return 0;
    }

    @Marker
    private float getCombinedDamageVsEntity() {
        return 0;
    }

    public float getMaterialHarvestEfficiency() {
        if (this.effective_material == Material.d) {
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
        } else if (this.effective_material == Material.f) {
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

    public boolean hasExpAndLevel(){
        return true;
    }

    public final float getMultipliedHarvestEfficiency(Block block, ItemStack itemStack,EntityHuman player) {
        float commonModifierValue = ToolModifierTypes.EFFICIENCY_MODIFIER.getModifierValue(itemStack.q());
        float unnaturalModifierValue = ToolModifierTypes.UNNATURAL_MODIFIER.getModifierValue(itemStack.q());
        if (unnaturalModifierValue > 0){
            int deltaLevel = itemStack.b().getMaterialForRepairs().getMinHarvestLevel() - block.cU.getMinHarvestLevel();
            if (deltaLevel > 0){
                commonModifierValue += deltaLevel * unnaturalModifierValue;
            }
        }
        if (player.H() || player.isInRain()) {
            return this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue + ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierValue(itemStack.q()));
        }
        return this.getBaseHarvestEfficiency(block) * (this.getMaterialHarvestEfficiency() + commonModifierValue);
    }

    public final int getToolDecayFromAttackingEntity(ItemStack item_stack, EntityLiving entity_living_base) {
        return applyCalculateDurabilityModifier(Math.max((int)(100.0F * dyCast(ItemTool.class,this).getBaseDecayRateForAttackingEntity(item_stack)), 1),item_stack);
    }

    @Marker
    private int getToolDecayFromBreakingBlock(BlockBreakInfo info) {
        return 0;
    }
}
