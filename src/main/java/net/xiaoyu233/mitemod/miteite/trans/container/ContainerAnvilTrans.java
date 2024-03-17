package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.Container;
import net.minecraft.ContainerRepair;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerRepair.class)
public abstract class ContainerAnvilTrans extends Container {
    public ContainerAnvilTrans(EntityPlayer player) {
        super(player);
    }

    @Shadow
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }

    @Inject(
            method = {"isRepairing(Z)Z"},
            at = {@At(
                    value = "FIELD",
                    target = "Lnet/minecraft/ContainerRepair;repair_fail_condition:I",
                    shift = At.Shift.AFTER,
                    opcode = 181
            )},
            slice = {@Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/Item;hasQuality()Z"
                    )
            )}
    )
    private void removeRepairExpReq(boolean b, CallbackInfoReturnable<Boolean> callbackInfo) {
        this.repair_fail_condition = 0;
    }
}
