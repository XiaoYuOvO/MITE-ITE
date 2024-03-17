package net.xiaoyu233.mitemod.miteite.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.network.CPacketStartForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketForgingTableInfo.EnhanceInfo.FaultFeedbackData;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiForgingTable extends GuiContainer implements ICrafting {
    private static final ResourceLocation FORGING_TABLE_TEXTURE = new ResourceLocation("textures/gui/container/forging_table.png");
    private final String axeCostInfo = StatCollector.translateToLocal("gui.forgingTable.axeCost");
    private final String chanceOfFailureInfo = StatCollector.translateToLocal("gui.forgingTable.chanceOfFailure");
    private final String hammerCostInfo = StatCollector.translateToLocal("gui.forgingTable.hammerCost");
    private final String ifFail = StatCollector.translateToLocal("gui.forgingTable.ifFail");
    private final EntityPlayer player;
    private static final int MAX_DISPLAY_TIME = 200;
    private final String timeInfo = StatCollector.translateToLocal("gui.forgingTable.time");
    private final ContainerForgingTable forgingTable;
    private GuiButton startButton;
    private String infoString;
    private int currentInfoTime;
    private int currentInfoColor;
    private int maxTime;
    private int chanceOfFailure;
    private final List<FaultFeedbackData> faultFeedbackData = new ArrayList<>();
    private int hammerCost;
    private int axeCost;

    public GuiForgingTable(EntityPlayer player, int x, int y, int z, ForgingTableSlots slots) {
        super(new ContainerForgingTable(slots, player, x, y, z));
        this.forgingTable = (ContainerForgingTable)super.inventorySlots;
        this.player = player;
    }

    public void initGui() {
        super.initGui();
        boolean isEnabled = true;
        if (this.startButton != null) {
            isEnabled = this.startButton.enabled;
        }

        this.buttonList.clear();
        this.buttonList.add(this.startButton = new GuiButton(0, this.width / 2 + 3, this.height / 2 - 32, 40, 20, StatCollector.translateToLocal("gui.forgingTable.start")));
        this.startButton.enabled = isEnabled;
    }

    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(FORGING_TABLE_TEXTURE);
        int var4 = (this.width - this.xSize) / 2;
        int var3 = (this.width + this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        int forgingTime = this.forgingTable.getForgingTime();
        if (forgingTime != 0 && this.maxTime != 0) {
            this.drawTexturedModalRect(this.width / 2 + 8, this.height / 2 - 48, 176, 0, (int)(30.0F * (float)forgingTime / (float)this.maxTime), 16);
        }

        if (this.infoString != null) {
            this.drawString(this.mc.fontRenderer, this.infoString, var4, var5 - 10, this.currentInfoColor);
        }

        if (this.maxTime != 0) {
            this.drawString(this.mc.fontRenderer, this.timeInfo + ":" + (float)this.maxTime / 20.0F + "S", var3, var5, 16777215);
            this.drawString(this.mc.fontRenderer, this.chanceOfFailureInfo + ":" + this.chanceOfFailure + "%", var3, var5 + 10, 16777215);
            this.drawString(this.mc.fontRenderer, this.hammerCostInfo + ":" + this.hammerCost, var3, var5 + 20, 16777215);
            this.drawString(this.mc.fontRenderer, this.axeCostInfo + ":" + this.axeCost, var3, var5 + 30, 16777215);
            this.drawString(this.mc.fontRenderer, this.ifFail, var3, var5 + 40, 16777045);
            List<FaultFeedbackData> feedbackData = this.faultFeedbackData;

            for(int i2 = 0; i2 < feedbackData.size(); ++i2) {
                FaultFeedbackData faultFeedbackData = feedbackData.get(i2);
                this.drawString(this.mc.fontRenderer, "   " + StatCollector.translateToLocalFormatted(faultFeedbackData.getName(), faultFeedbackData.getData()), var3, var5 + 50 + 10 * i2, 16733525);
            }
        }
    }

    public void sendProgressBarUpdate(Container container, int i, int i1) {
        this.player.sendPacket(new Packet105UpdateProgressbar(container.windowId, i, i1));
    }

    public void updateScreen() {
        super.updateScreen();
        if (this.currentInfoTime < MAX_DISPLAY_TIME) {
            ++this.currentInfoTime;
        } else {
            this.currentInfoTime = 0;
            this.infoString = null;
        }
    }

    public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3) {
        this.mc = par1Minecraft;
        this.fontRenderer = par1Minecraft.fontRenderer;
        this.width = par2;
        this.height = par3;
        this.initGui();
    }

    @Override
    public void sendSlotContents(Container container, int i, ItemStack itemStack) {

    }

    @Override
    public void sendContainerAndContentsToPlayer(Container container, List list) {
    }

    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton.id == 0) {
            this.player.sendPacket(new CPacketStartForging());
            this.startButton.enabled = false;
        }
    }

    public void enableButton() {
        this.startButton.enabled = true;
    }

    public void setInfo(String asString, int color) {
        this.infoString = asString;
        this.currentInfoColor = color;
        this.currentInfoTime = 0;
    }

    public void setEnhanceInfo(int chanceOfFailure, List<FaultFeedbackData> faultFeedbackData, int time, int hammerCost, int axeCost){
        this.chanceOfFailure = chanceOfFailure;
        this.maxTime = time;
        this.hammerCost = hammerCost;
        this.axeCost = axeCost;
        this.faultFeedbackData.clear();
        this.faultFeedbackData.addAll(faultFeedbackData);
    }
}
