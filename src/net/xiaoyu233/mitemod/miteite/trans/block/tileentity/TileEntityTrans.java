package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.TileEntity;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntity.class)
public class TileEntityTrans {
   @Shadow
   private static void addMapping(Class par0Class, String par1Str) {
   }

   @Inject(method = "<clinit>",at = @At(value = "RETURN"))
   private static void registerForgingTable(CallbackInfo c){
      addMapping(TileEntityForgingTable.class, "ForgingTable");
   }
}
