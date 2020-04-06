package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;

import javax.annotation.Nonnull;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Transform(ItemStack.class)
public class ItemStackTrans {
    @Link
    public int b;
    @Link
    public int c;
    @Link
    public int d;
    @Link
    public NBTTagCompound e;
    @Link
    private int damage;
    @Link
    private boolean is_artifact;
    @Link
    private EnumQuality quality;
    @Link
    private int subtype;
    @Link
    private boolean nbtFixed;


    public ItemStackTrans(int id, int stack_size, int subtype) {
        this.d = id;
        this.b = stack_size;
        this.setItemSubtype(subtype);
        if (this.b().hasExpAndLevel()) {
            this.fixNBT();
        }
    }

    public void fixNBT(){
        if (!this.nbtFixed) {
            this.nbtFixed = true;
            if (this.e == null) {
                this.setTagCompound(new NBTTagCompound());
                this.e.a("tool_level", 0);
                this.e.a("tool_exp", 0);
                this.e.a("modifiers", new NBTTagCompound());
            } else if (!this.e.b("tool_level")) {
                this.e.a("tool_level", 0);
                this.e.a("tool_exp", 0);
                this.e.a("modifiers", new NBTTagCompound());
            }
            if (this.e.b("modifiers")) {
                NBTTagCompound compound = e.l("modifiers");
                if (this.b() instanceof ItemArmor) {
                    if (!compound.d()) {
                        for (ArmorModifierTypes value : ArmorModifierTypes.values()) {
                            if (compound.a(value.nbtName) instanceof NBTTagFloat) {
                                float origin = compound.g(value.nbtName);
                                compound.a(value.nbtName, ((int) (origin / value.levelAddition)));
                            }
                        }
                    }
                } else {
                    if (!compound.d()) {
                        for (ToolModifierTypes value : ToolModifierTypes.values()) {
                            if (compound.a(value.nbtName) instanceof NBTTagFloat) {
                                float origin = compound.g(value.nbtName);
                                compound.a(value.nbtName, ((int) (origin / value.levelAddition)));
                            }
                        }
                    }
                }
            }
        }
    }

    public ItemStack m() {
        ItemStack var1 = (new ItemStack(this.d, this.b, this.subtype));
        var1.setItemDamage(this.damage);
        var1.setQuality(this.getQuality());
        var1.setIsArtifact( this.is_artifact);
        if (this.e != null) {
            var1.e = (NBTTagCompound)this.e.b();
        }
        if (this.b().hasExpAndLevel()) {
            var1.fixNBT();
        }

        return dyCast( var1);
    }

    public void setIsArtifact(boolean is_artifact) {
        this.is_artifact = is_artifact;
    }

    public Multimap<String, AttributeModifier> D() {
        Multimap<String, AttributeModifier> var1;
        if (this.p() && this.e.b("AttributeModifiers")) {
            var1 = HashMultimap.create();
            NBTTagList var2 = this.e.m("AttributeModifiers");

            for (int var3 = 0; var3 < var2.c(); ++var3) {
                NBTTagCompound var4 = (NBTTagCompound) var2.b(var3);
                AttributeModifier var5 = GenericAttributes.a(var4);
                if (var5.a().getLeastSignificantBits() != 0L && var5.a().getMostSignificantBits() != 0L) {
                    var1.put(var4.i("AttributeName"), var5);
                }
            }
        } else {
            var1 = this.b().getAttrModifiers(dyCast(this));
        }

        return var1;
    }

    public NBTTagCompound b(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.a("id", (short) this.d);
        par1NBTTagCompound.a("Count", (byte) this.b);
        par1NBTTagCompound.a("damage", this.damage);
        par1NBTTagCompound.a("subtype", (short) this.subtype);
        if (this.e != null) {
            NBTTagCompound effective_stackTagCompound = this.e;
            if (ItemReferencedBook.isReferencedBook(dyCast(this))) {
                effective_stackTagCompound = new NBTTagCompound();
                effective_stackTagCompound.a("reference_index", ItemReferencedBook.getReferenceIndex(dyCast(this)));
            }
            par1NBTTagCompound.a("tag", effective_stackTagCompound);
        } else
            if (this.b().hasExpAndLevel()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.a("tool_level", 0);
            compound.a("tool_exp", 0);
            compound.a("modifiers", new NBTTagCompound());
            par1NBTTagCompound.a("tag", compound);
        }

        if (this.b().hasQuality()) {
            par1NBTTagCompound.a("quality", (byte) this.getQuality().ordinal());
        }


        if (this.is_artifact) {
            par1NBTTagCompound.a("is_artifact", this.is_artifact);
        }

        return par1NBTTagCompound;
    }

    @Marker
    @Nonnull
    private Item b() {
        return null;
    }

    public void c(NBTTagCompound par1NBTTagCompound) {
        this.d = par1NBTTagCompound.d("id");
        this.b = par1NBTTagCompound.c("Count");
        if (this.d <= 0) {
            (new Exception()).printStackTrace();
        }

        Item item;
        if (par1NBTTagCompound.b("subtype")) {
            this.setItemSubtype(par1NBTTagCompound.d("subtype"));
            this.setItemDamage(par1NBTTagCompound.e("damage"));
        } else {
            if (Minecraft.inDevMode()) {
                System.out.println("Importing item stack " + this.b() + ", id=" + this.d);
            }

            item = this.b();
            if (this.b().hasExpAndLevel()) {
                if (this.e == null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.a("tool_level", 0);
                    tagCompound.a("tool_exp", 0);
                    tagCompound.a("modifiers",new NBTTagCompound());
                    this.setTagCompound(tagCompound);
                }
            }

            if (this.g() && this.h()) {
                item = this.b();
                if (item instanceof ItemAnvil) {
                    this.setItemSubtype(par1NBTTagCompound.d("Damage"));
                } else {
                    Minecraft.setErrorMessage("Unhandled item import, setting damage for: " + this);
                    this.setItemDamage(par1NBTTagCompound.d("Damage"));
                }
            } else if (this.g()) {
                this.setItemDamage(par1NBTTagCompound.d("Damage"));
            } else {
                this.setItemSubtype(par1NBTTagCompound.d("Damage"));
            }
        }

        if (par1NBTTagCompound.b("tag")) {
            this.e = par1NBTTagCompound.l("tag");
            if (ItemReferencedBook.isReferencedBook(dyCast(this))) {
                this.setTagCompound(
                        ItemReferencedBook.generateBookContents(ItemReferencedBook.getReferenceIndex(dyCast(this))));
            }
            if (this.b().hasExpAndLevel()) {
                this.fixNBT();
            }
        }

        item = this.b();
        if (item == null) {
            this.quality = null;
        } else {
            if (par1NBTTagCompound.b("quality")) {
                this.setQuality(EnumQuality.values()[par1NBTTagCompound.c("quality")]);
            } else {
                this.setQuality(null);
            }

            if (this.g() && this.damage >= this.l()) {
                this.setItemDamage(this.l() - 1);
            }
        }

        this.is_artifact = par1NBTTagCompound.n("is_artifact");
    }

    @Marker
    private boolean g() {
        return false;
    }

    public float getMeleeDamageBonus() {
        return this.b().getMeleeDamageBonus(dyCast(this));
    }

    @Marker
    @Nonnull
    private EnumQuality getQuality() {
        return null;
    }

    public float getStrVsBlock(Block block, int metadata) {
        return this.b().getStrVsBlock(block, metadata);
    }

    @Marker
    private boolean h() {
        return false;
    }

    @Marker
    private int l() {
        return 0;
    }

    @Marker
    private boolean p() {
        return false;
    }

    @Marker
    private ItemStack setItemDamage(int damage) {
        return null;
    }

    @Marker
    private ItemStack setItemSubtype(int subtype) {
        return null;
    }

    @Marker
    public ItemStack setQuality(EnumQuality quality) {
        return null;
    }


    public ItemStack setTagCompound(NBTTagCompound par1NBTTagCompound) {
        this.e = par1NBTTagCompound;
        if (this.b().hasExpAndLevel()) {
            this.fixNBT();
        }
        return dyCast(this);
    }
}
