package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Random;

@Mixin(ContainerEnchantment.class)
public abstract class SlotArmorTrans extends Container {
   public SlotArmorTrans(EntityPlayer player) {
      super(player);
   }

   @ModifyConstant(method = "calcEnchantmentLevelsForSlot", constant = @Constant(intValue = 24, ordinal = 0))
   private int redirectMaxBookCountCheck(int constant, Random random, int slotIndex, int numAccessibleBookshelves, ItemStack itemStack){
      if (itemStack.getItem().getHardestMetalMaterial() == Materials.vibranium){
         return 48;
      }
      return 24;
   }

   @ModifyConstant(method = "calcEnchantmentLevelsForSlot", constant = @Constant(intValue = 2, ordinal = 2))
   private int modifyItemEnchantingCost(int original){
      return 4;
   }


   //Dont know can it work or not,so dont use now
   @ModifyConstant(method = "getNumAccessibleBookshelves",constant = {@Constant(intValue = 1, ordinal = 1), @Constant(intValue = 1, ordinal = 0)})
   private int modifyMaxBookShelvesCount(int raw){
      return 3;
   }
}
