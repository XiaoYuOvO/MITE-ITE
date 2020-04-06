package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EnumChatFormat;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;

@Transform(EnumChatFormat.class)
public class EnumChatFormatTrans {
    public static EnumChatFormat getByChar(char c) {
        for(int i = 0; i < EnumChatFormats.VALUES.size(); ++i) {
            if (EnumChatFormats.VALUES.get(i).a() == c) {
                return EnumChatFormats.VALUES.get(i);
            }
        }

        return null;
    }
}
