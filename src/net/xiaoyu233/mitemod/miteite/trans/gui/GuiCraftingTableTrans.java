package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(axk.class)
public class GuiCraftingTableTrans extends axp {
    @Link
    private static final bjo t = new bjo("textures/gui/container/crafting_table.png");
    private static final String BOOST_INFO = LocaleI18n.a("container.workbench.boost");

    @Marker
    public GuiCraftingTableTrans(Container par1Container) {
        super(par1Container);
    }

    protected void b(int par1, int par2) {
        ContainerWorkbench container_workbench = (ContainerWorkbench)this.e;
        String var3 = Translator.get("tile.toolbench." + BlockWorkbench.getToolMaterial(container_workbench.getBlockMetadata()) + ".name");
        this.o.b(var3, 29, 6, 4210752);
        this.o.b(bkb.a("container.inventory"), 7, this.d - 96 + 3, 4210752);
        if (this.e.player.getCraftingBoostFactor() != 0){
            this.o.b(BOOST_INFO  + ((int) (this.e.player.getCraftingBoostFactor() * 100)) + "%", 90,6, 0xaa00aa);
        }
    }

    @Override
    @Marker
    protected void a(float v, int i, int i1) {

    }

}
