package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.RegisterHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(CraftingManager.class)
public abstract class CraftingManagerHelper {
   @Shadow
   @Final
   private static CraftingManager instance;
   @Shadow
   private List recipes;

   @Shadow
   private ShapedRecipes addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
      return null;
   }

   @Shadow
   private void checkRecipe(Item item, int subtype_or_0){}

   @Redirect(method = "<init>",
           at = @At(value = "INVOKE",target = "Lnet/minecraft/RecipesMITE;addCraftingRecipes(Lnet/minecraft/CraftingManager;)V"))
   private void injectRegisterRecipes(CraftingManager crafters) {
      RegisterHelper.registerAllItems();
      RecipesMITE.addCraftingRecipes(crafters);
      RegisterHelper.registerAllRecipes(crafters);
   }
}
