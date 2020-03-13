package net.xiaoyu233.mitemod.miteite.liar.util;

import net.minecraft.EnumChatFormat;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;
import team.unknowndomain.liar.annotation.Deceive;

@Deceive(EnumChatFormat.class)
public class EnumChatFormatLiar {
    public static EnumChatFormat getByChar(char c) {
        for(int i = 0; i < EnumChatFormats.values.size(); ++i) {
            if (EnumChatFormats.values.get(i).a() == c) {
                return EnumChatFormats.values.get(i);
            }
        }

        return null;
    }
}
