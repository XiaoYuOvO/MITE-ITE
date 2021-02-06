package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.HashMap;
import java.util.Map;

@Transform(RecipesFurnace.class)
public class RecipesFurnaceTrans {
    @Link
    private final Map b = new HashMap();
    public ItemStack getSmeltingResult(ItemStack input_item_stack, int heat_level) {
        if (input_item_stack == null) {
            return null;
        } else {
            int input_item_id = input_item_stack.d;
            if (heat_level == -1) {
                return (ItemStack)this.b.get(input_item_id);
            } else {
                ItemStack result_item_stack;
                if (input_item_id == Block.J.cF) {
                    result_item_stack = (heat_level != 1 || input_item_stack.b >= 4) && input_item_stack.b >= 4 ? new ItemStack(heat_level == 1 ? Block.V : Block.R) : null;
                } else if (input_item_id == Block.an.cF){
                    result_item_stack = heat_level >= 4 && input_item_stack.b >= 4 ? new ItemStack(Item.ingotMithril) : null;
                }else if (input_item_id == Block.blockMithril.cF){
                    result_item_stack = heat_level >= 4 && input_item_stack.b >= MITEITEMod.CONFIG.get(Config.ConfigEntry.MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM) ? new ItemStack(Item.ingotMithril) : null;
                }else {
                    result_item_stack = (ItemStack)this.b.get(input_item_id);
                }

                return heat_level < TileEntityFurnace.getHeatLevelRequired(input_item_stack.d) ? null : result_item_stack;
            }
        }
    }
    public boolean doesSmeltingRecipeExistFor(ItemStack input_item_stack) {
        return this.b.get(input_item_stack.d) != null || (input_item_stack.isBlock() && input_item_stack.getItemAsBlock().getBlock().cF == Block.an.cF);
    }
}
