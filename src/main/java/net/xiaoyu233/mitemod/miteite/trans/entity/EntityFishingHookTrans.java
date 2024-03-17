package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EntityFishHook.class})
public abstract class EntityFishingHookTrans extends Entity {
   @Shadow
   public EntityPlayer angler;

   public EntityFishingHookTrans(World par1World) {
      super(par1World);
   }

   @Redirect(
           method = {"catchFish()I"},
           at = @At(
                   value = "NEW",
                   target = "(Lnet/minecraft/World;DDDI)Lnet/minecraft/EntityXPOrb;"
           )
   )
   private EntityXPOrb ctorFishingExp(World par1World, double par2, double par4, double par6, int par8) {
      return new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, Configs.GameMechanics.FISHING_XP_SCALE.get());
   }
}