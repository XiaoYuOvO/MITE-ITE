package net.xiaoyu233.mitemod.miteite.util;

import com.google.common.collect.Lists;
import net.minecraft.EnumChatFormat;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EnumChatFormats {
    private static ConstructorAccessor constructorAccessor;
    private static int ordinary = EnumChatFormat.values().length;
    //Must put at top
    static {
        try {
            Constructor<?> ctor = EnumChatFormat.class.getDeclaredConstructors()[1];
            Method acquireConstructorAccessor;
            acquireConstructorAccessor = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
            acquireConstructorAccessor.setAccessible(true);
            acquireConstructorAccessor.invoke(ctor);
            Field field = Constructor.class.getDeclaredField("constructorAccessor");
            field.setAccessible(true);
            constructorAccessor = (ConstructorAccessor) field.get(ctor);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private EnumChatFormats(){}
    /**
     * Enums
     * */
    public static final List<EnumChatFormat> VALUES = Lists.newArrayList(EnumChatFormat.values());
    public static final EnumChatFormat LIGHT_BLUE = newEnumChatFormat("LIGHT_BLUE",'s',151,215,224);
    public static final EnumChatFormat LIGHT_YELLOW_GREEN = newEnumChatFormat("LIGHT_YELLOW_GREEN",'t',126,222,188);
    public static final EnumChatFormat DARK_GREY = newEnumChatFormat("DARK_GREY",'u',16,87,75);
    public static final EnumChatFormat LIGHT_ORANGE = newEnumChatFormat("DARK_GREY",'v',234,158,50);
    public static final EnumChatFormat DARK_RED = newEnumChatFormat("DARK_RED",'w',134,45,45);
    public static final EnumChatFormat SILVER = newEnumChatFormat("SILVER",'x', 209,236,246);
    public static final EnumChatFormat DARK_GREEN = newEnumChatFormat("DARK_GREEN",'y',16,87,75);
    private static EnumChatFormat newEnumChatFormat(String name,char c, int r, int g, int b){
        try {
            EnumChatFormat format =  (EnumChatFormat) constructorAccessor.newInstance(new Object[]{name,ordinary++,c,r,g,b});
            VALUES.add(format);
            return format;
        } catch (InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
