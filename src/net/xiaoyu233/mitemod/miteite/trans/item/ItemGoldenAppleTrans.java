package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemGoldenApple.class)
public class ItemGoldenAppleTrans extends ItemFood {

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return ItemGoldenApple.isEnchantedGoldenApple(par1ItemStack) ? Configs.Item.ENCHANTED_GOLDEN_APPLE_EAT_TIME.get(): Configs.Item.GOLDEN_APPLE_EAT_TIME.get();
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    private void injectCtor(CallbackInfo callback){
        this.setSugarContent(1500);
    }

    @Overwrite
    protected void onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getItemSubtype() > 0) {
            if (!par2World.isRemote) {
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, 1200, 1));
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.resistance.id, 600, 1));
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.fireResistance.id, 1200, 0));
                if (!(par3EntityPlayer.isPotionActive(MobEffectList.field_76444_x) && par3EntityPlayer.getActivePotionEffect(MobEffectList.field_76444_x).getAmplifier() > 0)) {
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.heal.id, 2, 0));
                    par3EntityPlayer.removePotionEffect(MobEffectList.field_76444_x.id);
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.field_76444_x.id, 20 * 20, 2));
                }
            }
        } else {
            if (!par2World.isRemote) {
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, 60 * 20, 0));
                //absorption
                if (!(par3EntityPlayer.isPotionActive(MobEffectList.field_76444_x) && par3EntityPlayer.getActivePotionEffect(MobEffectList.field_76444_x).getAmplifier() == 0)){
                    par3EntityPlayer.removePotionEffect(MobEffectList.field_76444_x.id);
                    par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.field_76444_x.id, 20 * 20, 0));
                }
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.fireResistance.id, 10 * 20, 0));
                par3EntityPlayer.addPotionEffect(new MobEffect(MobEffectList.resistance.id, 10 * 20, 0));
            }
        }
    }

}
