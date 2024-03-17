package net.xiaoyu233.mitemod.miteite.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableRecipes;
import net.xiaoyu233.mitemod.miteite.network.*;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderAncientDragon;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderWanderingWitch;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class MITEITEEvents {
    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event) {
        String par2Str = event.getCommand();
        EntityPlayer player = event.getPlayer();
        ICommandSender commandListener = event.getListener();
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
                Entity entity = EntityList.createEntityByID(id, world);
                if (entity != null) {
                    entity.setPosition(player.posX, player.posY, player.posZ);
                    if (entity instanceof EntityLiving) {
                        ((EntityLiving)entity).onSpawnWithEgg(null);
                    }

                    world.spawnEntityInWorld(entity);
                    commandListener.sendChatToPlayer(ChatMessageComponent.createFromText("已生成实体 " + entity).setColor(EnumChatFormatting.LIGHT_GRAY));
                } else {
                    commandListener.sendChatToPlayer(ChatMessageComponent.createFromText("无法生成实体:ID为 " + id + " 的实体不存在!").setColor(EnumChatFormatting.DARK_RED));
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

            for (Object o : player.getWorldServer().getMinecraftServer().getConfigurationManager().playerEntityList) {
                EntityPlayer currentPlayer = (EntityPlayer) o;
                if (!currentPlayer.inBed()) {
                    allSlept = false;
                    notSleepingPlayers.append(currentPlayer.getEntityName()).append(",");
                }
            }

            if (allSlept) {
                commandListener.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("command.sleep_check.none").setColor(EnumChatFormatting.DARK_GREEN));
            } else {
                commandListener.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("command.sleep_check.msg").addText(notSleepingPlayers.substring(0, notSleepingPlayers.length() - 1)).setColor(EnumChatFormatting.YELLOW));
            }

            event.setExecuteSuccess(true);
        }

    }

    @Subscribe
    public void onPacketRegister(PacketRegisterEvent event){
        event.register(false, true, CPacketStartForging.class);
        event.register(true, false, SPacketFinishForging.class);
        event.register(true, false, SPacketForgingTableInfo.class);
        event.register(true, false, SPacketOverlayMessage.class);
        event.register(false, true, CPacketSyncItems.class);
        event.register(true, false, SPacketCraftingBoost.class);
        event.register(true, true, BiPacketUpdateDefense.class);
        event.register(true,false, SPacketUpdateNutrition.class);
    }

    @Subscribe
    public void onItemRegister(ItemRegistryEvent event){
        Items.registerItems(event);
        Blocks.registerItemBlocks(event);
    }

    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event){
        Items.registerRecipes(event);
        Blocks.registerRecipes(event);
        ForgingTableRecipes.registerAll();
    }

    @Subscribe
    public void onEnchantmentRegister(EnchantmentRegistryEvent event){
        Enchantments.registerEnchantments(event);
    }

    @Subscribe
    public void onAchievementRegister(AchievementRegistryEvent event){
        Achievements.registerAchievements();
    }
    
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        event.getPlayer().sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("[Server] ").appendComponent(ChatMessageComponent.createFromTranslationKey("MITE-ITE模组已加载,当前版本:").setColor(EnumChatFormatting.DARK_GREEN)).appendComponent(ChatMessageComponent.createFromText(Constant.MITE_ITE_VERSION).setColor(EnumChatFormatting.DARK_RED)));
        event.getPlayer().sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("[MITE-ITE]:").appendComponent(ChatMessageComponent.createFromTranslationKey("MITE-ITE 由").appendComponent(ChatMessageComponent.createFromTranslationKey("XiaoYu233").setColor(EnumChatFormatting.WHITE)).addText(" 制作,作者主页:").setColor(EnumChatFormatting.DARK_RED).appendComponent(ChatMessageComponent.createFromTranslationKey("https://afdian.net/@XiaoYu233").setColor(EnumChatFormatting.DARK_GREEN))));
    }

    @Subscribe
    public void onEntityRegister(EntityRegisterEvent event){
        event.register(EntityAncientDragon.class, "EntityTest", 201);
        event.register(EntityRideMarker.class, "EntityMarker", 202);
        event.register(EntityZombieLord.class, "EntityZombieLord", 203, 0xffffff, 0xcccccc);
        event.register(EntityZombiePigmanLord.class, "EntityZombiePigmanLord", 204, 0xffffff, 0xcccccc);
        event.register(EntityAnnihilationSkeleton.class, "EntityAnnihilationSkeleton", 205, 0xffffff, 0xcccccc);
        event.register(EntityWanderingWitch.class, "EntityWanderingWitch", 206, 0xffffff, 0xcccccc);
    }

    @Subscribe
    public void onEntityRendererRegister(EntityRendererRegistryEvent event){
        event.register(EntityAncientDragon.class, new RenderAncientDragon());
        event.register(EntityAnnihilationSkeleton.class, new RenderAnnihilationSkeleton());
        event.register(EntityWanderingWitch.class, new RenderWanderingWitch());
    }
}
