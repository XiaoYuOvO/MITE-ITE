package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.EntityItem;
import net.minecraft.IInventory;
import net.minecraft.TileEntityHopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityHopper.class)
public class TileEntityHopperTrans {
    @Inject(method = "insertStackFromEntity",at = @At("HEAD"),cancellable = true)
    private static void injectCheckCanPick(IInventory par0IInventory, EntityItem par1EntityItem,CallbackInfoReturnable<Boolean> callback){
        if (!par1EntityItem.canBePickUpByPlayer()){
            callback.setReturnValue(false);
            callback.cancel();
        }
    }
}
