package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import static net.minecraft.BlockPortal.getDestinationDimensionIdForMetadata;

@Transform(BlockPortal.class)
public class BlockPortalTrans extends Block{
    @Marker
    protected BlockPortalTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }
    @Marker
    public boolean isRunegate(int metadata) {return false;}
    @Marker
    public boolean isPortalToOverworldSpawn(World world, int metadata) {return false;}
    @Marker
    private void initiateRunegateTeleport(WorldServer world, int x, int y, int z, EntityPlayer player, boolean is_portal_to_world_spawn){}
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        EntityHuman player = par5Entity instanceof EntityHuman ? (EntityHuman)par5Entity : null;
        if (player == null || !player.is_runegate_teleporting) {
            int metadata = par1World.h(par2, par3, par4);
            boolean is_runegate = this.isRunegate(metadata);
            boolean is_portal_to_world_spawn = this.isPortalToOverworldSpawn(par1World, metadata);
            if (!is_runegate && !is_portal_to_world_spawn) {
                if (par5Entity.o == null && par5Entity.n == null) {
                    par5Entity.setInPortal(getDestinationDimensionIdForMetadata(metadata));
                }

            } else if (!par1World.I && player != null) {
                if (player.o == null && player.n == null) {
                    if (MITEITEMod.CONFIG.get(Config.ConfigEntry.LOG_PLAYERS_INTERACT_WITH_PORTAL)){
                        ((WorldServer) par1World).p().af().a(ChatMessage.b("gameplay.portal_door.interact",player.an()).a(EnumChatFormat.e));
                    }
                    this.initiateRunegateTeleport((WorldServer)par1World, par2, par3, par4, (EntityPlayer)player, is_portal_to_world_spawn);
                }
            }
        }
    }
}
