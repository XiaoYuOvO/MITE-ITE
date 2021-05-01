package net.xiaoyu233.mitemod.miteite.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.HandleChatCommandEvent;
import net.xiaoyu233.fml.reload.event.PacketRegisterEvent;
import net.xiaoyu233.fml.reload.event.PlayerLoggedInEvent;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.network.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class MITEITEEvents {
    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event) {
        String par2Str = event.getCommand();
        EntityPlayer player = event.getPlayer();
        ICommandListener commandListener = event.getListener();
        if (Minecraft.inDevMode()) {
            World world = event.getWorld();
            ItemStack itemStack;
            NBTTagCompound compound;
            if (par2Str.startsWith("itemlevel setLevel")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("tool_level")) {
                        compound.setInteger("tool_level", Integer.parseInt(par2Str.substring(19)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("itemlevel setXp")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("tool_exp")) {
                        compound.setInteger("tool_exp", Integer.parseInt(par2Str.substring(16)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            String modifierName;
            int posLength;
            NBTTagCompound modifiers;
            if (par2Str.startsWith("itemlevel addAttrA")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("modifiers")) {
                        modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ToolModifierTypes modifierType = ToolModifierTypes.valueOf(modifierName);
                        posLength = modifierType.getModifierLevel(compound);
                        modifiers = compound.getCompoundTag("modifiers");
                        modifiers.setInteger(modifierType.nbtName, posLength + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("itemlevel addAttrB")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("modifiers")) {
                        modifierName = par2Str.substring(19, par2Str.lastIndexOf(" ")).toUpperCase();
                        ArmorModifierTypes modifierType = ArmorModifierTypes.valueOf(modifierName);
                        posLength = modifierType.getModifierLevel(compound);
                        modifiers = compound.getCompoundTag("modifiers");
                        modifiers.setInteger(modifierType.nbtName, posLength + Integer.parseInt(par2Str.substring(19 + modifierName.length() + 1)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            String msg;
            if (par2Str.startsWith("teleport ")) {
                System.out.println(par2Str);
                msg = par2Str.substring(9);
                String[] pos = msg.split(" ");
                double[] poses = new double[3];
                int i = 0;

                for(posLength = pos.length; i < posLength; ++i) {
                    String po = pos[i];

                    try {
                        poses[i] = Double.parseDouble(po);
                    } catch (NumberFormatException var13) {
                        var13.printStackTrace();
                    }
                }

                player.setPositionAndUpdate(poses[0], poses[1], poses[2]);
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("xsummon")) {
                int id = Integer.parseInt(par2Str.substring(8));
                Entity entity = EntityTypes.createEntityByID(id, world);
                if (entity != null) {
                    entity.setPosition(player.posX, player.posY, player.posZ);
                    if (entity instanceof EntityInsentient) {
                        ((EntityInsentient)entity).onSpawnWithEgg(null);
                    }

                    world.spawnEntityInWorld(entity);
                    commandListener.sendChatToPlayer(ChatMessage.createFromText("已生成实体 " + entity).setColor(EnumChatFormat.LIGHT_GRAY));
                } else {
                    commandListener.sendChatToPlayer(ChatMessage.createFromText("无法生成实体:ID为 " + id + " 的实体不存在!").setColor(EnumChatFormat.DARK_RED));
                }

                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("forging_grade set")) {
                itemStack = player.getHeldItemStack();
                if (itemStack.stackTagCompound != null) {
                    compound = itemStack.stackTagCompound;
                    if (compound.hasKey("forging_grade")) {
                        compound.setInteger("forging_grade", Integer.parseInt(par2Str.substring(18)));
                        event.setExecuteSuccess(true);
                    }
                }
            }

            if (par2Str.startsWith("overlayMsg")) {
                msg = par2Str.substring("overlayMsg".length());
                player.sendPacket(new SPacketOverlayMessage(msg, 16777215, 100));
                event.setExecuteSuccess(true);
            }

            if (par2Str.startsWith("emptyItem")) {
                Item item = Item.itemsList[Integer.parseInt(par2Str.substring("overlayMsg".length()))];
                if (item != null) {
                    player.setHeldItemStack(new ItemStack(item, 0, 0));
                }
            }
        }

        if (par2Str.startsWith("sleep")) {
            StringBuilder notSleepingPlayers = new StringBuilder();
            boolean allSlept = true;

            for (Object o : player.getWorldServer().p().getConfigurationManager().playerEntityList) {
                EntityPlayer currentPlayer = (EntityPlayer) o;
                if (!currentPlayer.inBed()) {
                    allSlept = false;
                    notSleepingPlayers.append(currentPlayer.getEntityName()).append(",");
                }
            }

            if (allSlept) {
                commandListener.sendChatToPlayer(ChatMessage.createFromTranslationKey("command.sleep_check.none").setColor(EnumChatFormat.DARK_GREEN));
            } else {
                commandListener.sendChatToPlayer(ChatMessage.createFromTranslationKey("command.sleep_check.msg").addText(notSleepingPlayers.substring(0, notSleepingPlayers.length() - 1)).setColor(EnumChatFormat.YELLOW));
            }

            event.setExecuteSuccess(true);
        }

    }

    @Subscribe
    public void onPacketRegister(PacketRegisterEvent event){
        event.register(134, false, true, CPacketStartForging.class);
        event.register(135, true, false, SPacketFinishForging.class);
        event.register(136, true, false, SPacketForgingTableInfo.class);
        event.register(137, true, false, SPacketOverlayMessage.class);
        event.register(138, false, true, CPacketSyncItems.class);
        event.register(139, true, false, SPacketCraftingBoost.class);
    }

//    @Subscribe
//    public void onGuiOverlayDraw(GuiOverlayDrawEvent event){
//        Minecraft minecraft = event.getMinecraft();
//        GuiIngame gui = event.getGuiIngame();
//        float protein = minecraft.h.getProtein();
//        float phytonutrients = minecraft.h.getPhytonutrients();
//        int y = event.getGuiUp()+32 + (Config.ConfigEntry.HEALTH_BAR_Y_OFFSET);
//        int x = event.getVar12()+240 + (Config.ConfigEntry.HEALTH_BAR_X_OFFSET);
//        GL11.glPushMatrix();
//        GL11.glPushMatrix();
//        gui.b(minecraft.l,(int)phytonutrients + "/" + 160000, x-167,y-8,0xffffff);
//        GL11.glScalef(0.6f,1f,1f);
//        minecraft.J().a(Constant.icons_ite);
//        gui.b(x,y,0,106,182,6);
//        gui.b(x,y,0,94, (int) (182f * (  phytonutrients / 160000f )),6);
//        GL11.glPopMatrix();
//        x=event.getVar12() - 303;
//        GL11.glPushMatrix();
//        gui.b(minecraft.l,(int)protein + "/" + 160000, x,y-8,0xffffff);
//        GL11.glScalef(0.6f,1f,1f);
//        minecraft.J().a(Constant.icons_ite);
//        gui.b(x,y,0,106,182,6);
//        gui.b(x,y,0,100,(int) (182f * (  protein / 160000f )),6);
//        GL11.glPopMatrix();
//        GL11.glPopMatrix();
//
//        if (gui.overlayMsgTime > 0) {
//            minecraft.C.a("overlayMessage");
//            awf window = new awf(minecraft.u, minecraft.d, minecraft.e);
//            int var6 = window.a();
//            int var7 = window.b();
//            GL11.glPushMatrix();
//            GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 68), 0.0F);
//            GL11.glEnable(3042);
//            GL11.glBlendFunc(770, 771);
//            gui.b(gui.overlayMsg, - minecraft.l.a(this.overlayMsg) / 2, -4, gui.overlayMsgColor);
//            GL11.glDisable(3042);
//            GL11.glPopMatrix();
//            minecraft.C.b();
//            gui.overlayMsgTime--;
//        }
//    }
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        event.getPlayer().sendChatToPlayer(ChatMessage.createFromTranslationKey("[Server] ").appendComponent(ChatMessage.createFromTranslationKey("MITE-ITE模组已加载,当前版本:").setColor(EnumChatFormat.DARK_GREEN)).appendComponent(ChatMessage.createFromText(Constant.MITE_ITE_VERSION).setColor(EnumChatFormat.DARK_RED)));
        event.getPlayer().sendChatToPlayer(ChatMessage.createFromTranslationKey("[MITE-ITE]:").appendComponent(ChatMessage.createFromTranslationKey("MITE-ITE 由").appendComponent(ChatMessage.createFromTranslationKey("XiaoYu233").setColor(EnumChatFormat.WHITE)).addText(" 制作,作者主页:").setColor(EnumChatFormat.DARK_RED).appendComponent(ChatMessage.createFromTranslationKey("https://afdian.net/@XiaoYu233").setColor(EnumChatFormat.DARK_GREEN))));
    }
}
