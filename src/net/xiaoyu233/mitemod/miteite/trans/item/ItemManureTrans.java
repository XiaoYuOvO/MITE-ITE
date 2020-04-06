package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import static net.minecraft.ItemDye.suppress_standard_particle_effect;
import static net.minecraft.ItemDye.tryFertilize;

@Transform(ItemManure.class)
public class ItemManureTrans extends Item {
    @Override
    public boolean onItemRightClick(EntityHuman player, float partial_tick, boolean ctrl_is_down) {
        RaycastCollision rc = player.getSelectedObject(partial_tick, false);
        if (rc != null && rc.isBlock()) {
            if (this.tryFertilizeBlock(rc.world, rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, rc.face_hit,
                    player.getHeldItemStack(), player)) {
                if (player.onClient()) {
                    player.swingArm();
                } else if (!player.inCreativeMode()) {
                    player.convertOneOfHeldItem(null);
                }

                return true;
            } else {
                World world = rc.world;
                int x = rc.block_hit_x;
                int y = rc.block_hit_y;
                int z = rc.block_hit_z;
                ItemStack item_stack = player.getHeldItemStack();
                if (tryFertilize(item_stack, world, x, y, z, rc.face_hit)) {
                    if (player.onServer() && !player.inCreativeMode()) {
                        player.convertOneOfHeldItem(null);
                    }

                    if (suppress_standard_particle_effect) {
                        suppress_standard_particle_effect = false;
                        return true;
                    }

                    if (player.onServer()) {
                        Block block = rc.getBlockHit();
                        if (block == Block.z || block == Block.aF || block == Block.bD) {
                            ++y;
                        }

                        world.e(2005, x, y, z, 0);
                    }

                    if (world.getBlock(x, y, z) instanceof BlockCrops) {
                        player.a(AchievementList.plantDoctor);
                    }

                    return true;
                }else{
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @Marker
    private boolean tryFertilizeBlock(World world, int block_hit_x, int block_hit_y, int block_hit_z, EnumFace face_hit, ItemStack heldItemStack, EntityHuman player) {
        return false;
    }
}
