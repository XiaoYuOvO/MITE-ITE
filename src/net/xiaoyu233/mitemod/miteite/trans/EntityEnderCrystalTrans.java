package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityEnderCrystal.class)
public class EntityEnderCrystalTrans extends Entity {
    public EntityEnderCrystalTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Shadow
    protected void entityInit() {

    }

    @Overwrite
    public boolean isImmuneTo(DamageSource damage_source) {
        if (!super.isImmuneTo(damage_source)) {
            if (damage_source.isMelee()) {
                ItemStack item_stack = damage_source.getItemAttackedWith();
                if (item_stack != null && item_stack.getItem().isTool()) {
                    ItemTool item_tool = item_stack.getItemAsTool();
                    //Make only vibranium will be effective
                    return !item_tool.isEffectiveAgainstBlock(Block.blockAdamantium, 0);
                }
            }

        }
        return true;
    }

    @Override
    @Shadow
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {

    }

    @Override
    @Shadow
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {

    }
}
