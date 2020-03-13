package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(PlayerInventory.class)
public class PlayerInventoryLiar {
    @Liar
    private EntityHuman d;
    @Liar
    public ItemStack[] b;

    public void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
        if (this.d.onClient()) {
            atv.setErrorMessage("InventoryPlayer.damageArmor: called on client?");
        }

        if (this.d.isWearingDamageableItems(true) && !this.d.inCreativeMode()) {
            if (damage_source == null || !damage_source.e()) {
                if (result == null) {
                    result = new EntityDamageResult(this.d);
                }

                int amount_remaining = (int)amount;
                if (amount_remaining < 1) {
                    amount_remaining = 1;
                }

                while(amount_remaining > 0 && this.d.isWearingDamageableItems(true)) {
                    int armor_index = this.d.aD().nextInt(this.b.length);
                    ItemStack item_stack = this.b[armor_index];
                    if (item_stack != null && item_stack.b() instanceof ItemArmor) {
                        int portion;
                        if (this.getNumberOfArmorPiecesEquipped() == 1) {
                            portion = amount_remaining;
                        } else {
                            portion = this.d.aD().nextInt(amount_remaining) + 1;
                        }

                        float durability_modifier = 0f;
                        if (item_stack.q() != null) {
                            durability_modifier = ArmorModifierTypes.DURABILITY_MODIFIER.getModifierValue(item_stack.e);
                        }

                        result.applyArmorDamageResult(item_stack.tryDamageItem(damage_source, (int) Math.max((1-durability_modifier) * portion,1), this.d));
                        item_stack.b().addExpForTool(item_stack,this.d,portion);
                        if (item_stack.b == 0) {
                            this.b[armor_index] = null;
                        }

                        amount_remaining -= portion;
                    } else {
                        amount_remaining -= this.d.aD().nextInt(amount_remaining) + 1;
                    }
                }

            }
        }
    }

    @Stealing
    private int getNumberOfArmorPiecesEquipped() {
        return 0;
    }
}
