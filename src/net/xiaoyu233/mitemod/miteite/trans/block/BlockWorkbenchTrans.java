package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockWorkbench.class)
public class BlockWorkbenchTrans extends BlockTrans {
   @Shadow
   @Mutable
   @Final
   private static Material[] tool_materials;
   @Shadow
   protected IIcon[] side_icons;
   @Shadow
   private IIcon displayOnCreativeTab;
   @Shadow
   private IIcon[] front_icons;
   @Shadow
   private IIcon icon_flint_top;
   @Shadow
   private IIcon icon_obsidian_top;

   @Inject(method = "<clinit>",at = @At("RETURN"))
   private static void injectClinit(CallbackInfo callback){
      tool_materials = new Material[]{Material.flint, Material.copper, Material.silver, Material.gold, Material.iron, Material.ancient_metal, Material.mithril, Material.adamantium, Materials.vibranium, Material.obsidian};
   }

   @Override
   @SoftOverride
   public String getItemDisplayName(ItemStack itemStack) {
      return Translator.get("tile.toolbench." + BlockWorkbench.getToolMaterial(itemStack.getItemSubtype()).getName() + ".name");
   }

   @Overwrite
   public IIcon a(int side, int metadata) {
      if (metadata < 4) {
         return side == 1 ? this.icon_flint_top : Block.wood.a(side, metadata);
      } else if (metadata > 11) {
         return side == 1 ? this.icon_obsidian_top : Block.wood.a(side, metadata - 11);
      } else if (side == 0) {
         return Block.planks.m(side);
      } else if (side == 1) {
         return this.displayOnCreativeTab;
      } else {
         return side != 2 && side != 3 ? this.side_icons[metadata] : this.front_icons[metadata];
      }
   }

   @Overwrite
   public void a(mt par1IconRegister) {
      this.icon_flint_top = par1IconRegister.a("crafting_table/flint/top");
      this.icon_obsidian_top = par1IconRegister.a("crafting_table/obsidian/top");
      this.displayOnCreativeTab = par1IconRegister.a("crafting_table_top");

      for(int i = 4; i < this.front_icons.length - 3; ++i) {
         this.front_icons[i] = par1IconRegister.a("crafting_table/" + BlockWorkbench.getToolMaterial(i).toString() + "/front");
         this.side_icons[i] = par1IconRegister.a("crafting_table/" + BlockWorkbench.getToolMaterial(i).toString() + "/side");
      }

   }
}
