package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Random;

@Mixin(EntityVillager.class)
public abstract class EntityVillagerTrans extends EntityAgeable implements IMerchant, INpc {
   @Shadow
   private MerchantRecipeList buyingList;
   @Shadow
   private float field_82191_bN;

   public EntityVillagerTrans(World par1World) {
      super(par1World);
   }

   @Unique
   private static void forEmeraldShard(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {
      if (par2Random.nextFloat() < par3) {
         par0MerchantRecipeList.add(new MerchantRecipe(new ItemStack(par1, Configs.Entities.VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT.get()), new ItemStack(Item.shardEmerald, Configs.Entities.VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT.get())));
      }

   }

   @Redirect(method = "addDefaultEquipmentAndRecipies",
           at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityVillager;addMerchantItem(Lnet/minecraft/MerchantRecipeList;ILjava/util/Random;F)V"),
           slice = @Slice(
                   from = @At(value = "FIELD", target = "Lnet/minecraft/Block;cloth:Lnet/minecraft/Block;"),
                   to = @At(value = "FIELD", target = "Lnet/minecraft/Item;chickenRaw:Lnet/minecraft/ItemMeat;")
           )
   )
   private void redirectMakeWoolToEmeraldShard(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {
      forEmeraldShard(par0MerchantRecipeList,par1,par2Random,par3);
   }

}
