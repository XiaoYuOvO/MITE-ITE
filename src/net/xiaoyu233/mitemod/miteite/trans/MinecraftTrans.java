package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.EnumChatFormat;
import net.minecraft.Minecraft;
import net.minecraft.client.main.Main;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import static net.minecraft.Minecraft.inDevMode;

@Transform(Minecraft.class)
public class MinecraftTrans {
    public static String getVersionDescriptor(boolean include_formatting) {
        String red = include_formatting ? EnumChatFormat.m.toString() : "";
        return "1.6.4-MITE" + "-ITE-" + Constant.MITE_ITE_VERSION + (Main.is_MITE_DS ? "-DS" : "") + " modded by XiaoYu233 " + (inDevMode() ? red + " DEV" : "");
    }
}
