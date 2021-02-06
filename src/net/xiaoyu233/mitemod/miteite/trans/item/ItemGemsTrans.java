package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EntityHuman;
import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(ItemRock.class)
public class ItemGemsTrans extends Item {
    public static int getExperienceValueWhenSacrificed(ItemStack item_stack) {
        Item item = item_stack.b();
        if (item == Item.aY && item_stack.getItemSubtype() == 4) {
            //MODDED
            return 40;
        } else {
            //Make quartz less
            return item == cb ? 35 : (item == bJ ? 250 : (item == p ? MITEITEMod.CONFIG.get(Config.ConfigEntry.DIAMOND_EXP) : 0));
        }
    }

    public static boolean onItemRightClick(EntityHuman player, ItemStack item_stack, float partial_tick, boolean ctrl_is_down) {
        int xp_value = getExperienceValueWhenSacrificed(item_stack);
        if (xp_value < 1) {
            return false;
        } else {
            if (player.onServer()) {
                if (item_stack.b() == cb && player.getExperienceLevel() >= MITEITEMod.CONFIG.get(Config.ConfigEntry.QUARTZ_MAX_EXP_LEVEL)){
                    return false;
                }
                player.causeBreakingItemEffect(item_stack.b(), player.bn.c);
                player.convertOneOfHeldItem(null);
                player.s(xp_value);
            } else {
                player.bobItem();
            }

            return true;
        }
    }
}
