package net.xiaoyu233.mitemod.miteite.tileentity;

import net.minecraft.NBTTagCompound;
import net.minecraft.TileEntity;

public class TileEntityForgingEnchantmentTable  extends TileEntity {
    private String customName;


    //getCustomName(NBTTagCompound)
    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        if (this.b()) {
            par1NBTTagCompound.a("CustomName", this.customName);
        }
    }

    public boolean b() {
        return this.customName != null && this.customName.length() > 0;
    }
}
