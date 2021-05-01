package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ItemBow.class)
public class ItemBowTrans extends Item {
   @Shadow
   private Material reinforcement_material;

   public ItemBowTrans(int id, Material reinforcement_material) {
      super(id, Material.wood, "bows/" + reinforcement_material.toString() + "/");
//      if (reinforcement_material != null && reinforcement_material != Material.wood) {
//         this.addMaterial(new Material[]{reinforcement_material});
//      }
//
//      this.reinforcement_material = reinforcement_material;
//      this.d(1);
//      this.e(reinforcement_material == Materials.vibranium ? 512 : (reinforcement_material == Material.mithril ? 128 : (reinforcement_material == Material.ancient_metal ? 64 : 32)));
//      this.a(CreativeModeTab.tabCombat);
//      this.setSkillsetThatCanRepairThis(reinforcement_material.isMetal() ? Skill.ARCHERY.getID() + Skill.BLACKSMITHING.getID() : Skill.ARCHERY.getID());
   }

   @Overwrite
   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info && this.reinforcement_material.isMetal()) {
         int bonus = this.reinforcement_material == Material.mithril ? 25 : (this.reinforcement_material == Materials.vibranium ? 75 : 10);
         info.add("");
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("item.tooltip.velocityBonus", new Object[]{bonus}));
      }

      super.addInformation(item_stack, player, info, extended_info, slot);
   }

   @Redirect(method = "<init>",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/Item;setMaxDamage(I)Lnet/minecraft/Item;"))
   private void redirectSetMaxDamage(){
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
   }
}
