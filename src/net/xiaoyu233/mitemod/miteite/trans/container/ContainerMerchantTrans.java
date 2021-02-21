package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.ArrayList;

@Transform(ContainerMerchant.class)
public class ContainerMerchantTrans extends Container {
    @Link
    private IMerchant a;
    @Link
    private InventoryMerchant f;
    @Marker
    public ContainerMerchantTrans(EntityHuman player) {
        super(player);
    }

    @Override
    public void b(EntityHuman par1EntityPlayer) {
        super.b(par1EntityPlayer);
        this.a.a_((EntityHuman)null);
        super.b(par1EntityPlayer);
        if (!this.world.I) {
            ItemStack var2 = this.f.a_(0);
            if (var2 != null) {
                par1EntityPlayer.b(var2);
            }

            var2 = this.f.a_(1);
            if (var2 != null) {
                par1EntityPlayer.b(var2);
            }
            this.updatePlayerInventory(par1EntityPlayer);
        }

    }

    public ItemStack b(EntityHuman par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.c.get(par2);
        if (var4 != null && var4.e()) {
            ItemStack var5 = var4.d();
            var3 = var5.m();
            if (par2 == 2) {
                if (!this.a(var5, 3, 39, true)) {
                    return null;
                }

                var4.a(var5, var3);
            } else if (par2 != 0 && par2 != 1) {
                if (par2 >= 3 && par2 < 30) {
                    if (!this.a(var5, 30, 39, false)) {
                        return null;
                    }
                } else if (par2 >= 30 && par2 < 39 && !this.a(var5, 3, 30, false)) {
                    return null;
                }
            } else if (!this.a(var5, 3, 39, false)) {
                return null;
            }

            if (var5.b == 0) {
                var4.c(null);
            } else {
                var4.f();
            }

            if (var5.b == var3.b) {
                return null;
            }

            var4.a(par1EntityPlayer, var5);
        }

        if (!this.player.q.I){
            this.updatePlayerInventory(this.player);
            ((EntityPlayer) this.player).a(this, this.a());
        }

        return var3;
    }

    private void updatePlayerInventory(EntityHuman player){
        ArrayList<ItemStack> itemList = new ArrayList<>();

        for(int index = 0; index < player.bp.c.size(); ++index) {
            itemList.add(((Slot)player.bp.c.get(index)).d());
        }
        ((EntityPlayer) player).a(player.bp, itemList);
    }

    @Override
    @Marker
    public boolean a(EntityHuman var1) {
        return false;
    }
}
