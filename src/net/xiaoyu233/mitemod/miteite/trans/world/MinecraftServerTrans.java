package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.ChatMessage;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;


public class MinecraftServerTrans {
    @Link
    private static boolean treachery_detected;
    @Link
    private static int treachery_shutdown_counter;

    public static void setTreacheryDetected() {
        treachery_detected = false;
        treachery_shutdown_counter = 0;
    }
    @Marker
    public void a(ChatMessage par1ChatMessageComponent){}
}
