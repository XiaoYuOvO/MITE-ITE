package net.xiaoyu233.mitemod.miteite;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.xiaoyu233.fml.util.EnumExtends;

public class EarlyRiser implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        System.out.println("[MITE-ITE] Early riser registering chat formatting");
        EnumExtends.CHAT_FORMATTING.addEnum("LIGHT_BLUE",'s',151,215,224);
        EnumExtends.CHAT_FORMATTING.addEnum("LIGHT_YELLOW_GREEN",'t',126,222,188);
        EnumExtends.CHAT_FORMATTING.addEnum("DARK_GREY",'u',16,87,75);
        EnumExtends.CHAT_FORMATTING.addEnum("LIGHT_ORANGE",'v',234,158,50);
//        EnumExtends.CHAT_FORMATTING.addEnum("DARK_RED",'w',134,45,45);
        EnumExtends.CHAT_FORMATTING.addEnum("SILVER",'x', 209,236,246);
//        EnumExtends.CHAT_FORMATTING.addEnum("DEEP_GREEN",'y',16,87,75);

        EnumExtends.EQUIPMENT_MATERIAL.addEnum("VIBRANIUM", ()->new Object[]{512.0f, 120, net.minecraft.EnumQuality.legendary, "vibranium"});
    }
}
