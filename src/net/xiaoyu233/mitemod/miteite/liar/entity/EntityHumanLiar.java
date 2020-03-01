package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityHuman.class)
public class EntityHumanLiar{

    @Liar
    public PlayerInventory bn;

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
