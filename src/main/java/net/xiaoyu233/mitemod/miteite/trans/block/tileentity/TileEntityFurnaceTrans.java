package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityFurnace.class)
public class TileEntityFurnaceTrans extends TileEntity {
   @Shadow
   private final ItemStack[] furnaceItemStacks = new ItemStack[3];
   @Shadow
   public int furnaceCookTime;
   @Shadow
   public int heat_level;

   @Inject(method = "getHeatLevelRequired", at = @At("HEAD"), cancellable = true)
   private static void getHeatLevelRequired(int item_id, CallbackInfoReturnable<Integer> cir) {
      if (item_id == Block.coalBlock.blockID) {
         cir.setReturnValue(5);
      } else if (item_id == Block.oreAdamantium.blockID || item_id == Block.blockIron.blockID || item_id == Block.blockMithril.blockID || item_id == Blocks.netherAdamantiumOre.blockID) {
         cir.setReturnValue(4);
      }
   }

   @Inject(method = "getCookProgressScaled", at = @At("HEAD"), cancellable = true)
   public void injectScaleProgress(int par1, CallbackInfoReturnable<Integer> cir) {
      if (this.furnaceItemStacks[0] != null){
         cir.setReturnValue(this.furnaceCookTime * par1 / (this.furnaceItemStacks[0].getItem().getCookTime() / Math.max(this.heat_level, 1)));
      }
   }

   @Shadow
   public ItemStack getInputItemStack() {
      return this.furnaceItemStacks[0];
   }

   //TODO Check correction
   @Redirect(method = "smeltItem", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/ItemStack;stackSize:I", ordinal = 1))
   private void injectSmeltRecipe(ItemStack instance, int value) {
      if (this.getInputItemStack().itemID == Block.blockIron.blockID && instance.itemID == Block.blockIron.blockID) {
         instance.stackSize -= 4;
      } else if (this.getInputItemStack().itemID == Block.blockMithril.blockID && instance.itemID == Block.blockMithril.blockID) {
         instance.stackSize -= (Configs.Item.Recipes.MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM.get()).byteValue();
      }else {
         instance.stackSize = value;
      }
   }

   @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 200))
   private int modifyCookTime(int constant){
      if (this.heat_level != 0){
         return this.furnaceItemStacks[0].getItem().getCookTime() / this.heat_level;
      }
      return constant;
   }
}
