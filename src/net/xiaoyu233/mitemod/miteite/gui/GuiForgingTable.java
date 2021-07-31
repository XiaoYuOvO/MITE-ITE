package net.xiaoyu233.mitemod.miteite.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketForgingTableInfo;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiForgingTable extends awy implements ICrafting {
    private static final bjo FORGING_TABLE_TEXTURE = new bjo("textures/gui/container/forging_table.png");
    private final String axeCostInfo = LocaleI18n.translateToLocal("gui.forgingTable.axeCost");
    private final String chanceOfFailureInfo = LocaleI18n.translateToLocal("gui.forgingTable.chanceOfFailure");
    private final String hammerCostInfo = LocaleI18n.translateToLocal("gui.forgingTable.hammerCost");
    private final String ifFail = LocaleI18n.translateToLocal("gui.forgingTable.ifFail");
    private final EntityPlayer player;
    private static final int MAX_DISPLAY_TIME = 200;
    private final int x;
    private final int y;
    private final int z;
    private final PlayerInventory inventory;
    private final String timeInfo = LocaleI18n.translateToLocal("gui.forgingTable.time");
    private final ContainerForgingTable forgingTable;
    private aut startButton;
    private String infoString;
    private int currentInfoTime;
    private int currentInfoColor;
    private int maxTime,chanceOfFailure;
    private final List<SPacketForgingTableInfo.EnhanceInfo.FaultFeedbackData> faultFeedbackData = new ArrayList<>();
    private int hammerCost,axeCost;

    public GuiForgingTable(EntityPlayer player, int x, int y, int z,ForgingTableSlots slots) {
        super(new ContainerForgingTable(slots,player,x, y, z));
        this.forgingTable = (ContainerForgingTable) super.e;
        this.x = x;
        this.y = y;
        this.z = z;
        this.inventory = player.inventory;
        this.player = player;
    }

    @Override
    //onWindowResize
    public void A_() {
        super.A_();
        boolean isEnabled = true;
        if (this.startButton != null){
            isEnabled = this.startButton.h;
        }
        this.i.clear();
        this.i.add(this.startButton = new aut(0,this.g/2+3, this.h/2-32,40,20,LocaleI18n.translateToLocal("gui.forgingTable.start")));
        this.startButton.h = isEnabled;
    }

    @Override
    protected void a(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.e();
        this.f.J().a(FORGING_TABLE_TEXTURE);
        int var4 = (this.g - this.c) / 2;
        int var3 = (this.g + this.c) / 2;
        int var5 = (this.h - this.d) / 2;
        this.b(var4, var5, 0, 0, this.c, this.d);
        int forgingTime = forgingTable.getForgingTime();
        if (forgingTime != 0 && this.maxTime != 0) {
            this.b(this.g/2+8, this.h/2-48,176,0, (int) (30 * (float)forgingTime / (float)this.maxTime),16);
        }
        if (this.infoString != null){
            this.b(this.f.l,infoString,var4,var5-10,currentInfoColor);
        }
        if (this.maxTime != 0){
            this.b(this.f.l,this.timeInfo + ":" + this.maxTime/20f + "S",var3,var5,0xffffff);
            this.b(this.f.l,this.chanceOfFailureInfo + ":" + this.chanceOfFailure + "%",var3,var5+10,0xffffff);
            this.b(this.f.l,this.hammerCostInfo + ":" + this.hammerCost,var3,var5+20,0xffffff);
            this.b(this.f.l,this.axeCostInfo + ":" + this.axeCost,var3,var5+30,0xffffff);
            this.b(this.f.l,this.ifFail,var3,var5+40,0xFFFF55);
            List<SPacketForgingTableInfo.EnhanceInfo.FaultFeedbackData> feedbackData = this.faultFeedbackData;
            for (int i2 = 0; i2 < feedbackData.size(); i2++) {
                SPacketForgingTableInfo.EnhanceInfo.FaultFeedbackData faultFeedbackData = feedbackData.get(i2);
                this.b(this.f.l, "   " + LocaleI18n.translateToLocalFormatted(faultFeedbackData.getName(), faultFeedbackData.getData()), var3, var5 + 50 + 10 * i2, 0xFF5555);
            }
        }
    }

    @Override
    public void sendProgressBarUpdate(Container container, int i, int i1) {
        this.player.sendPacket(new Packet105CraftProgressBar(container.windowId, i, i1));
    }

    @Override
    //tick
    public void c() {
        super.c();
        if (this.currentInfoTime < MAX_DISPLAY_TIME){
            this.currentInfoTime++;
        }else {
            this.currentInfoTime = 0;
            this.infoString = null;
        }
    }


    @Override
    public void a(Minecraft par1Minecraft, int par2, int par3) {
        this.f = par1Minecraft;
        this.o = par1Minecraft.l;
        this.g = par2;
        this.h = par3;
        this.A_();
    }

    @Override
    public void sendSlotContents(Container container, int i, ItemStack itemStack) {

    }

    @Override
    public void updateCraftingInventory(Container container, List list) {

    }

    @Override
    public void a(int par1, int par2, float par3) {
        super.a(par1, par2, par3);
    }

    //OnButtonClicked
    @Override
    protected void a(aut par1GuiButton) {
        super.a(par1GuiButton);
        if (par1GuiButton.g == 0) {
            player.sendPacket(new CPacketStartForging());
            //enable = false
            this.startButton.h = false;
        }
    }

    public void enableButton(){
        this.startButton.h = true;
    }

    public void setInfo(String asString,int color) {
        this.infoString = asString;
        this.currentInfoColor = color;
        this.currentInfoTime = 0;
    }

    public void setEnhanceInfo(int chanceOfFailure, List<SPacketForgingTableInfo.EnhanceInfo.FaultFeedbackData> faultFeedbackData, int time, int hammerCost, int axeCost){
        this.chanceOfFailure = chanceOfFailure;
        this.maxTime = time;
        this.hammerCost = hammerCost;
        this.axeCost = axeCost;
        this.faultFeedbackData.clear();
        this.faultFeedbackData.addAll(faultFeedbackData);
    }
}
