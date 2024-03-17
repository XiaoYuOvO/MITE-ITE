package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemAppleGold.class)
public class ItemGoldenAppleTrans extends ItemFood {

    public ItemGoldenAppleTrans() {
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return ItemAppleGold.isEnchantedGoldenApple(par1ItemStack) ? Configs.Item.ENCHANTED_GOLDEN_APPLE_EAT_TIME.get() : Configs.Item.GOLDEN_APPLE_EAT_TIME.get();
    }

    @Inject(
            method = {"<init>(IIILjava/lang/String;)V"},
            at = {@At("RETURN")}
    )
    private void injectCtor(CallbackInfo callback) {
        this.setSugarContent(1500);
    }

    @Overwrite
    protected void onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getItemSubtype() > 0) {
            if (!par2World.isRemote) {
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1200, 1));
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 600, 1));
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1200, 0));
                if (!par3EntityPlayer.isPotionActive(Potion.field_76444_x) || par3EntityPlayer.getActivePotionEffect(Potion.field_76444_x).getAmplifier() <= 0) {
                    par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.heal.id, 2, 0));
                    par3EntityPlayer.removePotionEffect(Potion.field_76444_x.id);
                    par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 400, 2));
                }
            }
        } else if (!par2World.isRemote) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1200, 0));
            if (!par3EntityPlayer.isPotionActive(Potion.field_76444_x) || par3EntityPlayer.getActivePotionEffect(Potion.field_76444_x).getAmplifier() != 0) {
                par3EntityPlayer.removePotionEffect(Potion.field_76444_x.id);
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 400, 0));
            }

            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 200, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 200, 0));
        }

    }

}
