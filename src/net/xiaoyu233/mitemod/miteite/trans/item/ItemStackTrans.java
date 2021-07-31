package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackTrans {
   @Shadow
   public int animationsToGo;
   @Shadow
   public int itemID;
   @Shadow
   public int stackSize;
   @Shadow
   public NBTTagCompound stackTagCompound;
   @Shadow
   private int damage;
   @Shadow
   private boolean is_artifact;
   @Shadow
   private EnumQuality quality;
   @Shadow
   private int subtype;
   private boolean toolNbtFixed;

   public ItemStackTrans(int id, int stack_size, int subtype) {
      this.itemID = id;
      this.stackSize = stack_size;
      this.setItemSubtype(subtype);
   }

   @Redirect(method = "getTooltip",at = @At(value = "INVOKE",target = "Lnet/minecraft/Translator;addToList(Lnet/minecraft/EnumChatFormat;Ljava/lang/String;Ljava/util/List;)V",ordinal = 0))
   private void removeChangeQualityInfo(EnumChatFormat enum_chat_formatting, String key, List list){
      //Do nothing to remove
      list.remove(list.size() - 1);
   }

   @Overwrite
   public ItemStack copy() {
      ItemStack var1 = new ItemStack(this.itemID, this.stackSize, this.subtype);
      var1.setItemDamage(this.damage);
      var1.setQuality(this.getQuality());
      var1.setIsArtifact(this.is_artifact);
      if (this.stackTagCompound != null) {
         var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      if (this.getItem().hasExpAndLevel()) {
         var1.fixNBT();
      }

      return ReflectHelper.dyCast(var1);
   }

   public void fixNBT() {
      if (!this.toolNbtFixed) {
         this.toolNbtFixed = true;
         if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
            this.stackTagCompound.setInteger("tool_level", 0);
            this.stackTagCompound.setInteger("tool_exp", 0);
            this.stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         } else if (!this.stackTagCompound.hasKey("tool_level")) {
            this.stackTagCompound.setInteger("tool_level", 0);
            this.stackTagCompound.setInteger("tool_exp", 0);
            this.stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         }

         if (this.stackTagCompound.hasKey("modifiers")) {
            NBTTagCompound compound = this.stackTagCompound.getCompoundTag("modifiers");
            int var3;
            int var4;
            float origin;
            if (this.getItem() instanceof ItemArmor) {
               if (!compound.hasNoTags()) {
                  ArmorModifierTypes[] var2 = ArmorModifierTypes.values();
                  var3 = var2.length;

                  for(var4 = 0; var4 < var3; ++var4) {
                     ArmorModifierTypes value = var2[var4];
                     if (compound.getTag(value.nbtName) instanceof NBTTagFloat) {
                        origin = compound.getFloat(value.nbtName);
                        compound.setInteger(value.nbtName, (int)(origin / value.levelAddition));
                     }
                  }
               }
            } else if (!compound.hasNoTags()) {
               ToolModifierTypes[] var7 = ToolModifierTypes.values();
               var3 = var7.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  ToolModifierTypes value = var7[var4];
                  if (compound.getTag(value.nbtName) instanceof NBTTagFloat) {
                     origin = compound.getFloat(value.nbtName);
                     compound.setInteger(value.nbtName, (int)(origin / value.levelAddition));
                  }
               }
            }
         }
      }

   }

   @Overwrite
   public Multimap<String, AttributeModifier> getAttributeModifiers() {
      Multimap<String, AttributeModifier> var1 = HashMultimap.create();
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers")) {
         NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers");

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            AttributeModifier var5 = GenericAttributes.func_111259_a(var4);
            if (var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L) {
               var1.put(var4.getString("AttributeName"), var5);
            }
         }
      } else {
         var1.putAll(this.getItem().getAttrModifiers(ReflectHelper.dyCast(this)));
      }

      return var1;
   }

   public double getEnhanceFactor() {
      return Constant.ENHANCE_FACTORS[this.getForgingGrade()];
   }

   public int getForgingGrade() {
      return this.stackTagCompound != null ? this.stackTagCompound.getInteger("forging_grade") : 0;
   }

   public int getEmergencyCooldown(){
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("emergencyCooldown") ? this.stackTagCompound.getInteger("emergencyCooldown") : 0;
   }

   public void setEmergencyCooldown(int cooldown){
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("emergencyCooldown", cooldown);
   }

   public void setForgingGrade(int grade) {
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("forging_grade", grade);
   }

   @Shadow
   private boolean getHasSubtypes() {
      return false;
   }

   @Shadow
   @Nonnull
   private Item getItem() {
      return null;
   }

   @Shadow
   private int getMaxDamage() {
      return 0;
   }

   @Overwrite
   public float getMeleeDamageBonus() {
      return this.getItem().getMeleeDamageBonus(ReflectHelper.dyCast(this));
   }

   @Shadow
   @Nonnull
   private EnumQuality getQuality() {
      return null;
   }

   @Overwrite
   public float getStrVsBlock(Block block, int metadata) {
      return this.getItem().getStrVsBlock(block, metadata);
   }

   @Shadow
   private boolean hasTagCompound() {
      return false;
   }

   @Inject(method = "<init>(III)V",at = @At("RETURN"))
   private void injectCtorFix(CallbackInfo callback){
      if (this.getItem() != null){
         if (this.getItem().hasExpAndLevel()) {
            this.fixNBT();
         }
      }
   }

   @Inject(method = "setTagCompound",at = @At(value = "FIELD",target = "Lnet/minecraft/ItemStack;stackTagCompound:Lnet/minecraft/NBTTagCompound;",shift = At.Shift.AFTER))
   private void injectSetTagFix(CallbackInfoReturnable<ItemStack> callback){
      if (this.getItem().hasExpAndLevel()) {
         this.fixNBT();
      }
   }

   @Shadow
   private boolean isItemStackDamageable() {
      return false;
   }

   @Overwrite
   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.itemID = par1NBTTagCompound.getShort("id");
      this.stackSize = par1NBTTagCompound.getByte("Count");
      if (this.itemID <= 0) {
         (new Exception()).printStackTrace();
      }

      Item item;
      if (par1NBTTagCompound.hasKey("subtype")) {
         this.setItemSubtype(par1NBTTagCompound.getShort("subtype"));
         this.setItemDamage(par1NBTTagCompound.getInteger("damage"));
      } else {
         if (Minecraft.inDevMode()) {
            System.out.println("Importing item stack " + this.getItem() + ", id=" + this.itemID);
         }

         if (this.getItem().hasExpAndLevel() && this.stackTagCompound == null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("tool_level", 0);
            tagCompound.setInteger("tool_exp", 0);
            tagCompound.setCompoundTag("modifiers", new NBTTagCompound());
            tagCompound.setInteger("forging_grade", 0);
            tagCompound.setInteger("emergencyCooldown",0);
            this.setTagCompound(tagCompound);
         }

         if (this.isItemStackDamageable() && this.getHasSubtypes()) {
            item = this.getItem();
            if (item instanceof ItemAnvil) {
               this.setItemSubtype(par1NBTTagCompound.getShort("Damage"));
            } else {
               Minecraft.setErrorMessage("Unhandled item import, setting damage for: " + this);
               this.setItemDamage(par1NBTTagCompound.getShort("Damage"));
            }
         } else if (this.isItemStackDamageable()) {
            this.setItemDamage(par1NBTTagCompound.getShort("Damage"));
         } else {
            this.setItemSubtype(par1NBTTagCompound.getShort("Damage"));
         }
      }

      if (par1NBTTagCompound.hasKey("tag")) {
         this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
         if (ItemReferencedBook.isReferencedBook(ReflectHelper.dyCast(this))) {
            this.setTagCompound(ItemReferencedBook.generateBookContents(ItemReferencedBook.getReferenceIndex(ReflectHelper.dyCast(this))));
         }
         if (this.getItem().hasExpAndLevel()) {
            this.fixNBT();
         }
      }

      item = this.getItem();
      if (item == null) {
         this.quality = null;
      } else {
         if (par1NBTTagCompound.hasKey("quality")) {
            this.setQuality(EnumQuality.values()[par1NBTTagCompound.getByte("quality")]);
         } else {
            this.setQuality(null);
         }

         if (this.isItemStackDamageable() && this.damage >= this.getMaxDamage()) {
            this.setItemDamage(this.getMaxDamage() - 1);
         }
      }

      this.is_artifact = par1NBTTagCompound.getBoolean("is_artifact");
   }

   public void setIsArtifact(boolean is_artifact) {
      this.is_artifact = is_artifact;
   }

   @Shadow
   private ItemStack setItemDamage(int damage) {
      return null;
   }

   @Shadow
   private ItemStack setItemSubtype(int subtype) {
      return null;
   }

   @Shadow
   public ItemStack setQuality(EnumQuality quality) {
      return null;
   }

   @Shadow
   public ItemStack setTagCompound(NBTTagCompound par1NBTTagCompound) {
      return null;
   }

   @Overwrite
   public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("id", (short)this.itemID);
      par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
      par1NBTTagCompound.setInteger("damage", this.damage);
      par1NBTTagCompound.setShort("subtype", (short)this.subtype);
      NBTTagCompound effective_stackTagCompound;
      if (this.stackTagCompound != null) {
         effective_stackTagCompound = this.stackTagCompound;
         if (ItemReferencedBook.isReferencedBook(ReflectHelper.dyCast(this))) {
            effective_stackTagCompound = new NBTTagCompound();
            effective_stackTagCompound.setInteger("reference_index", ItemReferencedBook.getReferenceIndex(ReflectHelper.dyCast(this)));
         }

         if (!effective_stackTagCompound.hasKey("forging_grade")) {
            effective_stackTagCompound.setInteger("forging_grade", 0);
         }

         par1NBTTagCompound.setCompoundTag("tag", effective_stackTagCompound);
      } else if (this.getItem().hasExpAndLevel()) {
         effective_stackTagCompound = new NBTTagCompound();
         effective_stackTagCompound.setInteger("tool_level", 0);
         effective_stackTagCompound.setInteger("tool_exp", 0);
         effective_stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         effective_stackTagCompound.setInteger("forging_grade", 0);
         par1NBTTagCompound.setCompoundTag("tag", effective_stackTagCompound);
      }

      if (this.getItem().hasQuality()) {
         par1NBTTagCompound.setByte("quality", (byte)this.getQuality().ordinal());
      }

      if (this.is_artifact) {
         par1NBTTagCompound.setBoolean("is_artifact", this.is_artifact);
      }

      return par1NBTTagCompound;
   }
}
