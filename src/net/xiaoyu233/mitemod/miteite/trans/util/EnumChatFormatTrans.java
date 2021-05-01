package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EnumChatFormat;
import net.xiaoyu233.mitemod.miteite.util.EnumChatFormats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnumChatFormat.class)
public class EnumChatFormatTrans {
   @Overwrite
   public static EnumChatFormat getByChar(char c) {
      for(int i = 0; i < EnumChatFormats.VALUES.size(); ++i) {
         if (EnumChatFormats.VALUES.get(i).func_96298_a() == c) {
            return EnumChatFormats.VALUES.get(i);
         }
      }

      return null;
   }
}
