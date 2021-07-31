package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TileEntityBeacon.class)
public class TileEntityBeaconTrans extends TileEntity {
   private BlockPos currentPos;
   @Shadow
   private boolean isBeaconActive;
   @Shadow
   private int levels;
   @Shadow
   private int primaryEffect;
   @Shadow
   private int secondaryEffect;

   @Overwrite
   private void addEffectsToPlayers() {
      if (this.isBeaconActive && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
         double var1 = this.levels * 10 + 10;
         byte var3 = 0;
         if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
            var3 = 1;
         }

         AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(var1, var1, var1);
         var4.maxY = this.worldObj.getHeight();
         List var5 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4);

         EntityPlayer var7;
         for (Object o : var5) {
            var7 = (EntityPlayer) o;
            var7.addPotionEffect(new MobEffect(this.primaryEffect, 180, var3, true));
            var7.setCraftingBoostFactor(this.getCraftingBoostFactor(this.levels), this.currentPos);
            var7.setCraftingBoostTimer(80);
         }

         if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {

            for (Object o : var5) {
               var7 = (EntityPlayer) o;
               var7.addPotionEffect(new MobEffect(this.secondaryEffect, 180, 0, true));
            }
         }
      }

   }

   public void clearAllPlayersBoost() {
      double var1 = this.levels * 10 + 10;
      AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(var1, var1, var1);
      for (Object o : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4)) {
         EntityPlayer EntityPlayer = (EntityPlayer) o;
         EntityPlayer.setCraftingBoostFactor(0.0F, this.currentPos);
      }

   }

//   @Inject(locals = LocalCapture.PRINT,method = "addEffectsToPlayers",at = @At(ordinal = 1,shift = At.Shift.AFTER,value = "INVOKE",target = "Lnet/minecraft/EntityLiving;addPotionEffect(Lnet/minecraft/MobEffect;)V"))
//   private void injectCraftingBoost(CallbackInfo ci){
//
//   }

   private float getCraftingBoostFactor(int level) {
      switch(level) {
      case 1:
         return 0.25F;
      case 2:
         return 0.4F;
      case 3:
         return 0.6F;
      case 4:
         return 0.85F;
      default:
         return 0F;
      }
   }

   @Inject(method = "readFromNBT",at = @At("RETURN"))
   public void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callbackInfo) {
      this.currentPos = new BlockPos(this.xCoord, this.yCoord, this.zCoord);
   }

   @Inject(method = "updateState",at = @At(value = "HEAD"))
   private void injectUpdatePos(CallbackInfo ci){
      if (this.currentPos == null) {
         this.currentPos = new BlockPos(this.xCoord, this.yCoord, this.zCoord);
      }
   }

   @Shadow
   public void setPrimaryEffect(int par1) {
   }

   @Overwrite
   private void updateState(){
      if (this.currentPos == null){
         this.currentPos = new BlockPos(this.xCoord, this.yCoord, this.zCoord);
      }
      if (!this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) {
         this.isBeaconActive = false;
         this.levels = 0;
      } else {
         this.isBeaconActive = true;
         int previousLevel = this.levels;
         this.levels = 0;

         for(int var1 = 1; var1 <= 4; this.levels = var1++) {
            int var2 = this.yCoord - var1;
            if (var2 < 0) {
               break;
            }

            boolean var3 = true;

            for(int var4 = this.xCoord - var1; var4 <= this.xCoord + var1 && var3; ++var4) {
               for(int var5 = this.zCoord - var1; var5 <= this.zCoord + var1; ++var5) {
                  int var6 = this.worldObj.getBlockId(var4, var2, var5);
                  if (var6 != Block.blockEmerald.blockID && var6 != Block.blockDiamond.blockID && var6 != Block.blockCopper.blockID && var6 != Block.blockSilver.blockID && var6 != Block.blockGold.blockID && var6 != Block.blockIron.blockID && var6 != Block.blockMithril.blockID && var6 != Block.blockAdamantium.blockID && var6 != Blocks.blockVibranium.blockID) {
                     var3 = false;
                     break;
                  }
               }
            }

            if (!var3) {
               break;
            }
         }

         if (this.levels == 0) {
            this.isBeaconActive = false;
            if (previousLevel != 0) {
               double var1 = previousLevel * 10 + 10;
               AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(var1, var1, var1);

               for (Object o : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4)) {
                  EntityPlayer EntityPlayer = (EntityPlayer) o;
                  EntityPlayer.setCraftingBoostFactor(0.0F, this.currentPos);
               }
            }
         } else {
            this.setPrimaryEffect(this.primaryEffect);
         }
      }
   }
}
