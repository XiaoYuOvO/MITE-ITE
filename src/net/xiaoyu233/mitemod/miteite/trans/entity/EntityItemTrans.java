package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityItem.class)
public class EntityItemTrans extends Entity {
    private boolean isExploded;

    @Marker
    public EntityItemTrans(World par1World) {
        super(par1World);
    }

    @Marker
    public EntityItem applyExplosionMotion(Explosion explosion) {
        return null;
    }

    @Override
    @Marker
    protected void a() {

    }

    @Override
    @Marker
    protected void a(NBTTagCompound var1) {

    }

    @Override
    @Marker
    protected void b(NBTTagCompound var1) {

    }

    @Marker
    public ItemStack d() {
        return null;
    }

    @Marker
    public float calcExplosionForce(float explosion_size, double distance_sq) {
        return (float)((double)explosion_size / Math.pow(distance_sq, 0.75D));
    }

    public boolean handleExplosion(Explosion explosion) {
        this.applyExplosionMotion(explosion);
        if (this.isImmuneToExplosion()) {
            return true;
        } else {
            ItemStack item_stack = this.d();
            if (item_stack == null) {
                return false;
            } else {
                Item item = item_stack.b();
                if (item == null) {
                    return false;
                } else {
                    double dx = this.u - explosion.c;
                    double dy = this.v - explosion.d;
                    double dz = this.w - explosion.e;
                    float explosion_force = this.calcExplosionForce(explosion.explosion_size_vs_blocks, World.getDistanceSqFromDeltas(dx, dy * 0.5D, dz));
                    if (!this.isExploded){
                        if (item.cv < 256) {
                            Block block = Block.getBlock(item.cv);
                            if (block == null) {
                                return false;
                            }

                            if (explosion_force * 2.0F < block.getExplosionResistance(explosion)) {
                                return true;
                            }

                            int metadata = item_stack.getItemSubtype();
                            int i;
                            if (!block.isValidMetadata(metadata)) {
                                for(i = 0; i < 16; ++i) {
                                    if (block.isValidMetadata(i)) {
                                        metadata = i;
                                        break;
                                    }
                                }
                            }

                            for(i = 0; i < item_stack.b; ++i) {
                                block.dropBlockAsEntityItem((new BlockBreakInfo(this.q, this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ())).setBlock(block, metadata).setExploded(explosion));
                            }
                        }
                        else {
                            if (item_stack.g()) {
                                this.attackEntityFrom(new Damage(DamageSource.a(explosion), explosion_force * 64.0F));
                                return true;
                            }

                            if (explosion_force < 0.2F) {
                                return true;
                            }

                            Block block = null;
                            if (item == Item.bc) {
                                block = Block.X;
                            } else if (item == Item.ax) {
                                block = Block.aJ;
                            }

                            if (block != null) {
                                block.dropBlockAsEntityItem((new BlockBreakInfo(this.q, MathHelper.c(this.u), MathHelper.c(this.v), MathHelper.c(this.w))).setBlock(Block.X, 0).setExploded(explosion));
                                this.x();
                                this.tryRemoveFromWorldUniques();
                                return true;
                            }

                            if (item == Item.ar) {
                                item = Item.chipFlint;
                            } else if (item == Item.bJ) {
                                item = Item.shardEmerald;
                            } else if (item == Item.p) {
                                item = Item.shardDiamond;
                            } else {
                                item = null;
                            }

                            if (item != null && !this.q.I) {
                                for(int i = 0; i < item_stack.b; ++i) {
                                    EntityItem entity_item = (new EntityItem(this.q, this.u, this.v, this.w, new ItemStack(item))).applyExplosionMotion(explosion);
                                    this.q.d(entity_item);
                                }
                            }
                        }
                        this.isExploded = true;
                    }

                    this.x();
                    this.tryRemoveFromWorldUniques();
                    return true;
                }
            }
        }
    }

    @Marker
    public void tryRemoveFromWorldUniques() {}
}
