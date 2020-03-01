package net.xiaoyu233.mitemod.miteite.liar.world;

import net.minecraft.ChatMessage;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumChatFormat;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(MinecraftServer.class)
public class MinecraftServerLiar {
    public static void setTreacheryDetected() {}
    public void playerLoggedIn(EntityPlayer par1EntityPlayerMP) {
        par1EntityPlayerMP.a(ChatMessage.e("[Server]:").a(ChatMessage.e("MITE-ITE模组已加载,当前版本:").a(EnumChatFormat.c)).a(ChatMessage.e(
                Constant.MITE_ITE_VERSION).a(EnumChatFormat.e)));
        par1EntityPlayerMP.a(ChatMessage.e("[MITE-ITE]:").a(ChatMessage.e("MITE-ITE 由").a(ChatMessage.e("XiaoYu233").a(EnumChatFormat.p)).a(" 制作,作者主页:").a(EnumChatFormat.e).
                a(ChatMessage.e("https://afdian.net/@XiaoYu233").a(EnumChatFormat.c))));
    }
    @Stealing
    public void a(ChatMessage par1ChatMessageComponent){}
}
