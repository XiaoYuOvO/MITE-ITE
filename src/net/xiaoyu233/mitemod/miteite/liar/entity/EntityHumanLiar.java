package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Deceive(EntityHuman.class)
public class EntityHumanLiar extends EntityLiving {

    @Liar
    public PlayerInventory bn;

    @Stealing
    public EntityHumanLiar(World par1World) {
        super(par1World);
    }

    @Override
    @Stealing
    public void c(int i, ItemStack itemStack) {

    }

    @Override
    @Stealing
    public ItemStack n(int i) {
        return null;
    }

    @Override
    @Stealing
    public ItemStack getHeldItemStack() {
        return null;
    }

    @Override
    @Stealing
    public void setHeldItemStack(ItemStack itemStack) {

    }

    @Override
    @Stealing
    public ItemStack[] ae() {
        return new ItemStack[0];
    }

    @Override
    @Stealing
    public boolean setWornItem(int i, ItemStack itemStack) {
        return false;
    }

    @Override
    @Stealing
    public boolean isWearing(ItemStack itemStack) {
        return false;
    }

    @Override
    @Stealing
    public ItemStack[] getWornItems() {
        return new ItemStack[0];
    }

    public float getCurrentPlayerStrVsBlock(int x, int y, int z, boolean apply_held_item) {
        Block block = Block.s[super.q.a(x, y, z)];
        if (block == null) {
            return 0.0F;
        } else {
            float block_hardness = super.q.getBlockHardness(x, y, z);
            if (block_hardness == 0.0F) {
                return 1.0F;
            } else {
                float min_str_vs_block = -3.4028235E38F;
                Item held_item = super.getHeldItem();
                float str_vs_block;
                if (block.isPortable(super.q, this, x, y, z)) {
                    str_vs_block = min_str_vs_block = 4.0F * block_hardness;
                } else {
                    int metadata;
                    if (apply_held_item && held_item != null) {
                        metadata = super.q.h(x, y, z);
                        str_vs_block = held_item.getStrVsBlock(block, metadata,getHeldItemStack(),dyCast(this));
                        if (str_vs_block < 1.0F) {
                            return this.getCurrentPlayerStrVsBlock(x, y, z, false);
                        }

                        int var4 = EnchantmentManager.c(this);
                        if (var4 > 0) {
                            float var6 = (float)(var4 * var4 + 1);
                            str_vs_block += var6;
                        }
                    } else {
                        metadata = super.q.h(x, y, z);
                        if (block.cU.requiresTool(block, metadata)) {
                            str_vs_block = 0.0F;
                        } else {
                            str_vs_block = 1.0F;
                        }
                    }
                }

                if (block == Block.ab) {
                    boolean decrease_strength = true;
                    if (apply_held_item && held_item != null && held_item.isTool() && held_item.getAsTool().isEffectiveAgainstBlock(block, 0)) {
                        decrease_strength = false;
                    }

                    if (decrease_strength) {
                        str_vs_block *= 0.2F;
                    }
                }

                if (super.a(MobEffectList.e)) {
                    str_vs_block *= 1.0F + (float)(super.b(MobEffectList.e).c() + 1) * 0.2F;
                }

                if (super.a(MobEffectList.f)) {
                    str_vs_block *= 1.0F - (float)(super.b(MobEffectList.f).c() + 1) * 0.2F;
                }

                if (super.a(Material.h) && !EnchantmentManager.h(dyCast(this))) {
                    str_vs_block /= 5.0F;
                }

                if (!super.F) {
                    str_vs_block /= 5.0F;
                }

                if (!this.hasFoodEnergy()) {
                    str_vs_block /= 5.0F;
                }

                str_vs_block *= 1.0F + this.getLevelModifier(EnumLevelBonus.HARVESTING);
                return Math.max(str_vs_block, min_str_vs_block);
            }
        }
    }

    @Stealing
    public boolean a(Material h) {
        return false;
    }

    @Stealing
    private float getLevelModifier(EnumLevelBonus harvesting) {
        return 0;
    }

    @Stealing
    private boolean hasFoodEnergy() {
        return false;
    }

    @Stealing
    protected static final int getExperienceRequired(int level){
        return 0;
    }

    public static int getExpRequired(int level){
        return getExperienceRequired(level);
    }

    public static int getHealthLimit(int level) {
        return Math.max(Math.min(6 + level / 5 * 2, 40), 6);
    }

    @Override
    public boolean knockBack(Entity attacker, float amount) {
        ItemStack[] itemStacks = this.ae();
        for (int i = 0, itemStacksLength = itemStacks.length; i < itemStacksLength; i++) {
            ItemStack stack = itemStacks[i];
            if (stack!=null) {
                amount *= Math.max(1 - ArmorModifierTypes.STEADY_MODIFIER.getModifierValue(stack.e),0);
            }
        }
        return super.knockBack(attacker, amount);
    }

    private void checkForArmorAchievements() {
        boolean wearing_leather = false;
        boolean wearing_full_suit_plate = true;
        boolean wearing_full_suit_adamantium_plate = true;
        boolean wearing_full_suit_vibranium_plate = true;
        for(int i = 0; i < 4; ++i) {
            if (this.bn.b[i] != null && this.bn.b[i].b() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor)this.bn.b[i].b();
                Material material = armor.getArmorMaterial();
                if (material == Material.leather) {
                    wearing_leather = true;
                }

                if (material != Material.copper && material != Material.silver && material != Material.gold && material != Material.f && material != Material.mithril && material != Material.adamantium && material != Material.ancient_metal) {
                    wearing_full_suit_plate = false;
                }

                if (material != Material.adamantium) {
                    wearing_full_suit_adamantium_plate = false;
                }
                if (material != Materials.vibranium){
                    wearing_full_suit_vibranium_plate = false;
                }
            } else {
                wearing_full_suit_plate = false;
                wearing_full_suit_adamantium_plate = false;
                wearing_full_suit_vibranium_plate = false;
            }
        }

        if (wearing_leather) {
            this.a(AchievementList.wearLeather);
        }

        if (wearing_full_suit_plate) {
            this.a(AchievementList.wearAllPlateArmor);
        }

        if (wearing_full_suit_adamantium_plate) {
            this.a(AchievementList.wearAllAdamantiumPlateArmor);
        }

        if (wearing_full_suit_vibranium_plate){
            this.a(Achievements.wearAllVibraniumPlateArmor);
        }

    }

    public void a(TileEntityBeacon par1TileEntityBeacon) {
    }

    @Stealing
    private void a(Statistic statistic) {

    }
}
