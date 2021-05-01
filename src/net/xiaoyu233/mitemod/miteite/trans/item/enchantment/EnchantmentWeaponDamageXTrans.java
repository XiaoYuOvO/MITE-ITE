package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentWeaponDamage.class)
public abstract class EnchantmentWeaponDamageXTrans extends Enchantment {
   protected EnchantmentWeaponDamageXTrans(int id, yq rarity, int difficulty) {
      super(id, rarity, difficulty);
   }

   public boolean canEnchantItem(Item item) {
      if (this != Enchantment.sharpness) {
         if (this == Enchantment.baneOfArthropods) {
            return item instanceof ItemSword;
         } else if (this != Enchantment.smite) {
            return false;
         } else {
            return item.getClass() == ItemWarHammer.class || item.getHardestMetalMaterial() == Materials.vibranium && item.getClass() == ItemSword.class;
         }
      } else {
         return item instanceof ItemSword || item.getClass() == ItemBattleAxe.class || item instanceof ItemScythe;
      }
   }

   @Shadow
   public String getNameSuffix() {
      return null;
   }

   public int getNumLevelsForVibranium() {
      return 7;
   }

   public boolean isCompatibleWith(Enchantment enchantment, Item item) {
      return item.getHardestMetalMaterial() == Materials.vibranium ? enchantment != this : !(enchantment instanceof EnchantmentWeaponDamage);
   }

   @Shadow
   public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
      return false;
   }
}
