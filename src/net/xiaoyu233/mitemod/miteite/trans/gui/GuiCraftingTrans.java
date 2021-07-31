package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(awy.class)
public class GuiCraftingTrans {
    @Shadow public Container e;

    @Redirect(method = "drawItemStackTooltip",at = @At(value = "INVOKE",target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
    private boolean redirectRemoveDamageLimitation(InventoryCrafting caller){
        aah recipe = ((MITEContainerCrafting) this.e).current_crafting_result.recipe;
        if (recipe instanceof ShapedRecipes){
            return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
        }else if (recipe instanceof ShapelessRecipes){
            return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
        }
        return caller.hasDamagedItem();
    }
}
