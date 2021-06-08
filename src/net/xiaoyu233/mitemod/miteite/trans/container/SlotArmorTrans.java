package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;
import java.util.Random;

@Mixin(SlotArmor.class)
public class SlotArmorTrans extends Container {
   @Shadow
   private int posX;
   @Shadow
   private int posY;
   @Shadow
   private int posZ;

   public SlotArmorTrans(EntityPlayer player) {
      super(player);
   }

   @Nonnull
   @Overwrite
   public int calcEnchantmentLevelsForSlot(Random random, int slotIndex, int numAccessibleBookshelves, ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (!ItemPotion.isBottleOfWater(itemStack) && !ItemGoldenApple.isUnenchantedGoldenApple(itemStack)) {
         if (item.getItemEnchantability() <= 0) {
            return 0;
         } else {
            if (numAccessibleBookshelves > 24 && item.getHardestMetalMaterial() != Materials.vibranium) {
               numAccessibleBookshelves = 24;
            }

            Block enchantment_table_block = super.world.getBlock(this.posX, this.posY, this.posZ);
            int enchantment_table_power = (1 + numAccessibleBookshelves) * (enchantment_table_block == Block.enchantmentTableEmerald ? 2 : 4);
            int enchantment_levels = EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability(enchantment_table_power, item);
            float fraction = (1.0F + (float)slotIndex) / 3.0F;
            if (slotIndex < 2) {
               fraction += (random.nextFloat() - 0.5F) * 0.2F;
            }

            return Math.max(Math.round((float)enchantment_levels * fraction), 1);
         }
      } else {
         return 4;
      }
   }


   //Dont know can it work or not,so dont use now
//   @ModifyConstant(method = "getNumAccessibleBookshelves",constant = {@Constant(intValue = 1)})
//   private int modifyMaxBookShelvesCount(int raw){
//      return 3;
//   }

   @Shadow
   public boolean canInteractWith(EntityPlayer EntityPlayer) {
      return false;
   }

   @Overwrite
   private int getNumAccessibleBookshelves() {
      int num_bookshelves = 0;

      for(int dy = 0; dy <= 3 && (dy != 3 || super.world.isAirOrPassableBlock(this.posX, this.posY + 1, this.posZ, false)); ++dy) {
         boolean[][] is_block_accessible = new boolean[5][5];
         if (super.world.isAirOrPassableBlock(this.posX, this.posY + dy, this.posZ - 1, false)) {
            is_block_accessible[2][0] = true;
            if (super.world.isAirOrPassableBlock(this.posX - 1, this.posY + dy, this.posZ - 1, false)) {
               is_block_accessible[0][1] = true;
               is_block_accessible[1][0] = true;
            }

            if (super.world.isAirOrPassableBlock(this.posX + 1, this.posY + dy, this.posZ - 1, false)) {
               is_block_accessible[3][0] = true;
               is_block_accessible[4][1] = true;
            }
         }

         if (super.world.isAirOrPassableBlock(this.posX + 1, this.posY + dy, this.posZ, false)) {
            is_block_accessible[4][2] = true;
            if (super.world.isAirOrPassableBlock(this.posX + 1, this.posY + dy, this.posZ - 1, false)) {
               is_block_accessible[3][0] = true;
               is_block_accessible[4][1] = true;
            }

            if (super.world.isAirOrPassableBlock(this.posX + 1, this.posY + dy, this.posZ + 1, false)) {
               is_block_accessible[4][3] = true;
               is_block_accessible[3][4] = true;
            }
         }

         if (super.world.isAirOrPassableBlock(this.posX, this.posY + dy, this.posZ + 1, false)) {
            is_block_accessible[2][4] = true;
            if (super.world.isAirOrPassableBlock(this.posX + 1, this.posY + dy, this.posZ + 1, false)) {
               is_block_accessible[4][3] = true;
               is_block_accessible[3][4] = true;
            }

            if (super.world.isAirOrPassableBlock(this.posX - 1, this.posY + dy, this.posZ + 1, false)) {
               is_block_accessible[1][4] = true;
               is_block_accessible[0][3] = true;
            }
         }

         if (super.world.isAirOrPassableBlock(this.posX - 1, this.posY + dy, this.posZ, false)) {
            is_block_accessible[0][2] = true;
            if (super.world.isAirOrPassableBlock(this.posX - 1, this.posY + dy, this.posZ + 1, false)) {
               is_block_accessible[1][4] = true;
               is_block_accessible[0][3] = true;
            }

            if (super.world.isAirOrPassableBlock(this.posX - 1, this.posY + dy, this.posZ - 1, false)) {
               is_block_accessible[0][1] = true;
               is_block_accessible[1][0] = true;
            }
         }

         for(int dx = -2; dx <= 2; ++dx) {
            for(int dz = -2; dz <= 2; ++dz) {
               if (is_block_accessible[2 + dx][2 + dz] && super.world.getBlock(this.posX + dx, this.posY + dy, this.posZ + dz) == Block.bookShelf) {
                  ++num_bookshelves;
               }
            }
         }
      }

      return num_bookshelves;
   }
}
