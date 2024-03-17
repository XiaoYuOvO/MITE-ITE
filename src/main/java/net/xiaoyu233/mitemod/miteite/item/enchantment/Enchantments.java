package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.EnumRarity;
import net.xiaoyu233.fml.reload.event.EnchantmentRegistryEvent;
import net.xiaoyu233.fml.util.IdUtil;
import net.xiaoyu233.mitemod.miteite.api.ITEEnchantment;

import java.util.ArrayList;
import java.util.List;

public class Enchantments {
    public static final Enchantment DEFENCED = new EnchantmentDefence(getNextEnchantmentID(), EnumRarity.rare,20);
    public static final Enchantment CRIT = new EnchantmentCrit(getNextEnchantmentID(),EnumRarity.rare, 10);
    public static final Enchantment EXTEND = new EnchantmentExtend(getNextEnchantmentID(),EnumRarity.rare, 15);
    public static final Enchantment EMERGENCY = new EnchantmentEmergency(getNextEnchantmentID(),EnumRarity.rare,15);
    public static final Enchantment CONQUEROR = new EnchantmentConqueror(getNextEnchantmentID(),EnumRarity.rare,15);
    public static final Enchantment BEHEADING = new EnchantmentBeheading(getNextEnchantmentID(),EnumRarity.rare,10);
    public static final List<Enchantment> individualEnchantments = new ArrayList<>();
    public static void registerEnchantments(EnchantmentRegistryEvent event){
        event.registerEnchantment(DEFENCED,CRIT,EXTEND,EMERGENCY,CONQUEROR,BEHEADING);
        for (Enchantment enchantment : Enchantment.enchantmentsList) {
            if (enchantment != null && !Enchantments.individualEnchantments.contains(enchantment)){
                if (enchantment.enchantIndividually()){
                    Enchantments.individualEnchantments.add(enchantment);
                }
            }
        }
    }


    private static int getNextEnchantmentID(){
        return IdUtil.getNextEnchantmentID();
    }
}
