package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.CraftingManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.ShapedRecipes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

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

   //TODO Move to recipe modifier API
   @Redirect(method = "<init>",
           at = @At(value = "INVOKE",target = "Lnet/minecraft/CraftingManager;addRecipe(Lnet/minecraft/ItemStack;[Ljava/lang/Object;)Lnet/minecraft/ShapedRecipes;"),
           slice = @Slice(
                   from = @At(value = "FIELD",target = "Lnet/minecraft/Item;appleGold:Lnet/minecraft/Item;"),
                   to = @At(value = "FIELD",target = "Lnet/minecraft/Item;goldenCarrot:Lnet/minecraft/Item;")))
   private ShapedRecipes injectModifyGoldenAppleRecipe(CraftingManager caller,ItemStack par1ItemStack, Object... par2ArrayOfObj){
      return this.addRecipe(new ItemStack(Item.appleGold, 1, 0),
              "###",
                              "IXI",
                              "###",
              '#', Item.goldNugget,
              'I', Item.ingotGold,
              'X', Item.appleRed);
   }
}
