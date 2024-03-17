package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITERecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({GuiContainer.class})
public class GuiCraftingTrans {
    @Shadow
    public Container inventorySlots;

    @Redirect(
            method = {"drawItemStackTooltip(Lnet/minecraft/ItemStack;IILnet/minecraft/Slot;)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"
            )
    )
    private boolean redirectRemoveDamageLimitation(InventoryCrafting caller) {
        IRecipe recipe = ((MITEContainerCrafting)this.inventorySlots).current_crafting_result.recipe;
        if (recipe instanceof ShapedRecipes) {
            return !((ITERecipe)recipe).isExtendsNBT() && caller.hasDamagedItem();
        } else if (!(recipe instanceof ShapelessRecipes)) {
            return caller.hasDamagedItem();
        } else {
            return !((ITERecipe)recipe).isExtendsNBT() && caller.hasDamagedItem();
        }
    }
}