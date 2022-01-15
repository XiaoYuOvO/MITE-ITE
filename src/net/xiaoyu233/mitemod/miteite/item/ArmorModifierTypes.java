package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public enum ArmorModifierTypes implements ItemModifierTypes{
    //Armor Modifiers
    DURABILITY_MODIFIER(0.1F,"持久",EnumChatFormat.DARK_PURPLE,20,5,(stack -> true)),
    PROJECTILE_PROTECTION_MODIFIER(0.5F,"弹射物保护", EnumChatFormats.DARK_GREY,7,5,(stack -> hasNotOtherProtectionModifier(stack,3))),
    EXPLOSION_PROTECTION_MODIFIER(0.5F,"爆炸保护", EnumChatFormats.DARK_RED,7,5,(stack -> hasNotOtherProtectionModifier(stack,2))),
    FIRE_PROTECTION_MODIFIER(0.5F,"火焰保护", EnumChatFormats.LIGHT_ORANGE,7,5,(stack -> hasNotOtherProtectionModifier(stack,0))),
    PROTECTION_MODIFIER(0.25F,"保护",EnumChatFormat.WHITE,5,5,(stack -> hasNotOtherProtectionModifier(stack,1))),
    STEADY_MODIFIER(0.15F,"稳定",EnumChatFormat.DARK_GRAY,8,5,(stack -> true)),
    BLESSED_MODIFIER(1F,"神圣",EnumChatFormats.SILVER,8,5,(stack -> true)),
    INDOMITABLE(0.25f,"坚毅不倒",EnumChatFormats.LIGHT_YELLOW_GREEN,5,4,itemStack -> itemStack.getItem() instanceof ItemCuirass),
    IMMUNITY(0.15f,"免疫",EnumChatFormat.GREEN,3,4,itemStack -> itemStack.getItem() instanceof ItemHelmet);
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack>  canApplyTo;
    private final int maxLevel;

    ArmorModifierTypes(float levelAddition, String displayName, EnumChatFormat color, int weight,int maxLevel, Predicate<ItemStack> canApplyTo){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = color;
        this.weight = weight;
        this.canApplyTo = canApplyTo;
        this.maxLevel = maxLevel;
    }

    private static boolean hasNotOtherProtectionModifier(ItemStack stack,int protectionType){
        switch (protectionType){
            //FIRE_PROTECTION_MODIFIER
            case 0:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER);
            //PROTECTION_MODIFIER
            case 1:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //EXPLOSION_PROTECTION_MODIFIER
            case 2:
                return !ItemModifierTypes.hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //PROJECTILE_PROTECTION_MODIFIER
            case 3:
                return !ItemModifierTypes.hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && !ItemModifierTypes.hasModifier(stack,PROTECTION_MODIFIER) && ! ItemModifierTypes.hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            default:
                return true;
        }

    }

    @Override
    public boolean canApplyTo(ItemStack stack) {
        return this.canApplyTo.test(stack);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag);
    }

    public int getModifierLevel(@Nullable NBTTagCompound itemTag) {
        int lvl = 0;
        if (itemTag != null && itemTag.hasKey("modifiers")) {
            NBTTagCompound modifiers = itemTag.getCompoundTag("modifiers");
            if (modifiers.hasKey(this.nbtName)) {
                lvl = modifiers.getInteger(this.nbtName);
            }
        }

        return lvl;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }
}
