package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.Container;
import net.minecraft.ContainerAnvil;
import net.minecraft.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerAnvil.class)
public abstract class ContainerAnvilTrans extends Container {
    public ContainerAnvilTrans(EntityPlayer player) {
        super(player);
    }

    @Override
    @Shadow
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }

    @Inject(method = "isRepairing",
            at = @At(value = "FIELD",target = "Lnet/minecraft/ContainerAnvil;repair_fail_condition:I",shift = At.Shift.AFTER,opcode = Opcodes.PUTFIELD),
    slice = @Slice(from = @At(value = "INVOKE",target = "Lnet/minecraft/Item;hasQuality()Z")))
    private void removeRepairExpReq(boolean b, CallbackInfoReturnable<Boolean> callbackInfo){
        this.repair_fail_condition = 0;
    }
}
