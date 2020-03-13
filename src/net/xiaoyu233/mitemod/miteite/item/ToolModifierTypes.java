package net.xiaoyu233.mitemod.miteite.item;

import com.google.common.collect.Lists;
import net.minecraft.EnumChatFormat;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.xiaoyu233.mitemod.miteite.liar.item.ItemToolLiar;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;
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
//  斩首
//  剧毒 - 给予目标中毒效果
// }
// Tool Modifiers{
//  震击 - 挖掘方块时充能,到达一定层数后提供一段时间的急迫和速度
//  老成 - 挖掘方块时有几率掉落经验
//  自然祝福 - 每挖掘一个方块都有概率恢复饱食度.有时在挖掘一定数量的方块后,有概率对玩家进行治疗。
//  圣盾 - 当这个工具在你手中时,你将被赋予抗火效果.此外,损害减少,但耐久也会随之减低。
//  不动如山 - 格挡时可以减少更多的伤害
//  超自然 - 工具的挖掘等级比目标方块越大,挖掘速度越快
//  自动冶炼 - 自动烧制挖掘等级比此属性级数小的目标方块
// }
@SuppressWarnings("Convert2MethodRef")
public enum  ToolModifierTypes implements ItemModifierTypes<ToolModifierTypes>{
    EFFICIENCY_MODIFIER(0.25F,"急速",EnumChatFormat.e,10, (stack -> !ItemToolLiar.isWeapon(stack))),
    AQUADYNAMIC_MODIFIER(1.25F,"喜水",EnumChatFormats.LIGHT_YELLOW_GREEN,5, (stack -> !ItemToolLiar.isWeapon(stack))),
    DURABILITY_MODIFIER(0.1F,"耐久",EnumChatFormat.f,20,(stack -> true)),
    DAMAGE_MODIFIER(1.0F,"锋利", EnumChatFormat.p,10, stack -> ItemToolLiar.isWeapon(stack)),
    SLOWDOWN_MODIFIER(1.0F,"织网",EnumChatFormats.LIGHT_BLUE,5, stack -> ItemToolLiar.isWeapon(stack));
    public final String nbtName;
    public final float levelAddition;
    public final String displayName;
    public final EnumChatFormat color;
    public final int weight;
    private final Predicate<ItemStack>  canApplyTo;
    ToolModifierTypes(float levelAddition, String displayName, EnumChatFormat color, int weight, Predicate<ItemStack> canApplyTo){
        this.nbtName = this.name().toLowerCase();
        this.levelAddition = levelAddition;
        this.displayName = displayName;
        this.color = color;
        this.weight = weight;
        this.canApplyTo = canApplyTo;
    }

    @Nonnull
    public static ToolModifierTypes getModifierWithWeight(Random rand,ItemStack stack){
        ArrayList<ToolModifierTypes> p_180166_0_ = ToolModifierTypes.getAllCanBeApplied(stack);
        int totalWeight = 0;
        for (ToolModifierTypes modifierTypes : p_180166_0_) {
            totalWeight += modifierTypes.weight;
        }
        int currentWeight = rand.nextInt(totalWeight);
        int x = 0;
        for(int j = p_180166_0_.size(); x < j; ++x) {
            ToolModifierTypes t = p_180166_0_.get(x);
            currentWeight -= t.weight;
            if (currentWeight < 0) {
                return t;
            }
        }

        return null;
    }

    private static ArrayList<ToolModifierTypes> getAllCanBeApplied(ItemStack stack){
        ArrayList<ToolModifierTypes> toolModifierTypes  = Lists.newArrayList(ToolModifierTypes.values());
        toolModifierTypes.removeIf(modifierType -> !modifierType.canApplyTo.test(stack));
        return toolModifierTypes;
    }

    public float getModifierValue(NBTTagCompound itemTag){
        return this.levelAddition * getModifierLevel(itemTag);
    }

    public int getModifierLevel(NBTTagCompound itemTag){
        int lvl = 0;
        if (itemTag != null && itemTag.b("modifiers")) {
            NBTTagCompound modifiers = itemTag.l("modifiers");
            if (modifiers.b(this.nbtName)) {
                lvl = modifiers.e(this.nbtName);
            }
        }
        return lvl;
    }

    @Override
    public String getNbtName() {
        return this.nbtName;
    }
}
