package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import javax.annotation.Nonnull;
import java.util.Random;

@Transform(SlotArmor.class)
public class SlotArmorTrans extends Container{
    @Link
    private int i;
    @Link
    private int j;
    @Link
    private int k;

    @Marker
    public SlotArmorTrans(EntityHuman player) {
        super(player);
    }

    @Override
    @Marker
    public boolean a(EntityHuman entityHuman) {
        return false;
    }
    private int getNumAccessibleBookshelves() {
        int num_bookshelves = 0;

        for(int dy = 0; dy <= 3 && (dy != 3 || super.world.isAirOrPassableBlock(this.i, this.j + 1, this.k, false)); ++dy) {
            boolean[][] is_block_accessible = new boolean[5][5];
            if (super.world.isAirOrPassableBlock(this.i, this.j + dy, this.k - 1, false)) {
                is_block_accessible[2][0] = true;
                if (super.world.isAirOrPassableBlock(this.i - 1, this.j + dy, this.k - 1, false)) {
                    is_block_accessible[0][1] = true;
                    is_block_accessible[1][0] = true;
                }

                if (super.world.isAirOrPassableBlock(this.i + 1, this.j + dy, this.k - 1, false)) {
                    is_block_accessible[3][0] = true;
                    is_block_accessible[4][1] = true;
                }
            }

            if (super.world.isAirOrPassableBlock(this.i + 1, this.j + dy, this.k, false)) {
                is_block_accessible[4][2] = true;
                if (super.world.isAirOrPassableBlock(this.i + 1, this.j + dy, this.k - 1, false)) {
                    is_block_accessible[3][0] = true;
                    is_block_accessible[4][1] = true;
                }

                if (super.world.isAirOrPassableBlock(this.i + 1, this.j + dy, this.k + 1, false)) {
                    is_block_accessible[4][3] = true;
                    is_block_accessible[3][4] = true;
                }
            }

            if (super.world.isAirOrPassableBlock(this.i, this.j + dy, this.k + 1, false)) {
                is_block_accessible[2][4] = true;
                if (super.world.isAirOrPassableBlock(this.i + 1, this.j + dy, this.k + 1, false)) {
                    is_block_accessible[4][3] = true;
                    is_block_accessible[3][4] = true;
                }

                if (super.world.isAirOrPassableBlock(this.i - 1, this.j + dy, this.k + 1, false)) {
                    is_block_accessible[1][4] = true;
                    is_block_accessible[0][3] = true;
                }
            }

            if (super.world.isAirOrPassableBlock(this.i - 1, this.j + dy, this.k, false)) {
                is_block_accessible[0][2] = true;
                if (super.world.isAirOrPassableBlock(this.i - 1, this.j + dy, this.k + 1, false)) {
                    is_block_accessible[1][4] = true;
                    is_block_accessible[0][3] = true;
                }

                if (super.world.isAirOrPassableBlock(this.i - 1, this.j + dy, this.k - 1, false)) {
                    is_block_accessible[0][1] = true;
                    is_block_accessible[1][0] = true;
                }
            }

            for(int dx = -2; dx <= 2; ++dx) {
                for(int dz = -2; dz <= 2; ++dz) {
                    if (is_block_accessible[2 + dx][2 + dz] && super.world.getBlock(this.i + dx, this.j + dy, this.k + dz) == Block.as) {
                        ++num_bookshelves;
                    }
                }
            }
        }

        return num_bookshelves;
    }

    @Nonnull
    public int calcEnchantmentLevelsForSlot(Random random, int slotIndex, int numAccessibleBookshelves, ItemStack itemStack) {
        Item item = itemStack.b();
        if (!ItemPotion.isBottleOfWater(itemStack) && !ItemGoldenApple.isUnenchantedGoldenApple(itemStack)) {
            if (item.c() <= 0) {
                return 0;
            } else {
                if (numAccessibleBookshelves > 24 && item.getHardestMetalMaterial() != Materials.vibranium) {
                    numAccessibleBookshelves = 24;
                }

                Block enchantment_table_block = super.world.getBlock(this.i, this.j, this.k);
                int enchantment_table_power = (1 + numAccessibleBookshelves) * (enchantment_table_block == Block.enchantmentTableEmerald ? 2 : 4);
                int enchantment_levels = EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability(enchantment_table_power, item);
                float fraction = (1.0F + (float)slotIndex) / 3.0F;
                if (slotIndex < 2) {
                    fraction += (random.nextFloat() - 0.5F) * 0.2F;
                }

                return Math.max(Math.round((float)enchantment_levels * fraction), 1);
            }
        } else {
            return 2;
        }
    }
}
