package net.xiaoyu233.mitemod.miteite.trans.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityEnderCrystal.class)
public abstract class EntityEnderCrystalTrans extends Entity {
    public EntityEnderCrystalTrans(World par1World) {
        super(par1World);
    }

    @ModifyExpressionValue(method = "isImmuneTo", at = @At(value = "FIELD", target = "Lnet/minecraft/Block;blockMithril:Lnet/minecraft/Block;"))
    public Block isImmuneTo(Block original) {
        //Make only vibranium will be effective
        return Block.blockAdamantium;
    }
}
