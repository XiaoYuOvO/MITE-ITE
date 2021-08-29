package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemHoe.class)
public class ItemHoeTrans extends ItemToolTrans{
    @Override
    @SoftOverride
    protected int getExpForBlockBreak(BlockBreakInfo blockBreakInfo) {
        Block block = blockBreakInfo.block;
        if (block instanceof BlockCrops){
            if (((BlockCrops) block).isMature(blockBreakInfo.getMetadata())) {
                return 8;
            }
        }
        if (this.materials_effective_against.contains(block)){
            return 3;
        }
        return 0;
    }

    @Inject(method = "tryTillSoil",at =
    @At(value = "INVOKE",
            target = "Lnet/minecraft/EntityPlayer;tryDamageHeldItem(Lnet/minecraft/DamageSource;I)Lnet/minecraft/ItemDamageResult;",
            shift = At.Shift.AFTER))
    private static void injectAddExpForTilling(World world, int x, int y, int z, EnumFace face, EntityPlayer player, ItemStack item_stack, CallbackInfoReturnable<Boolean> callbackInfo){
        item_stack.getItem().addExpForTool(item_stack,player,1);
    }
}
