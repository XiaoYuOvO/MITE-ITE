package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;

import java.util.function.Predicate;


//TODO Weapon Modifiers{
//  自然祝福 - 每一个怪物被杀死都有概率恢复饱食度.有时在杀死一群怪物后,有概率对玩家进行治疗。
//  自然之力 - 攻击一个怪物：20%概率燃烧目标.20%概率给予玩家速度效果.20%概率给予玩家力量效果。
//  启示录 - 你的受害者将会变得奄奄一息,并且会受到越来越多凋零效果的折磨。
//  恶魔之力 - 对非主世界的怪物造成额外的伤害。
//  百折不挠 - 当你的生命值越低，造成的伤害也就越高。
//  海纳百川 - 你的生命值越高，造成的伤害也就越高。
//  不动如山 - 格挡时可以减少更多的伤害
//  可燃 - 火焰附加
//  剧毒 - 给予目标中毒效果
// }
// Tool Modifiers{
//  震击 - 挖掘方块时充能,到达一定层数后提供一段时间的急迫和速度
//  老成 - 挖掘方块时有几率掉落经验
//  自然祝福 - 每挖掘一个方块都有概率恢复饱食度.有时在挖掘一定数量的方块后,有概率对玩家进行治疗。
//  圣盾 - 当这个工具在你手中时,你将被赋予抗火效果.此外,损害减少,但耐久也会随之减低。
//  不动如山 - 格挡时可以减少更多的伤害
//  自动冶炼 - 自动烧制挖掘等级比此属性级数小的目标方块
// }

public enum ToolModifierTypes implements ItemModifierTypes{
    //Tool Modifiers
    EFFICIENCY_MODIFIER(0.25F,"急速",EnumChatFormat.DARK_RED,20, (stack -> !ToolModifierTypes.isWeapon(stack)),10),
    AQUADYNAMIC_MODIFIER(1.25F,"喜水",EnumChatFormats.LIGHT_YELLOW_GREEN,10, (stack -> !ToolModifierTypes.isWeapon(stack)),5),
    DURABILITY_MODIFIER(0.1F,"耐久",EnumChatFormat.DARK_PURPLE,40,(stack -> true),5),
    DAMAGE_MODIFIER(1.0F,"锋利", EnumChatFormat.WHITE,20, (ToolModifierTypes::isWeapon),10),
    SLOWDOWN_MODIFIER(1.0F,"织网",EnumChatFormats.LIGHT_BLUE,10, ToolModifierTypes::isWeapon,5),
    UNNATURAL_MODIFIER(0.1f,"超自然",EnumChatFormat.LIGHT_GRAY,6, itemStack -> !ToolModifierTypes.isWeapon(itemStack),5),
    DEMON_POWER(0.25f,"恶魔之力",EnumChatFormat.RED,4, ToolModifierTypes::isWeapon, 1),
    GEOLOGY(0.5f,"地质学",EnumChatFormat.valueOf("GOLD"),2,itemStack -> itemStack.getItem() instanceof ItemPickaxe,1);
//    BEHEADING_MODIFIER(0.02f,"斩首",EnumChatFormats.DEAR_GREEN,1, ToolModifierTypes::isWeapon, 5);
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack> canApplyTo;
    private final int maxLevel;
    ToolModifierTypes(float levelAddition, String displayName, EnumChatFormat color, int weight, Predicate<ItemStack> canApplyTo,int maxLevel){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = color;
        this.weight = weight;
        this.canApplyTo = canApplyTo;
        this.maxLevel = maxLevel;
    }

    public static boolean isWeapon(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemSword || item instanceof ItemBattleAxe || item instanceof ItemWarHammer;
    }

    @Override
    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag);
    }

    @Override
    public int getModifierLevel(NBTTagCompound itemTag) {
        int lvl = 0;
        if (itemTag != null && itemTag.hasKey("modifiers")) {
            NBTTagCompound modifiers = itemTag.getCompoundTag("modifiers");
            if (modifiers.hasKey(this.nbtName)) {
                lvl = modifiers.getInteger(this.nbtName);
            }
        }

        return lvl;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }

    @Override
    public boolean canApplyTo(ItemStack itemStack) {
        return this.canApplyTo.test(itemStack);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}
