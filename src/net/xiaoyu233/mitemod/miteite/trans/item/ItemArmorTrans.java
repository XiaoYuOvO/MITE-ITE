package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import java.util.List;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@SuppressWarnings("ALL")
@Transform(ItemArmor.class)
public class ItemArmorTrans extends Item implements IDamageableItem {
    @Link
    private Material effective_material;
    @Link
    private boolean is_chain_mail;
    @Link("delay")
    public int slotIndex;


    public static float getProtectionForDamage(ItemStack armor, DamageSource damageSource, EntityLiving owner){
        //Fire
       if (damageSource.m()){
           return ArmorModifierTypes.FIRE_PROTECTION_MODIFIER.getModifierValue(armor.e);
       }else /*Explosion*/if (damageSource.c()){
           return ArmorModifierTypes.EXPLOSION_PROTECTION_MODIFIER.getModifierValue(armor.e);
       }/*Projectile*/if (damageSource.a()){
           return ArmorModifierTypes.PROJECTILE_PROTECTION_MODIFIER.getModifierValue(armor.e);
        }else {
           return 0f;
        }
    }
    public static float getTotalArmorProtection(ItemStack[] armors, DamageSource damage_source, boolean include_enchantments, EntityLiving owner) {
        float total_defense = 0.0F;
        if (damage_source != null && damage_source.e()) {
            return total_defense;
        } else {
            if (damage_source == null || !damage_source.bypassesMundaneArmor()) {
                for (ItemStack item_stack : armors) {
                    if (item_stack != null) {
                        Item item = item_stack.b();
                        if (item instanceof ItemHorseArmor) {
                            ItemHorseArmor barding = (ItemHorseArmor) item;
                            total_defense += (float) barding.getProtection();
                        } else if (item.isArmor()) {
                            ItemArmor armor = (ItemArmor) item_stack.b();
                            if (damage_source != null){
                                if (damage_source.getResponsibleEntity() instanceof EntityLiving  && ((EntityLiving) damage_source.getResponsibleEntity()).aM()) {
                                    total_defense += ArmorModifierTypes.BLESSED_MODIFIER.getModifierValue(item_stack.e);
                                }
                                total_defense += getProtectionForDamage(item_stack, damage_source, owner);
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
        } else if (this.effective_material != Material.f && this.effective_material != Material.ancient_metal) {
            if (this.effective_material == Material.mithril) {
                protection = 9;
            } else {
                if (this.effective_material == Material.adamantium) {
                    protection = 10;
                } else if (this.effective_material == Materials.vibranium){
                    protection = 15;
                }else{
                    return 0;
                }

            }
        } else {
            protection = 8;
        }

        if (this.is_chain_mail) {
            protection -= 2;
        }

        return protection;
    }

    public float getMultipliedProtection(ItemStack item_stack) {
        float multiplied_protection = (float)(this.getNumComponentsForDurability() * this.getMaterialProtection()) / 24.0F;
        if (item_stack != null && item_stack.hasEnchantment(Enchantment.d, false)) {
            multiplied_protection += multiplied_protection * item_stack.getEnchantmentLevelFraction(Enchantment.d) * 0.5F;
        }
        if (item_stack != null && item_stack.e != null) {
            float protection_modifier = ArmorModifierTypes.PROTECTION_MODIFIER.getModifierValue(item_stack.e);
            if (protection_modifier > 0){
                multiplied_protection += protection_modifier;
            }
        }
        return multiplied_protection;
    }

    @Override
    @Marker
    public int getRepairCost() {
        return 0;
    }

    @Marker
    public int getNumComponentsForDurability() {
        return 0;
    }

    public void addInformation(ItemStack itemStack, EntityHuman player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            info.add("");
            float protection = this.getProtectionAfterDamageFactor(itemStack, player);
            int decimalPlaces = protection < 1.0F ? 2 : 1;
            info.add(EnumChatFormat.j + Translator.getFormatted("item.tooltip.protectionBonus", new Object[]{StringHelper.formatFloat(protection, decimalPlaces, decimalPlaces)}));
        }
        if (itemStack.p()) {
            int toolLevel = itemStack.q().e("tool_level");
            if (itemStack.q().b("tool_level")) {
                if (this.isMaxToolLevel(itemStack)) {
                    info.add("装备等级:§6已达到最高级" + toolLevel);
                }else{
                    info.add("装备等级:" + toolLevel);
                    if (itemStack.q().b("tool_exp")) {
                        info.add("装备经验" + EnumChatFormat.p + itemStack.q().e("tool_exp") + "/" + this.getExpReqForLevel(
                                toolLevel+ 1,slotIndex,dyCast(this)));
                    }
                }
            }
            if (extended_info) {
                NBTTagCompound compound = itemStack.e.l("modifiers");
                if (!compound.d()) {
                    info.add("装备强化:");
                    for (ArmorModifierTypes value : ArmorModifierTypes.values()) {
                        if (compound.b(value.nbtName)) {
                            info.add("  " + value.color.toString() + value.displayName + "§r " + LocaleI18n.a(
                                    "enchantment.level." + compound.e(value.nbtName)));
                        }
                    }
                }
            }
        }
    }

    private int getExpReqForLevel(int tool_level,int slotIndexl,ItemArmor armor) {
        switch (slotIndexl){
            case 0:
                return (int) (1.75f * 4 * armor.getMaterialProtection() * tool_level);
            case 1:
                return (int) (2.5f * 4 * armor.getMaterialProtection() * tool_level);
            case 2:
                return (int) (2.25 * 4 * armor.getMaterialProtection() * tool_level);
            case 3:
                return (int) (1.5f * 4 * armor.getMaterialProtection() * tool_level);
            default:
                return 64 * tool_level;
        }

    }

    @Override
    public boolean isMaxToolLevel(ItemStack itemStack) {
        return this.getMaterialForDurability().getMinHarvestLevel() * 3 <= this.getToolLevel(itemStack);
    }

    @Marker
    private float getProtectionAfterDamageFactor(ItemStack itemStack, EntityLiving owner) {
        return 0;
    }

    @Override
    public void onItemLevelUp(NBTTagCompound tagCompound, EntityHuman player, ItemStack stack) {
        NBTTagCompound modifiers = tagCompound.l("modifiers");
        ArmorModifierTypes modifierType = ArmorModifierTypes.getModifierWithWeight(player.aD(),stack);
        if (modifierType != null) {
            if (modifiers.b(modifierType.nbtName)) {
                player.a(ChatMessage.e(
                        "你的" + stack.getMITEStyleDisplayName() + "的" + modifierType.color.toString() + modifierType.displayName + "§r属性已升级到" + this.addModifierLevelFor(modifiers,modifierType) + "级"));
            } else {
                this.addModifierLevelFor(modifiers,modifierType);
                player.a(ChatMessage.e(
                        "你的" + stack.getMITEStyleDisplayName() + "获得了" + modifierType.color.toString() + modifierType.displayName + "§r属性"));
            }
        }
        player.suppressNextStatIncrement();
    }

    @Override
    public int getExpReqForLevel(int i, boolean weapon) {
        return this.getExpReqForLevel(i,slotIndex,dyCast(this));
    }

    @Override
    public boolean isWeapon(Item b) {
        return false;
    }

    public boolean hasExpAndLevel() {
        return true;
    }
}
