package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Transform(EntityPlayer.class)
public class ServerPlayerTrans extends EntityHuman implements ICrafting{
    @Link
    private String bN ;
    @Link
    public PlayerConnection a;
    @Link
    public MinecraftServer b;
    @Link
    public PlayerInteractManager c;
    @Link
    public double d;
    @Link
    public double e;
    @Link
    public  List f;
    @Link
    public  List g ;
    @Link
    private float bO ;
    @Link
    private float bP ;
    @Link
    private int last_satiation ;
    @Link
    private int last_nutrition ;
    @Link
    private int protein;
    @Link
    private int phytonutrients;
    @Link
    private int last_experience ;
    @Link
    private int bT;
    @Link
    public boolean h;
    @Link
    public int i;
    @Link
    public boolean j;
    private int last_phytonutrients,last_protein;
    @Marker
    public ServerPlayerTrans(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    @Marker
    public void a(ChatMessage chatMessage) {

    }

    @Override
    @Marker
    public boolean a(int i, String s) {
        return false;
    }

    @Override
    @Marker
    public void a(Container container, List list) {

    }

    @Marker
    @Override
    public void a(Container container, int i, ItemStack itemStack) {

    }

    @Override
    @Marker
    public void a(Container container, int i, int i1) {

    }

    @Override
    @Marker
    public ChunkCoordinates b() {
        return null;
    }

    @Override
    @Marker
    public INetworkManager getNetManager() {
        return null;
    }

    public void h() {
        try {
            super.l_();

            for(int var1 = 0; var1 < this.bn.j_(); ++var1) {
                ItemStack var6 = this.bn.a(var1);
                if (var6 != null && Item.g[var6.d].f() && this.a.f() <= 5) {
                    Packet var8 = ((ItemWorldMapBase)Item.g[var6.d]).c(var6, this.q, this);
                    if (var8 != null) {
                        this.a.b(var8);
                    }
                }
            }

            float health = this.aN();
            int satiation = this.getSatiation();
            int nutrition = this.getNutrition();
            float hunger = this.bq.getHunger();
            if (health != this.bP || satiation != this.last_satiation || nutrition != this.last_nutrition || this.vision_dimming > 0.0F || this.last_protein != this.protein || this.last_phytonutrients != this.phytonutrients) {
                this.a.b(new Packet8UpdateHealth(health, satiation, nutrition,this.protein,this.phytonutrients, this.vision_dimming));
                this.bP = health;
                this.last_satiation = satiation;
                this.last_nutrition = nutrition;
                this.last_phytonutrients = phytonutrients;
                this.last_protein = protein;
                this.vision_dimming = 0.0F;
            }

            if (this.aN() + this.bn() != this.bO) {
                this.bO = this.aN() + this.bn();
                Collection var5 = this.bM().a(IScoreboardCriteria.f);
                Iterator var7 = var5.iterator();

                while(var7.hasNext()) {
                    ScoreboardObjective var9 = (ScoreboardObjective)var7.next();
                    this.bM().a(this.an(), var9).a(Arrays.asList(this));
                }
            }

            if (this.bJ != this.last_experience) {
                this.last_experience = this.bJ;
                this.a.b(new Packet43SetExperience(this.bJ));
            }

        } catch (Throwable var8) {
            CrashReport var2 = CrashReport.a(var8, "Ticking player");
            CrashReportSystemDetails var3 = var2.a("Player being ticked");
            this.a(var3);
            throw new ReportedException(var2);
        }
    }
}
