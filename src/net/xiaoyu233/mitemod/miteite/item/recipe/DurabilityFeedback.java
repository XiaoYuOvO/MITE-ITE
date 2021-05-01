package net.xiaoyu233.mitemod.miteite.item.recipe;

import net.minecraft.ItemStack;

public class DurabilityFeedback implements IFaultFeedback {
    private final Type feedbackType;

    private DurabilityFeedback(Type feedbackType) {
        this.feedbackType = feedbackType;
    }

    public static DurabilityFeedback of(Type type){
        return new DurabilityFeedback(type);
    }

    @Override
    public ItemStack accept(ItemStack itemStack) {
        int rawDamage = itemStack.getItemDamage();
        int maxDamage = itemStack.getMaxDamage();
        int resultDamage = maxDamage - this.feedbackType.acceptDurability(maxDamage - rawDamage);
        if (resultDamage > maxDamage){
            return null;
        }else {
            return itemStack.setItemDamage(resultDamage);
        }
    }

    @Override
    public String getName() {
        return "forging.failFeedback.durability."+ this.feedbackType.getSuffix();
    }

    @Override
    public int getData() {
        return this.feedbackType.getData();
    }

    public interface Type{
        static Type ofPercentage(int percentage){
            return new Type() {
                @Override
                public int acceptDurability(int raw) {
                    return Math.round(raw * (1 - percentage / 100f));
                }

                @Override
                public int getData() {
                    return percentage;
                }

                @Override
                public String getSuffix() {
                    return "percentage";
                }
            };
        }
        static Type ofAmount(int amount){
            return new Type() {
                @Override
                public int acceptDurability(int raw) {
                    return raw - amount;
                }

                @Override
                public int getData() {
                    return amount;
                }

                @Override
                public String getSuffix() {
                    return "amount";
                }
            };
        }
        int acceptDurability(int raw);
        int getData();
        String getSuffix();
    }
}
