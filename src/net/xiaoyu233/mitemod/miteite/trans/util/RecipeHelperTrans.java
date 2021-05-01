package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.RecipeHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeHelper.class)
public class RecipeHelperTrans {
   //TODO
//   public static void addRecipe(aah recipe, boolean include_in_lowest_crafting_difficulty_determination) {
//      try {
//         if (include_in_lowest_crafting_difficulty_determination) {
//            recipe.setIncludeInLowestCraftingDifficultyDetermination();
//         }
//
//         ItemStack[] recipe_items = recipe.getComponents();
//         ItemStack recipe_output = recipe.getRecipeOutput();
//         Item output_item = recipe_output.getItem();
//         output_item.setAsCraftingProduct();
//         output_item.recipes[output_item.num_recipes++] = recipe;
//         float difficulty = 0.0F;
//         ItemStack[] var8 = recipe_items;
//         int var9 = recipe_items.length;
//
//         float highest_durability_that_is_less_than_tool_material;
//         int i;
//         for(i = 0; i < var9; ++i) {
//            ItemStack recipe_item = var8[i];
//            if (recipe_item != null) {
//               Item item = recipe_item.getItem();
//               if (item.getHasSubtypes() && recipe_item.getItemSubtype() != 32767) {
//                  recipe_item.setAsComponentOfCraftingProduct(recipe_output);
//               } else {
//                  item.setAsComponentOfCraftingProduct(recipe_output);
//               }
//
//               float component_difficulty = recipe_item.getCraftingDifficultyAsComponent();
//               if (component_difficulty < 0.0F) {
//                  highest_durability_that_is_less_than_tool_material = recipe_item.getItem().getLowestCraftingDifficultyToProduce();
//                  if (highest_durability_that_is_less_than_tool_material != Float.MAX_VALUE) {
//                     recipe_item.getItem().setCraftingDifficultyAsComponent(highest_durability_that_is_less_than_tool_material);
//                     component_difficulty = highest_durability_that_is_less_than_tool_material;
//                  } else if (item.hasMaterial(Material.rusted_iron)) {
//                     Item peer = Item.getMatchingItem(item.getClass(), Material.copper);
//                     if (peer != null) {
//                        if (item.getMaterialForDurability() == null) {
//                           Minecraft.setErrorMessage("addRecipe: getMaterialForDurability()==null for component " + item);
//                        }
//
//                        item.setCraftingDifficultyAsComponent(peer.getCraftingDifficultyAsComponent(null) * item.getMaterialForDurability().getDurability() / peer.getMaterialForDurability().getDurability());
//                        component_difficulty = item.getCraftingDifficultyAsComponent(null);
//                     }
//                  }
//               }
//
//               if (component_difficulty < 0.0F) {
//                  Minecraft.setErrorMessage("Warning: recipe for " + recipe_output.getDisplayName() + ", component crafting difficulty not set: " + recipe_item.getItem().getItemDisplayName(recipe_item) + " [" + (recipe_item.itemID - 256) + "]");
//               } else {
//                  difficulty += component_difficulty;
//               }
//            }
//         }
//
//         if (difficulty < 0.0F) {
//            Minecraft.setErrorMessage("addRecipe: recipe output cannot have a crafting difficulty < 0.0F");
//         }
//
//         recipe.setDifficulty(difficulty);
//         if (recipe_output.stackSize < 1) {
//            Minecraft.setErrorMessage("stackSize is 0 for recipe output (" + recipe_output.getDisplayName() + ")");
//         }
//
//         ItemStack output_item_stack = recipe.getRecipeOutput();
//         if (output_item_stack != null) {
//            output_item = output_item_stack.getItem();
//            if (output_item.containsMetal()) {
//               if (output_item instanceof ItemIngot) {
//                  return;
//               }
//
//               if (output_item.getClass() == ItemKnife.class) {
//                  return;
//               }
//
//               if (output_item.getClass() == ItemBucket.class && RecipeHelper.hasAsComponent(recipe, ItemBucket.class)) {
//                  return;
//               }
//
//               recipe.setMaterialToCheckToolBenchHardnessAgainst(output_item.getHardestMetalMaterial());
//            } else if (output_item.itemID == Block.planks.blockID) {
//               recipe.setMaterialToCheckToolBenchHardnessAgainst(Material.wood);
//            } else if (output_item.itemID == Block.workbench.blockID) {
//               Material tool_material = BlockWorkbench.getToolMaterial(output_item_stack.getItemSubtype());
//               if (tool_material.isMetal()) {
//                  Material next_strongest_material = null;
//                  highest_durability_that_is_less_than_tool_material = 0.0F;
//
//                  for(i = 0; i < BlockWorkbench.tool_materials.length; ++i) {
//                     Material material = BlockWorkbench.tool_materials[i];
//                     if (material != Material.obsidian && material.getDurability() < tool_material.getDurability() && material.getDurability() > highest_durability_that_is_less_than_tool_material) {
//                        next_strongest_material = material;
//                        highest_durability_that_is_less_than_tool_material = material.getDurability();
//                     }
//                  }
//
//                  recipe.setMaterialToCheckToolBenchHardnessAgainst(next_strongest_material);
//               }
//            }
//         }
//      } catch (ArrayIndexOutOfBoundsException var15) {
//      }
//
//   }
}
