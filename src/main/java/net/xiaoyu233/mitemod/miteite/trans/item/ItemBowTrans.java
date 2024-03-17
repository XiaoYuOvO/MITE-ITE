package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemBow.class)
public class ItemBowTrans extends Item {
   @Shadow
   private Material reinforcement_material;

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.setMaxDamage(reinforcement_material == Materials.vibranium ? 512 : (reinforcement_material == Material.mithril ? 128 : (reinforcement_material == Material.ancient_metal ? 64 : 32)));
   }

   public ItemBowTrans(int id, Material reinforcement_material) {
      super(id, Material.wood, "bows/" + reinforcement_material.toString() + "/");
   }

   @Redirect(method = "addInformation", at = @At(value = "INVOKE", target = "Lnet/minecraft/Translator;getFormatted(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"))
   private String injectAddInfo(String key, Object[] par1ArrayOfObj){
      if (this.reinforcement_material == Materials.vibranium){
         return Translator.getFormatted(key,75);
      }
      return Translator.getFormatted(key,par1ArrayOfObj);
   }

   @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemBow;setMaxDamage(I)Lnet/minecraft/Item;"))
   private Item redirectSetMaxDamage(ItemBow instance, int i){
      if (reinforcement_material == Materials.vibranium) {
         this.setMaxDamage(512);
      } else {
         if (reinforcement_material == Material.mithril) {
            this.setMaxDamage(128);
         } else {
            if (reinforcement_material == Material.ancient_metal) {
               this.setMaxDamage(64);
            } else {
               this.setMaxDamage(32);
            }
         }
      }
      return this;
   }
}
