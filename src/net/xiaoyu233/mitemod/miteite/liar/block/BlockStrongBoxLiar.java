package net.xiaoyu233.mitemod.miteite.liar.block;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(BlockStrongbox.class)
public class BlockStrongBoxLiar extends BlockChest {
    @Stealing
    protected BlockStrongBoxLiar(int id, EnumChestType chest_type, Material material) {
        super(id, chest_type, material);
    }

    @Override
    public void onBlockAboutToBeBroken(BlockBreakInfo info) {
        EntityHuman player = info.getResponsiblePlayer();
        if (player != null && !player.inCreativeMode() && info.world.isWorldServer() && !((TileEntityStrongbox)info.tile_entity).isOwner(player)){
            info.world.getAsWorldServer().p().getAuxLogAgent().a("Player " + player.c_() + " tried to break personal chest of " + ((TileEntityStrongbox)info.tile_entity).owner_name);
            info.world.getAsWorldServer().p().af().a(ChatMessage.e("[Server]:").a(ChatMessage.e("玩家 " + player.c_() + " 尝试破坏玩家 " + ((TileEntityStrongbox)info.tile_entity).owner_name + " 的私人金属箱子").a(EnumChatFormat.o)));
        }
        super.onBlockAboutToBeBroken(info);
    }
}
