package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;

import javax.annotation.Nullable;

public class RecipesHelper {
    public static void checkRecipe(Item item, int subtype_or_0, @Nullable Object marker) {
        if ((item.isCraftingProduct() || item.isRepairable()) && item.getLowestCraftingDifficultyToProduce() == 3.4028235E38F) {
            if (item.hasMaterial(Material.rusted_iron)) {
                Item peer;
                if (item instanceof ItemArmor) {
                    peer = ItemArmor.getMatchingArmor(item.getClass(), Material.copper, item.isChainMail());
                } else {
                    peer = Item.getMatchingItem(item.getClass(), Material.copper);
                }

                if (peer != null) {
                    item.setLowestCraftingDifficultyToProduce(peer.getLowestCraftingDifficultyToProduce());
                }
            }

            if (item.getLowestCraftingDifficultyToProduce() == 3.4028235E38F) {
                Minecraft.setErrorMessage("Warning: " + item.getItemDisplayName(null) + " [" + item.itemID + "] is " + (item.isCraftingComponent(subtype_or_0) ? "a crafting product" : "repairable") + " but its lowest_crafting_difficulty_to_produce cannot be determined");
            }
        }

        if (item.isCraftingComponent(subtype_or_0) && item.getCraftingDifficultyAsComponent(new ItemStack(item, 1, subtype_or_0)) < 0.0F) {
            float lowest_crafting_difficulty_to_produce = item.getLowestCraftingDifficultyToProduce();
            if (lowest_crafting_difficulty_to_produce != 3.4028235E38F) {
                item.setCraftingDifficultyAsComponent(lowest_crafting_difficulty_to_produce);
            } else {
                Minecraft.setErrorMessage("Warning: " + item.getItemDisplayName(null) + " [" + item.itemID + "] is a crafting component but its crafting_difficulty_as_component has not been set");
            }
        }

    }
}
