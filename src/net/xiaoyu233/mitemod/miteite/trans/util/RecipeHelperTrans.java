package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Transform;

import static net.minecraft.RecipeHelper.hasAsComponent;

@Transform(RecipeHelper.class)
public class RecipeHelperTrans {
    //fix crash
    public static void addRecipe(aah recipe, boolean include_in_lowest_crafting_difficulty_determination) {
        try {
            if (include_in_lowest_crafting_difficulty_determination) {
                recipe.setIncludeInLowestCraftingDifficultyDetermination();
            }

            ItemStack[] recipe_items = recipe.getComponents();
            ItemStack recipe_output = recipe.b();
            Item output_item = recipe_output.b();
            output_item.setAsCraftingProduct();
            output_item.recipes[output_item.num_recipes++] = recipe;
            float difficulty = 0.0F;

            ItemStack output_item_stack;
            float highest_durability_that_is_less_than_tool_material;
            for (ItemStack recipe_item : recipe_items) {
                output_item_stack = recipe_item;
                if (output_item_stack != null) {
                    Item item = output_item_stack.b();
                    if (item.n() && output_item_stack.getItemSubtype() != 32767) {
                        output_item_stack.setAsComponentOfCraftingProduct(recipe_output);
                    } else {
                        item.setAsComponentOfCraftingProduct(recipe_output);
                    }

                    float component_difficulty = output_item_stack.getCraftingDifficultyAsComponent();
                    if (component_difficulty < 0.0F) {
                        highest_durability_that_is_less_than_tool_material = output_item_stack.b().getLowestCraftingDifficultyToProduce();
                        if (highest_durability_that_is_less_than_tool_material != Float.MAX_VALUE) {
                            output_item_stack.b().setCraftingDifficultyAsComponent(highest_durability_that_is_less_than_tool_material);
                            component_difficulty = highest_durability_that_is_less_than_tool_material;
                        } else if (item.hasMaterial(Material.rusted_iron)) {
                            Item peer = Item.getMatchingItem(item.getClass(), Material.copper);
                            if (peer != null) {
                                if (item.getMaterialForDurability() == null) {
                                    Minecraft.setErrorMessage("addRecipe: getMaterialForDurability()==null for component " + item);
                                }

                                item.setCraftingDifficultyAsComponent(peer.getCraftingDifficultyAsComponent(null) * item.getMaterialForDurability().getDurability() / peer.getMaterialForDurability().getDurability());
                                component_difficulty = item.getCraftingDifficultyAsComponent(null);
                            }
                        }
                    }

                    if (component_difficulty < 0.0F) {
                        Minecraft.setErrorMessage("Warning: recipe for " + recipe_output.s() + ", component crafting difficulty not set: " + output_item_stack.b().l(output_item_stack) + " [" + (output_item_stack.d - 256) + "]");
                    } else {
                        difficulty += component_difficulty;
                    }
                }
            }

            if (difficulty < 0.0F) {
                Minecraft.setErrorMessage("addRecipe: recipe output cannot have a crafting difficulty < 0.0F");
            }

            recipe.setDifficulty(difficulty);
            if (recipe_output.b < 1) {
                Minecraft.setErrorMessage("stackSize is 0 for recipe output (" + recipe_output.s() + ")");
            }

            output_item_stack = recipe.b();
            if (output_item_stack != null) {
                output_item = output_item_stack.b();
                if (output_item.containsMetal()) {
                    if (output_item instanceof ItemIngot) {
                        return;
                    }

                    if (output_item.getClass() == ItemKnife.class) {
                        return;
                    }

                    if (output_item.getClass() == ItemBucket.class && hasAsComponent(recipe, ItemBucket.class)) {
                        return;
                    }

                    recipe.setMaterialToCheckToolBenchHardnessAgainst(output_item.getHardestMetalMaterial());
                } else if (output_item.cv == Block.C.cF) {
                    recipe.setMaterialToCheckToolBenchHardnessAgainst(Material.d);
                } else if (output_item.cv == Block.aD.cF) {
                    Material tool_material = BlockWorkbench.getToolMaterial(output_item_stack.getItemSubtype());
                    if (tool_material.isMetal()) {
                        Material next_strongest_material = null;
                        highest_durability_that_is_less_than_tool_material = 0.0F;

                        for (int i = 0; i < BlockWorkbench.tool_materials.length; ++i) {
                            Material material = BlockWorkbench.tool_materials[i];
                            if (material != Material.obsidian && material.getDurability() < tool_material.getDurability() && material.getDurability() > highest_durability_that_is_less_than_tool_material) {
                                next_strongest_material = material;
                                highest_durability_that_is_less_than_tool_material = material.getDurability();
                            }
                        }

                        recipe.setMaterialToCheckToolBenchHardnessAgainst(next_strongest_material);
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }
}
