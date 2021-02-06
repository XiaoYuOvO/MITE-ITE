package net.xiaoyu233.mitemod.miteite.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.HandleChatCommandEvent;
import net.xiaoyu233.fml.reload.event.PlayerLoggedInEvent;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class MITEITEEvents {
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event){
        event.getPlayer().a(
                ChatMessage.e("[Server] ").a(ChatMessage.e("MITE-ITE模组已加载,当前版本:").a(EnumChatFormat.c)).a(ChatMessage.e(
                        Constant.MITE_ITE_VERSION).a(EnumChatFormat.e)));
        event.getPlayer().a(ChatMessage.e("[MITE-ITE]:").a(ChatMessage.e("MITE-ITE 由").a(ChatMessage.e("XiaoYu233").a(EnumChatFormat.p)).a(" 制作,作者主页:").a(EnumChatFormat.e).
                a(ChatMessage.e("https://afdian.net/@XiaoYu233").a(EnumChatFormat.c))));
    }

    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event){
        String par2Str = event.getCommand();
        EntityPlayer player = event.getPlayer();
        if (Minecraft.inDevMode()) {
            ICommandListener par1ICommandSender = event.getListener();
            World world = event.getWorld();
            if (par2Str.startsWith("itemlevel setLevel")) {
                ItemStack itemStack = player.getHeldItemStack();
                if (itemStack.e != null) {
                    NBTTagCompound compound = itemStack.e;
                    if (compound.b("tool_level")) {
                        compound.a("tool_level", Integer.parseInt(par2Str.substring(19)));
                        event.setExecuteSuccess(true);
                    }
                }
            }
            if (par2Str.startsWith("itemlevel setXp")) {
                ItemStack itemStack = player.getHeldItemStack();
                if (itemStack.e != null) {
                    NBTTagCompound compound = itemStack.e;
                    if (compound.b("tool_exp")) {
                        compound.a("tool_exp", Integer.parseInt(par2Str.substring(16)));
                        event.setExecuteSuccess(true);
                    }
                }
            }
            // /itemlevel addAttr slowdown_modifier 1.0
            if (par2Str.startsWith("itemlevel addAttrA")) {
                ItemStack itemStack = player.getHeldItemStack();
                if (itemStack.e != null) {
                    NBTTagCompound compound = itemStack.e;
                    if (compound.b("modifiers")) {
                        String modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ToolModifierTypes modifierType = ToolModifierTypes.valueOf(modifierName);
                        int level = modifierType.getModifierLevel(compound);
                        NBTTagCompound modifiers = compound.l("modifiers");
                        modifiers.a(modifierType.nbtName, level + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }
            if (par2Str.startsWith("itemlevel addAttrB")) {
                ItemStack itemStack = player.getHeldItemStack();
                if (itemStack.e != null) {
                    NBTTagCompound compound = itemStack.e;
                    if (compound.b("modifiers")) {
                        String modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ArmorModifierTypes modifierType = ArmorModifierTypes.valueOf(modifierName);
                        int level = modifierType.getModifierLevel(compound);
                        NBTTagCompound modifiers = compound.l("modifiers");
                        modifiers.a(modifierType.nbtName, level + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }


            if (par2Str.startsWith("teleport ")) {
                System.out.println(par2Str);
                String xyz = par2Str.substring(9);
                String[] pos = xyz.split(" ");
                double[] poses = new double[3];
                for (int i = 0, posLength = pos.length; i < posLength; i++) {
                    String po = pos[i];
                    try {
                        poses[i] = Double.parseDouble(po);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                player.a(poses[0], poses[1], poses[2]);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("xsummon")) {
                int id = Integer.parseInt(par2Str.substring(8));
                Entity entity = EntityTypes.a(id, world);
                if (entity != null) {
                    entity.b(player.u, player.v, player.w);
                    if (entity instanceof  EntityInsentient){
                        ((EntityInsentient) entity).a((GroupDataEntity) null);
                    }
                    world.d(entity);
                    par1ICommandSender.a(ChatMessage.d("已生成实体 " + entity.toString()).a(EnumChatFormat.LIGHT_GRAY));
                } else {
                    par1ICommandSender.a(ChatMessage.d("无法生成实体:ID为 " + id + " 的实体不存在!").a(EnumChatFormat.e));
                }
                event.setExecuteSuccess(true);
            }
        }
    }
}
