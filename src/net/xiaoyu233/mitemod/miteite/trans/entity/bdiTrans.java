package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(bdi.class)
public class bdiTrans extends bex {
    private int protein;
    private int phytonutrients;
    @Link
    private final bcw a;
    @Link
    private int delta_tournament_score;
    @Link
    private int delta_tournament_score_opacity;
    @Link
    private int tournament_score;

    public bdiTrans(Minecraft par1Minecraft, World par2World, PlayerNameSession par3Session, bcw par4NetClientHandler) {
        super(par1Minecraft, par2World, par3Session, 0);
        this.a = par4NetClientHandler;
        if (DedicatedServer.tournament_type == EnumTournamentType.score) {
            this.delta_tournament_score = par1Minecraft.last_known_delta_tournament_score;
            this.delta_tournament_score_opacity = par1Minecraft.last_known_delta_tournament_score_opacity;
            this.tournament_score = par1Minecraft.last_known_tournament_score;
        }

    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setPhytonutrients(int phytonutrients) {
        this.phytonutrients = phytonutrients;
    }

    public int getProtein() {
        return protein;
    }

    public int getPhytonutrients() {
        return phytonutrients;
    }

    @Marker
    public void b(String par1Str){}
    @Override
    @Marker
    public INetworkManager getNetManager() {
        return null;
    }
}
