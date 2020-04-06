package net.xiaoyu233.mitemod.miteite.item;

import com.google.common.collect.Lists;
import net.minecraft.EnumChatFormat;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public enum ArmorModifierTypes implements ItemModifierTypes{
    //Armor Modifiers
    DURABILITY_MODIFIER(0.1F,"持久",EnumChatFormat.f,20,(stack -> true)),
    PROJECTILE_PROTECTION_MODIFIER(0.5F,"弹射物保护", EnumChatFormats.DARK_GREY,7,(stack -> hasNotOtherProtectionModifier(stack,3))),
    EXPLOSION_PROTECTION_MODIFIER(0.5F,"爆炸保护", EnumChatFormats.DARK_RED,7,(stack -> hasNotOtherProtectionModifier(stack,2))),
    FIRE_PROTECTION_MODIFIER(0.5F,"火焰保护", EnumChatFormats.LIGHT_ORANGE,7,(stack -> hasNotOtherProtectionModifier(stack,0))),
    PROTECTION_MODIFIER(0.25F,"保护",EnumChatFormat.p,5,(stack -> hasNotOtherProtectionModifier(stack,1))),
    STEADY_MODIFIER(0.15F,"稳定",EnumChatFormat.i,8,(stack -> true)),
    BLESSED_MODIFIER(1F,"神圣",EnumChatFormats.SILVER,8,(stack -> true));

    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack>  canApplyTo;
    ArmorModifierTypes(float levelAddition, String displayName, EnumChatFormat color, int weight, Predicate<ItemStack> canApplyTo){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = color;
        this.weight = weight;
        this.canApplyTo = canApplyTo;
    }

    private static  boolean hasNotOtherProtectionModifier(ItemStack stack,int protectionType){
        switch (protectionType){
            //FIRE_PROTECTION_MODIFIER
            case 0:
                return !hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! hasModifier(stack,PROTECTION_MODIFIER);
            //PROTECTION_MODIFIER
            case 1:
                return !hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && ! hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //EXPLOSION_PROTECTION_MODIFIER
            case 2:
                return !hasModifier(stack,PROJECTILE_PROTECTION_MODIFIER) && !hasModifier(stack,PROTECTION_MODIFIER) && ! hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            //PROJECTILE_PROTECTION_MODIFIER
            case 3:
                return !hasModifier(stack,EXPLOSION_PROTECTION_MODIFIER) && !hasModifier(stack,PROTECTION_MODIFIER) && ! hasModifier(stack,FIRE_PROTECTION_MODIFIER);
            default:
                return true;
        }

    }

    public static boolean hasModifier(ItemStack stack,ArmorModifierTypes modifierType){
        NBTTagCompound itemTag = stack.e;
        return itemTag != null && itemTag.b("modifiers") && itemTag.l("modifiers").b(modifierType.nbtName);
    }

    @Nullable
    public static ArmorModifierTypes getModifierWithWeight(Random rand,ItemStack stack){
        ArrayList<ArmorModifierTypes> p1801660 = ArmorModifierTypes.getAllCanBeApplied(stack);
        int totalWeight = 0;
        for (ArmorModifierTypes modifierTypes : p1801660) {
            totalWeight += modifierTypes.weight;
        }
        int currentWeight = rand.nextInt(totalWeight);
        int x = 0;
        for(int j = p1801660.size(); x < j; ++x) {
            ArmorModifierTypes t = p1801660.get(x);
            currentWeight -= t.weight;
            if (currentWeight < 0) {
                return t;
            }
        }

        return null;
    }

    private static ArrayList<ArmorModifierTypes> getAllCanBeApplied(ItemStack stack){
        ArrayList<ArmorModifierTypes> toolModifierTypes  = Lists.newArrayList(ArmorModifierTypes.values());
        toolModifierTypes.removeIf(modifierType -> !modifierType.canApplyTo.test(stack));
        return toolModifierTypes;
    }

    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag);
    }

    public int getModifierLevel(NBTTagCompound itemTag){
        int lvl = 0;
        if (itemTag != null && itemTag.b("modifiers")) {
            NBTTagCompound modifiers = itemTag.l("modifiers");
            if (modifiers.b(this.nbtName)) {
                lvl = modifiers.e(this.nbtName);
            }
        }
        return lvl;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }
}
