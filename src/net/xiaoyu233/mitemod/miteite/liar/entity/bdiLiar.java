package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(bdi.class)
public class bdiLiar extends bex {
    @Liar
    private final bcw a;
    @Liar
    private int delta_tournament_score;
    @Liar
    private int delta_tournament_score_opacity;
    @Liar
    private int tournament_score;

    public bdiLiar(atv par1Minecraft, World par2World, aus par3Session, bcw par4NetClientHandler) {
        super(par1Minecraft, par2World, par3Session, 0);
        this.a = par4NetClientHandler;
        if (DedicatedServer.tournament_type == EnumTournamentType.score) {
            this.delta_tournament_score = par1Minecraft.last_known_delta_tournament_score;
            this.delta_tournament_score_opacity = par1Minecraft.last_known_delta_tournament_score_opacity;
            this.tournament_score = par1Minecraft.last_known_tournament_score;
        }

    }
    @Stealing
    public void b(String par1Str){}
    @Override
    @Stealing
    public INetworkManager getNetManager() {
        return null;
    }
}
