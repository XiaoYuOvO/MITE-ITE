package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.AchievementList;
import net.minecraft.EntityHuman;
import net.minecraft.axp;
import net.minecraft.axv;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;

@Transform(axv.class)
public class GuiPlayerInventory extends axp {
    public GuiPlayerInventory(EntityHuman par1EntityPlayer) {
        super(par1EntityPlayer.bo);
        this.j = true;
        par1EntityPlayer.a(AchievementList.f, 1);
        par1EntityPlayer.incrementStatForThisWorldFromClient(AchievementList.f);
        par1EntityPlayer.sendPacket(new CPacketSyncItems());
    }

    @Override
    @Marker
    protected void a(float v, int i, int i1) {

    }
}
