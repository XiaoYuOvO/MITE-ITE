package net.xiaoyu233.mitemod.miteite.achievement;

import net.minecraft.Achievement;
import net.minecraft.AchievementList;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class Achievements {

    public static Achievement vibraniumIngot;
    public static Achievement wearAllVibraniumPlateArmor;
    public static Achievement vibraniumAnvil;
    public static Achievement vibraniumFurnace;
    public static void registerAchievements(){
        vibraniumIngot = new Achievement(getNextAchievementID(),"vibraniumIngot",-2,17, Items.VIBRANIUM_INGOT,
                AchievementList.adamantiumIngot).registerAchievement();
        wearAllVibraniumPlateArmor = new Achievement(getNextAchievementID(),"vibraniumArmor",12,1,Items.VIBRANIUM_CHESTPLATE,AchievementList.wearAllAdamantiumPlateArmor).setSpecial().registerAchievement();
        vibraniumAnvil = new Achievement(getNextAchievementID(),"vibraniumAnvil",0,17, Blocks.anvilVibranium,vibraniumIngot).setSpecial().registerAchievement();
        vibraniumFurnace = new Achievement(getNextAchievementID(),"vibraniumFurnace",-4,17,Blocks.furnaceVibraniumIdle,vibraniumIngot).setSpecial().registerAchievement();
    }
    private static int getNextAchievementID(){
        return Constant.nextAchievementID++;
    }
}
