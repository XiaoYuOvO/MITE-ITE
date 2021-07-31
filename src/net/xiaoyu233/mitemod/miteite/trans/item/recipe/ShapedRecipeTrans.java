package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.CraftingResult;
import net.minecraft.InventoryCrafting;
import net.minecraft.ItemStack;
import net.minecraft.ShapedRecipes;
import net.xiaoyu233.mitemod.miteite.item.IUpgradableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShapedRecipes.class)
public class ShapedRecipeTrans {

    @Shadow private boolean field_92101_f;

    public boolean isExtendsNBT() {
        return field_92101_f;
    }

    @Inject(method = "getCraftingResult",at = @At(value = "INVOKE",target = "Lnet/minecraft/ItemStack;setTagCompound(Lnet/minecraft/NBTTagCompound;)Lnet/minecraft/ItemStack;",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectDowngradeForgingGrade(InventoryCrafting par1InventoryCrafting, CallbackInfoReturnable<CraftingResult> cir, ItemStack output, int index, ItemStack currentItem){
        if (currentItem.getItem() instanceof IUpgradableItem && currentItem.getItem().isDamageable()){
            output.setItemDamage(currentItem.getItemDamage());
        }
    }
}
