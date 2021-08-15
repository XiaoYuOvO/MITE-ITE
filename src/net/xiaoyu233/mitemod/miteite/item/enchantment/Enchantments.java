package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.Enchantment;
import net.minecraft.yq;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.Enchantment.enchantmentsList;

public class Enchantments {
    public static final Enchantment DEFENCED = new EnchantmentDefence(getNextEnchantmentID(), yq.c,20);
    public static final Enchantment CRIT = new EnchantmentCrit(getNextEnchantmentID(),yq.c, 10);
    public static final Enchantment EXTEND = new EnchantmentExtend(getNextEnchantmentID(),yq.c, 15);
    public static final Enchantment EMERGENCY = new EnchantmentEmergency(getNextEnchantmentID(),yq.c,15);
    public static final Enchantment CONQUEROR = new EnchantmentConqueror(getNextEnchantmentID(),yq.c,15);
    public static final Enchantment BEHEADING = new EnchantmentBeheading(getNextEnchantmentID(),yq.c,10);
    public static List<Enchantment> individualEnchantments = new ArrayList<>();
    public static void registerEnchantments(){
        Enchantments.registerEnchantmentsUnsafe(DEFENCED,CRIT,EXTEND,EMERGENCY,CONQUEROR,BEHEADING);
    }

    public static void registerEnchantmentsUnsafe(Enchantment... enchantments) {
        for (int i = 0, bLength = enchantmentsList.length; i < bLength; i++) {
            if (enchantmentsList[i] == null) {
                for (int j = 0, enchantmentsLength = enchantments.length; j < enchantmentsLength; j++) {
                    enchantmentsList[i + j] = enchantments[j];
                }
                break;
            }
        }
        ArrayList<Enchantment> var0 = new ArrayList<>();
        for (Enchantment enchantment : enchantmentsList) {
            if (enchantment != null) {
                var0.add(enchantment);
            }
        }
        try {
            Field enchantmentsBookList = Enchantment.class.getDeclaredField("enchantmentsBookList");
            ReflectHelper.updateFinalModifiers(enchantmentsBookList);
            enchantmentsBookList.setAccessible(true);
            enchantmentsBookList.set(null,var0.toArray(new Enchantment[0]));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    private static int getNextEnchantmentID(){
        return Constant.nextEnchantmentID++;
    }
}
