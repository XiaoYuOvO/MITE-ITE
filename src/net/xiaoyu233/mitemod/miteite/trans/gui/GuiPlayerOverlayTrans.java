package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@Transform(GuiIngame.class)
public class GuiPlayerOverlayTrans extends avk{

    @Link
    private Random f;
    @Link
    private Minecraft g;
    @Link
    private int i;

    private void a(int par1, int par2) {
        boolean var3 = this.g.h.af / 3 % 2 == 1;
        if (this.g.h.af < 10) {
            var3 = false;
        }

        int var4 = MathHelper.f(this.g.h.aN());
        int var5 = MathHelper.f(this.g.h.ax);
        this.f.setSeed(this.i * 312871);
        FoodMetaData foodMetaData = this.g.h.bI();
        int foodLevel = foodMetaData.getNutrition();
//        AttributeInstance var10 = this.g.h.a(GenericAttributes.a);
        int var11 = par1 / 2 - 91;
        int var12 = par1 / 2 + 91;
        int guiUp = par2 - 39;
        float healthLimit = this.g.h.getHealthLimit();
        float currentHealth = this.g.h.bn();
        int var16 = MathHelper.f((healthLimit + currentHealth) / 2.0F / 10.0F);
        int var17 = Math.max(10 - (var16 - 2), 3);
        int var18 = guiUp - (var16 - 1) * var17 - 10;
        float currentHealthClone = currentHealth;
        float total_protection = this.g.h.getTotalProtection(null);
        int var20 = MathHelper.f(total_protection);
        int var21 = -1;
        if (this.g.h.a(MobEffectList.l)) {
            var21 = this.i % MathHelper.f(healthLimit + 5.0F);
        }

        this.g.C.a("armor");

        for(int var22 = 0; var22 < 10; ++var22) {
            if (total_protection > 0.0F || this.g.h.isWearingArmor()) {
                int var23 = var11 + var22 * 8;
                if (var22 * 2 + 1 < var20) {
                    this.b(var23, var18, 34, 9, 9, 9);
                }

                if (var22 * 2 + 1 == var20) {
                    this.b(var23, var18, 25, 9, 9, 9);
                }

                if (var22 * 2 + 1 > var20) {
                    this.b(var23, var18, 16, 9, 9, 9);
                }
            }
        }

        this.g.C.c("health");

        for(int var391 = MathHelper.f((healthLimit + currentHealth) / 2.0F) - 1; var391 >= 0; --var391) {
            int var23 = 16;
            if (this.g.h.a(MobEffectList.u)) {
                var23 += 36;
            } else if (this.g.h.a(MobEffectList.v)) {
                var23 += 72;
            }

            byte var24 = 0;
            if (var3) {
                var24 = 1;
            }

            int var25 = MathHelper.f((float)(var391 + 1) / 10.0F) - 1;
            int var26 = var11 + var391 % 10 * 8;
            int var27 = guiUp - var25 * var17;
            if (var4 <= 4) {
                var27 += this.f.nextInt(2);
            }

            if (var391 == var21) {
                var27 -= 2;
            }

            byte var28 = 0;
            if (this.g.f.N().t()) {
                var28 = 5;
            }

            if ((float)var391 < this.g.h.aT() / 2.0F) {
                this.b(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
            }

            if (var3) {
                if (var391 * 2 + 1 < var5) {
                    this.b(var26, var27, var23 + 54, 9 * var28, 9, 9);
                }

                if (var391 * 2 + 1 == var5) {
                    this.b(var26, var27, var23 + 63, 9 * var28, 9, 9);
                }
            }

            if (currentHealthClone > 0.0F) {
                if (currentHealthClone == currentHealth && currentHealth % 2.0F == 1.0F) {
                    this.b(var26, var27, var23 + 153, 9 * var28, 9, 9);
                } else {
                    this.b(var26, var27, var23 + 144, 9 * var28, 9, 9);
                }

                currentHealthClone -= 2.0F;
            } else {
                if (var391 * 2 + 1 < var4) {
                    this.b(var26, var27, var23 + 36, 9 * var28, 9, 9);
                }

                if (var391 * 2 + 1 == var4) {
                    this.b(var26, var27, var23 + 45, 9 * var28, 9, 9);
                }
            }
        }

        Entity var34 = this.g.h.o;
        if (var34 != null && !(var34 instanceof EntityBoat)) {
            if (var34 instanceof EntityLiving) {
                this.g.C.c("mountHealth");
                EntityLiving var38 = (EntityLiving)var34;
                int var35 = (int)Math.ceil(var38.aN());
                float var37 = var38.aT();
                int var26 = (int)(var37 + 0.5F) / 2;
                if (var26 > 30) {
                    var26 = 30;
                }

                int var27 = guiUp;

                for(int var39 = 0; var26 > 0; var39 += 20) {
                    int var29 = Math.min(var26, 10);
                    var26 -= var29;

                    for(int var30 = 0; var30 < var29; ++var30) {
                        byte var31 = 52;
                        byte var32 = 0;
                        int var33 = var12 - var30 * 8 - 9;
                        this.b(var33, var27, var31 + var32 * 9, 9, 9, 9);
                        if (var30 * 2 + 1 + var39 < var35) {
                            this.b(var33, var27, var31 + 36, 9, 9, 9);
                        }

                        if (var30 * 2 + 1 + var39 == var35) {
                            this.b(var33, var27, var31 + 45, 9, 9, 9);
                        }
                    }

                    var27 -= 10;
                }
            }
        } else {
            this.g.C.c("food");

            for(int renderIndex = 0; renderIndex < 10; ++renderIndex) {
                int y = guiUp;
                int var25 = 16;
                byte var36 = 0;
                //if hunger
                if (this.g.h.a(MobEffectList.s)) {
                    var25 += 36;
                    var36 = 13;
                }

                if (this.g.h.isHungry() && this.i % (foodLevel * 3 + 1) == 0) {
                    y = guiUp + (this.f.nextInt(3) - 1);
                }

                int x = var12 - renderIndex * 8 - 9;
                if (renderIndex < ( this.g.h).bI().getNutritionLimit() / 2) {
                    this.b(x, y, 16 + var36 * 9, 27, 9, 9);
                }

                if (renderIndex * 2 + 1 < foodLevel) {
                    this.b(x, y, var25 + 36, 27, 9, 9);
                }

                if (renderIndex * 2 + 1 == foodLevel) {
                    this.b(x, y, var25 + 45, 27, 9, 9);
                }
            }
            float protein = this.g.h.getProtein();
            float phytonutrients = this.g.h.getPhytonutrients();
            int y = guiUp+32;
            int x = var12+240;
            GL11.glPushMatrix();
                GL11.glPushMatrix();
                    this.b(this.g.l,(int)phytonutrients + "/" + 160000, x-167,y-8,0xffffff);
                    GL11.glScalef(0.6f,1f,1f);
                    this.g.J().a(Constant.icons_ite);
                    this.b(x,y,0,106,182,6);
                    this.b(x,y,0,94, (int) (182f * (  phytonutrients / 160000f )),6);
                GL11.glPopMatrix();
            x=var12 - 303;
                GL11.glPushMatrix();
                    this.b(this.g.l,(int)protein + "/" + 160000, x,y-8,0xffffff);
                    GL11.glScalef(0.6f,1f,1f);
                    this.g.J().a(Constant.icons_ite);
                    this.b(x,y,0,106,182,6);
                    this.b(x,y,0,100,(int) (182f * (  protein / 160000f )),6);
                GL11.glPopMatrix();
            GL11.glPopMatrix();
        }

        this.g.C.c("air");
        if (this.g.h.a(Material.h)) {
            int var23 = this.g.h.al();
                int var35 = MathHelper.f((double)(var23 - 2) * 10.0D / 300.0D);
            int var25 = MathHelper.f((double)var23 * 10.0D / 300.0D) - var35;

            for(int var26 = 0; var26 < var35 + var25; ++var26) {
                if (var26 < var35) {
                    this.b(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
                } else {
                    this.b(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
                }
            }
        }

        this.g.C.b();
    }

    //blit()
    @Marker
    public void b(int x, int y, int textureX, int textureY, int width, int height) {

    }
}
