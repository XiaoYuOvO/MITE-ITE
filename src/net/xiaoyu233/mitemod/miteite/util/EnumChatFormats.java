package net.xiaoyu233.mitemod.miteite.util;

import com.google.common.collect.Lists;
import net.minecraft.EnumChatFormat;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EnumChatFormats {
    private static ConstructorAccessor constructorAccessor;
    private static int ordinary = EnumChatFormat.values().length;
    //Must put at top
    static {
        try {
            Constructor<?> CTOR = EnumChatFormat.class.getDeclaredConstructors()[1];
            Method acquireConstructorAccessor;
            acquireConstructorAccessor = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
            acquireConstructorAccessor.setAccessible(true);
            acquireConstructorAccessor.invoke(CTOR);
            Field field = Constructor.class.getDeclaredField("constructorAccessor");
            field.setAccessible(true);
            constructorAccessor = (ConstructorAccessor) field.get(CTOR);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    //Enums
    public static ArrayList<EnumChatFormat> values = Lists.newArrayList(EnumChatFormat.values());
    public static EnumChatFormat LIGHT_BLUE = newEnumChatFormat("LIGHT_BLUE",'s',151,215,224);
    public static EnumChatFormat LIGHT_YELLOW_GREEN = newEnumChatFormat("LIGHT_YELLOW_GREEN",'t',126,222,188);
    public static EnumChatFormat DARK_GREY = newEnumChatFormat("DARK_GREY",'u',16,87,75);
    public static EnumChatFormat LIGHT_ORANGE = newEnumChatFormat("DARK_GREY",'v',234,158,50);
    public static EnumChatFormat DARK_RED = newEnumChatFormat("DARK_RED",'w',134,45,45);
    public static EnumChatFormat SILVER = newEnumChatFormat("SILVER",'x', 209,236,246);
    private static EnumChatFormat newEnumChatFormat(String name,char c, int r, int g, int b){
        try {
            EnumChatFormat format =  (EnumChatFormat) constructorAccessor.newInstance(new Object[]{name,ordinary++,c,r,g,b});
            values.add(format);
            return format;
        } catch (InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
