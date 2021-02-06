package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(Damage.class)
public class DamageTrans {
    @Link
    private DamageSource source;
    @Link
    private float amount;

    public Entity getResponsibleEntityP() {
        return this.source.getResponsibleEntity();
    }

    protected float applyTargetDefenseModifiers(EntityLiving target, EntityDamageResult result) {
        if (target.onClient()) {
            Minecraft.setErrorMessage("applyTargetDefenseModifiers: called on client?");
        }

        if (this.amount <= 0.0F) {
            return 0.0F;
        } else if (this.isAbsolute()) {
            return this.amount;
        } else {
            if (target instanceof EntityHuman) {
                EntityHuman player = (EntityHuman)target;
                if (!this.bypassesMundaneArmor() && player.bv()) {
                    this.amount /= 2.0F;
                    if (this.amount < 1.0F) {
                        this.amount = 1.0F;
                    }

                    ItemStack item_stack = player.getHeldItemStack();
                    if (item_stack != null && item_stack.b() instanceof ItemTool) {
                        ItemTool item_tool = (ItemTool)item_stack.b();
                        result.applyHeldItemDamageResult(item_stack.tryDamageItem(DamageSource.j, (int)(this.amount * (float)item_tool.getToolDecayFromAttackingEntity(item_stack, null)), target));
                    }
                }
            }

            float total_protection = target.getTotalProtection(this.getSource());
            ItemStack[] wornItems = target.getWornItems();
            if (wornItems.length >= 4){
                boolean allProtectionV = true;
                for (ItemStack armor : wornItems) {
                    if (armor != null){
                        allProtectionV = armor.getEnchantmentLevel(Enchantment.d) >= 5 && allProtectionV;
                    }else {
                        allProtectionV = false;
                    }
                }
                if (allProtectionV){
                    this.amount -= Math.min(this.amount * MITEITEMod.CONFIG.get(Config.ConfigEntry.ALL_PROTECTION_V_DEFENCE_FRACTION),this.amount);
                }
            }
            DebugAttack.setTargetProtection(total_protection);
            float amount_dealt_to_armor = Math.min(target.getProtectionFromArmor(this.getSource(), false), this.amount);
            target.tryDamageArmorP(this.getSource(), amount_dealt_to_armor, result);
            DebugAttack.setDamageDealtToArmor(amount_dealt_to_armor);
            float piercing = Enchantment.piercing.getLevelFraction(this.getItemAttackedWith()) * 5.0F;
            float effective_protection = Math.max(total_protection - piercing, 0.0F);
            DebugAttack.setPiercing(piercing);
            if (target instanceof EntityHuman && effective_protection >= this.amount) {
                int delta = (int)(effective_protection - this.amount);

                for(int i = -1; i < delta; ++i) {
                    if (target.aD().nextFloat() < 0.2F) {
                        return 0.0F;
                    }
                }
            }

            return Math.max(this.amount - effective_protection, 1.0F);
        }
    }

    @Marker
    private boolean bypassesMundaneArmor() {
        return false;
    }

    @Marker
    private ItemStack getItemAttackedWith() {
        return null;
    }

    @Marker
    private boolean isAbsolute() {
        return false;
    }

    @Marker
    private DamageSource getSource() {
        return null;
    }
}
