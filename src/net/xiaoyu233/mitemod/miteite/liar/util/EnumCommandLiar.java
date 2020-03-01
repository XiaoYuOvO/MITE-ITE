package net.xiaoyu233.mitemod.miteite.liar.util;

import net.minecraft.EnumCommand;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EnumCommand.class)
public class EnumCommandLiar {
    @Stealing
    static EnumCommand get(int ordinal) {
        return null;
    }
    @Stealing
    static EnumCommand get(String text) {
        return null;
    }
    public static EnumCommand get0(String text){
        return get(text);
    }
    public static EnumCommand get0(int ordinal){
        return get(ordinal);
    }
}
