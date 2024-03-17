package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.IRecipe;
import net.xiaoyu233.mitemod.miteite.api.ITERecipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IRecipe.class)
public interface IRecipeMixin extends ITERecipe {
    default boolean isExtendsNBT(){
        return false;
    }
}
