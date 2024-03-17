package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemBlock.class)
public abstract class ItemBlockTrans extends Item{
    @Shadow public abstract Block getBlock();

    @Override
    public String getItemDisplayName(ItemStack item_stack) {
        if (I18n.i18nLocale == null){
            return super.getItemDisplayName(item_stack);
        }
        return this.getBlock().getItemDisplayName(item_stack);
    }
}
