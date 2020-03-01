package net.xiaoyu233.mitemod.miteite.liar.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import java.util.Random;

@Deceive(SlotArmor.class)
public class SlotArmorLiar extends Container{
    @Liar
    private int i;
    @Liar
    private int j;
    @Liar
    private int k;

    @Stealing
    public SlotArmorLiar(EntityHuman player) {
        super(player);
    }

    @Override
    @Stealing
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

    public int calcEnchantmentLevelsForSlot(Random random, int slot_index, int num_accessible_bookshelves, ItemStack item_stack) {
        Item item = item_stack.b();
        if (!ItemPotion.isBottleOfWater(item_stack) && !ItemGoldenApple.isUnenchantedGoldenApple(item_stack)) {
            if (item.c() <= 0) {
                return 0;
            } else {
                if (num_accessible_bookshelves > 24 && item.getHardestMetalMaterial() != Materials.vibranium) {
                    num_accessible_bookshelves = 24;
                }

                Block enchantment_table_block = super.world.getBlock(this.i, this.j, this.k);
                int enchantment_table_power = (1 + num_accessible_bookshelves) * (enchantment_table_block == Block.enchantmentTableEmerald ? 2 : 4);
                int enchantment_levels = EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability(enchantment_table_power, item);
                float fraction = (1.0F + (float)slot_index) / 3.0F;
                if (slot_index < 2) {
                    fraction += (random.nextFloat() - 0.5F) * 0.2F;
                }

                return Math.max(Math.round((float)enchantment_levels * fraction), 1);
            }
        } else {
            return 2;
        }
    }
}
