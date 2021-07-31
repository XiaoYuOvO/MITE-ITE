package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.CraftingResult;
import net.minecraft.InventoryCrafting;
import net.minecraft.ItemStack;
import net.minecraft.ShapelessRecipes;
import net.xiaoyu233.mitemod.miteite.item.IUpgradableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShapelessRecipes.class)
public class ShapelessRecipeTrans {
    @Shadow private boolean propagate_tag_compound;

    public boolean isExtendsNBT() {
        return propagate_tag_compound;
    }

    @Inject(method = "getCraftingResult",at = @At(value = "INVOKE",target = "Lnet/minecraft/ItemStack;setTagCompound(Lnet/minecraft/NBTTagCompound;)Lnet/minecraft/ItemStack;",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectDowngradeForgingGrade(InventoryCrafting par1InventoryCrafting, CallbackInfoReturnable<CraftingResult> cir, ItemStack output, int index, ItemStack currentItem){
        if (currentItem.getItem() instanceof IUpgradableItem && currentItem.getItem().isDamageable()){
            output.setItemDamage(currentItem.getItemDamage());
        }
    }
}
