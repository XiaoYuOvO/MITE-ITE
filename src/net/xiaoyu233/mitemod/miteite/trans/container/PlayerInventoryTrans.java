package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.EntityPlayer;
import net.minecraft.PlayerInventory;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryTrans {
   @Shadow
   public EntityPlayer player;

   //Fix the bug of getting items invalidly
   @Inject(method = "trySwitchItemOrRestock",at = @At(value = "HEAD"))
   private void injectItemsSync(CallbackInfoReturnable<Boolean> callbackInfo) {
      this.player.sendPacket(new CPacketSyncItems());
   }
}


