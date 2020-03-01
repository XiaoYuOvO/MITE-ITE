package net.xiaoyu233.mitemod.miteite.liar;

import net.minecraft.EnumChatFormat;
import net.minecraft.atv;
import net.minecraft.client.main.Main;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import team.unknowndomain.liar.annotation.Deceive;

import static net.minecraft.atv.inDevMode;

@Deceive(atv.class)
public class MinecraftLiar {
    public static String getVersionDescriptor(boolean include_formatting) {
        String red = include_formatting ? EnumChatFormat.m.toString() : "";
        return "1.6.4-MITE" + "-ITE-" + Constant.MITE_ITE_VERSION + (Main.is_MITE_DS ? "-DS" : "") + " modded by XiaoYu233 " + (inDevMode() ? red + " DEV" : "");
    }
}
