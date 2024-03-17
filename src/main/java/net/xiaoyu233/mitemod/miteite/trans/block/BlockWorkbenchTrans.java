package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEMaterial;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockWorkbench.class)
public class BlockWorkbenchTrans extends BlockTrans {
   @Shadow
   @Mutable
   @Final
   public static Material[] tool_materials;

   @Inject(
           method = {"<clinit>()V"},
           at = {@At("RETURN")}
   )
   private static void injectClinit(CallbackInfo callback) {
      Class<Material> materialClass = Material.class;
      tool_materials = new Material[]{
              Material.flint,
              Material.copper,
              Material.silver,
              Material.gold,
              Material.iron,
              Material.ancient_metal,
              Material.mithril,
              Material.adamantium,
              Materials.vibranium,
              Material.obsidian
      };
   }

   @SoftOverride
   @Override
   public String getItemDisplayName(ItemStack itemStack) {
      return Translator.get("tile.toolbench." + BlockWorkbench.getToolMaterial(itemStack.getItemSubtype()).getName() + ".name");
   }

   @ModifyConstant(method = "getIcon", constant = @Constant(intValue = 10))
   private int modifyGetIconIndex(int original){
      return 11;
   }

   @ModifyConstant(method = "registerIcons", constant = @Constant(intValue = 4, ordinal = 1))
   private int injectModifyCraftingTableIconRegister(int original){
      return 3;
   }
}
