package net.xiaoyu233.mitemod.miteite.render.entity.model;

import net.minecraft.*;

@SuppressWarnings("ALL")
public class ModelAncientDragon extends bbo {
    private final bcu bb_main;
    private final bcu a;
    private final bcu bone;
    private final bcu shengti;
    private final bcu leftArm;
    private final bcu bone3;
    private final bcu bone4;
    private final bcu rightArm;
    private final bcu bone2;
    private final bcu bone5;
    private final bcu rightLeg;
    private final bcu bone7;
    private final bcu bone8;
    private final bcu leftLeg;
    private final bcu bone6;
    private final bcu bone9;
    private final bcu bozi;
    private final bcu tou;
    private final bcu bone11;
    private final bcu bone12;
    private final bcu bone14;
    private final bcu bone13;
    private final bcu bone15;
    private final bcu bone16;
    private final bcu bone17;
    private final bcu bone18;
    private final bcu bone19;
    private final bcu bone20;
    private final bcu bone34;
    private final bcu bone35;
    private final bcu bone36;
    private final bcu bone10;
    private final bcu weib;
    private final bcu bone21;
    private final bcu bone22;
    private final bcu youchi;
    private final bcu bone24;
    private final bcu bone25;
    private final bcu bone27;
    private final bcu bone30;
    private final bcu bone31;
    private final bcu zuochi;
    private final bcu bone23;
    private final bcu bone26;
    private final bcu bone28;
    private final bcu bone29;
    private final bcu bone32;
    private final bcu bone33;
    private final bcu jiao;

    public ModelAncientDragon() {
        t = 128;
        u = 128;

        bb_main = new bcu(this);
        bb_main.a(0.0F, 24.0F, 0.0F);
        bb_main.l.add(new bcp(bb_main, 0, 25, 0.0F, -16.0F, 5.0F, 1, 1, 1, 0.0F));

		a = new bcu(this);
		a.a(0.5F, 15.5F, 1.5F);
        setRotationAngle(a, -0.7854F, 0.0F, 0.0F);
		a.l.add(new bcp(a, 42, 43, -4.0F, -3.5F, -0.5F, 8, 4, 5, 0.0F));

        bone = new bcu(this);
        bone.a(0.5F, 15.5F, 1.5F);
        setRotationAngle(bone, 0.7854F, 0.0F, 0.0F);
        bone.l.add(new bcp(bone, 28, 33, -4.0F, -8.5F, -8.5F, 8, 5, 5, 0.0F));

        shengti = new bcu(this);
        shengti.a(0.5F, 14.5F, -2.5F);
        shengti.l.add(new bcp(shengti, 0, 0, -3.5F, -2.5F, -7.5F, 7, 5, 15, 0.0F));
        shengti.l.add(new bcp(shengti, 0, 43, -0.5F, -3.5F, -1.5F, 1, 1, 1, 0.0F));
        shengti.l.add(new bcp(shengti, 22, 39, -0.5F, -3.5F, 0.5F, 1, 1, 1, 0.0F));
        shengti.l.add(new bcp(shengti, 28, 35, -0.5F, -10.5F, -6.5F, 1, 1, 1, 0.0F));
        shengti.l.add(new bcp(shengti, 28, 33, -0.5F, -3.5F, 2.5F, 1, 1, 1, 0.0F));
        shengti.l.add(new bcp(shengti, 29, 10, -0.5F, -3.5F, 4.5F, 1, 1, 1, 0.0F));

        leftArm = new bcu(this);
        leftArm.a(5.0F, 17.1F, -7.0F);
        setRotationAngle(leftArm, -0.7854F, -3.0543F, 0.0F);
        leftArm.l.add(new bcp(leftArm, 58, 8, -1.0087F, -1.2346F, -2.0937F, 3, 5, 3, 0.0F));

        bone3 = new bcu(this);
        bone3.a(-0.0349F, 3.9304F, 3.3939F);
        setRotationAngle(bone3, 1.5708F, 0.0044F, 0.0F);
        leftArm.a(bone3);
        bone3.l.add(new bcp(bone3, 52, 60, -0.4466F, -2.4828F, 0.3011F, 2, 5, 2, 0.0F));

        bone4 = new bcu(this);
        bone4.a(0.0F, -2.0F, -4.0F);
        setRotationAngle(bone4, -0.7854F, 0.0F, 0.0F);
        bone3.a(bone4);
        bone4.l.add(new bcp(bone4, 56, 4, -1.0F, -1.0F, 6.0F, 3, 1, 3, 0.0F));

        rightArm = new bcu(this);
        rightArm.a(-3.0F, 17.7F, -7.2F);
        setRotationAngle(rightArm, -0.7679F, 3.1416F, 0.0F);
        rightArm.l.add(new bcp(rightArm, 16, 57, -1.0F, -1.6458F, -2.3755F, 3, 5, 3, 0.0F));

        bone2 = new bcu(this);
        bone2.a(0.0F, 3.8597F, 2.8473F);
        setRotationAngle(bone2, 1.5359F, 0.0F, 0.0F);
        rightArm.a(bone2);
        bone2.l.add(new bcp(bone2, 13, 46, 0.0F, -2.6354F, 0.4733F, 2, 5, 2, 0.0F));

        bone5 = new bcu(this);
        bone5.a(0.0F, -2.0F, -4.0F);
        setRotationAngle(bone5, -0.7854F, 0.0F, 0.0F);
        bone2.a(bone5);
        bone5.l.add(new bcp(bone5, 0, 37, -1.0F, -1.0F, 6.0F, 3, 1, 3, 0.0F));

        rightLeg = new bcu(this);
        rightLeg.a(-3.0F, 18.0F, 3.5F);
        setRotationAngle(rightLeg, 0.6981F, 0.0F, 0.0F);
        rightLeg.l.add(new bcp(rightLeg, 33, 55, -2.0F, -1.0F, 0.5F, 3, 6, 3, 0.0F));

        bone7 = new bcu(this);
        bone7.a(-0.5F, 2.0F, -2.0F);
        setRotationAngle(bone7, 0.1745F, 0.0F, 0.0F);
        rightLeg.a(bone7);
        bone7.l.add(new bcp(bone7, 47, 4, -1.0F, 2.0F, -1.0F, 2, 2, 5, 0.0F));

        bone8 = new bcu(this);
        bone8.a(0.0F, 1.5F, -3.5F);
        setRotationAngle(bone8, 0.6981F, 0.0F, 0.0F);
        bone7.a(bone8);
        bone8.l.add(new bcp(bone8, 11, 57, -1.5F, 1.5F, 0.5F, 3, 2, 1, 0.0F));

        leftLeg = new bcu(this);
        leftLeg.a(5.0F, 18.0F, 3.5F);
        setRotationAngle(leftLeg, 0.6981F, 0.0F, 0.0F);
        leftLeg.l.add(new bcp(leftLeg, 14, 30, -2.0F, -1.0F, 0.5F, 3, 6, 3, 0.0F));

        bone6 = new bcu(this);
        bone6.a(-0.5F, 2.0F, -2.0F);
        setRotationAngle(bone6, 0.1745F, 0.0F, 0.0F);
        leftLeg.a(bone6);
        bone6.l.add(new bcp(bone6, 26, 46, -1.0F, 2.0F, -1.0F, 2, 2, 5, 0.0F));

        bone9 = new bcu(this);
        bone9.a(0.0F, 1.5F, -3.5F);
        setRotationAngle(bone9, 0.6981F, 0.0F, 0.0F);
        bone6.a(bone9);
        bone9.l.add(new bcp(bone9, 14, 39, -1.5F, 1.5F, 0.5F, 3, 2, 1, 0.0F));

        bozi = new bcu(this);
        bozi.a(0.5F, 10.0F, -9.5F);
        setRotationAngle(bozi, -0.4363F, 0.0F, 0.0F);
        bozi.l.add(new bcp(bozi, 0, 27, -1.5F, -4.0F, 0.5F, 3, 7, 3, 0.0F));
        bozi.l.add(new bcp(bozi, 13, 43, -0.5F, -3.0F, 3.5F, 1, 1, 1, 0.0F));
        bozi.l.add(new bcp(bozi, 6, 43, -0.5F, -1.0F, 3.5F, 1, 1, 1, 0.0F));

        tou = new bcu(this);
        tou.a(-0.25F, 3.375F, 4.0F);
        setRotationAngle(tou, 0.2618F, 0.0F, 0.0F);
        bozi.a(tou);
        tou.l.add(new bcp(tou, 49, 52, -2.25F, -11.375F, -7.5F, 5, 4, 4, 0.0F));

        bone11 = new bcu(this);
        bone11.a(-0.25F, 9.625F, 5.5F);
        tou.a(bone11);

        bone12 = new bcu(this);
        bone12.a(0.0F, 0.0F, 0.0F);
        bone11.a(bone12);
        bone12.l.add(new bcp(bone12, 29, 12, -4.0F, -21.0F, -12.0F, 9, 1, 1, 0.0F));

        bone14 = new bcu(this);
        bone14.a(-3.5F, -21.5F, -12.5F);
        setRotationAngle(bone14, -0.6981F, -0.2618F, 0.0F);
        bone12.a(bone14);
        bone14.l.add(new bcp(bone14, 58, 16, -0.5F, -0.5F, -1.5F, 1, 1, 3, 0.0F));

        bone13 = new bcu(this);
        bone13.a(4.5F, -21.5F, -12.5F);
        setRotationAngle(bone13, -0.6981F, 0.2618F, 0.0F);
        bone12.a(bone13);
        bone13.l.add(new bcp(bone13, 0, 48, -0.5F, -0.5F, -1.5F, 1, 1, 3, 0.0F));

        bone15 = new bcu(this);
        bone15.a(0.5F, -21.5F, -13.0F);
        setRotationAngle(bone15, -0.6109F, 0.0F, 0.0F);
        bone12.a(bone15);
        bone15.l.add(new bcp(bone15, 44, 14, -0.5F, -0.5F, -3.0F, 1, 1, 4, 0.0F));

        bone16 = new bcu(this);
        bone16.a(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone16, 0.1745F, 0.0F, 0.0F);
        bone15.a(bone16);
        bone16.l.add(new bcp(bone16, 11, 9, -0.5F, -4.0F, -3.0F, 1, 4, 1, 0.0F));

        bone17 = new bcu(this);
        bone17.a(-1.5F, -21.5F, -8.0F);
        setRotationAngle(bone17, 0.4363F, -0.3491F, 0.0F);
        bone12.a(bone17);
        bone17.l.add(new bcp(bone17, 0, 43, -0.5F, -0.5F, -2.0F, 1, 1, 4, 0.0F));

        bone18 = new bcu(this);
        bone18.a(0.0F, 0.0F, 0.0F);
        bone12.a(bone18);

        bone19 = new bcu(this);
        bone19.a(2.5F, -21.5F, -8.0F);
        setRotationAngle(bone19, 0.3491F, 0.3491F, 0.0F);
        bone18.a(bone19);
        bone19.l.add(new bcp(bone19, 0, 20, -0.5F, -0.5F, -2.0F, 1, 1, 4, 0.0F));

        bone20 = new bcu(this);
        bone20.a(-0.5F, -22.5F, -8.5F);
        setRotationAngle(bone20, 0.7854F, 0.0F, 0.0F);
        bone12.a(bone20);
        bone20.l.add(new bcp(bone20, 40, 59, 0.5F, -1.5F, -4.5F, 1, 1, 5, 0.0F));

        bone34 = new bcu(this);
        bone34.a(0.0F, 0.0F, 0.0F);
        bone11.a(bone34);

        bone35 = new bcu(this);
        bone35.a(0.0F, 0.0F, 0.0F);
        bone34.a(bone35);
        bone35.l.add(new bcp(bone35, 116, 0, -1.0F, -18.5F, -16.0F, 3, 1, 3, 0.0F));
        bone35.l.add(new bcp(bone35, 0, 0, -1.0F, -19.0F, -15.0F, 1, 1, 1, 0.0F));
        bone35.l.add(new bcp(bone35, 0, 0, 1.0F, -19.0F, -15.0F, 1, 1, 1, 0.0F));
        bone35.l.add(new bcp(bone35, 126, 126, 1.0F, -18.0F, -15.0F, 0, 1, 1, 0.0F));
        bone35.l.add(new bcp(bone35, 123, 126, 0.0F, -18.0F, -15.0F, 0, 1, 1, 0.0F));

        bone36 = new bcu(this);
        bone36.a(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone36, 0.1745F, 0.0F, 0.0F);
        bone34.a(bone36);
        bone36.l.add(new bcp(bone36, 116, 5, -1.0F, -19.5F, -12.0F, 3, 1, 3, 0.0F));

        bone10 = new bcu(this);
        bone10.a(0.0F, -3.5F, 0.0F);
        setRotationAngle(bone10, -0.2618F, 0.0F, 0.0F);
        bozi.a(bone10);
        bone10.l.add(new bcp(bone10, 0, 55, -1.5F, -1.5F, -3.5F, 3, 3, 5, 0.0F));
        bone10.l.add(new bcp(bone10, 21, 43, -0.5F, -1.5F, 2.5F, 1, 1, 1, 0.0F));
        bone10.l.add(new bcp(bone10, 17, 43, -0.5F, -2.5F, -0.5F, 1, 1, 1, 0.0F));

        weib = new bcu(this);
        weib.a(0.5F, 8.0F, 4.5F);
        setRotationAngle(weib, -0.6981F, 0.0F, 0.0F);
        weib.l.add(new bcp(weib, 0, 0, -1.5F, -3.0F, 1.5F, 3, 7, 3, 0.0F));
        weib.l.add(new bcp(weib, 4, 25, -0.5F, 2.0F, 0.5F, 1, 1, 1, 0.0F));
        weib.l.add(new bcp(weib, 6, 22, -0.5F, -3.0F, 0.5F, 1, 1, 1, 0.0F));

        bone21 = new bcu(this);
        bone21.a(-0.25F, 6.25F, 0.75F);
        setRotationAngle(bone21, 0.0873F, 0.0F, 0.0F);
        weib.a(bone21);
        bone21.l.add(new bcp(bone21, 46, 12, -0.75F, -10.25F, 2.25F, 2, 3, 8, 0.0F));
        bone21.l.add(new bcp(bone21, 0, 22, -0.25F, -11.25F, 2.75F, 1, 1, 1, 0.0F));
        bone21.l.add(new bcp(bone21, 6, 20, -0.25F, -11.25F, 4.75F, 1, 1, 1, 0.0F));
        bone21.l.add(new bcp(bone21, 0, 20, -0.25F, -11.25F, 6.75F, 1, 1, 1, 0.0F));

        bone22 = new bcu(this);
        bone22.a(0.125F, 2.3482F, 7.8071F);
        setRotationAngle(bone22, 1.3439F, 0.0F, 0.0F);
        bone21.a(bone22);
        bone22.l.add(new bcp(bone22, 49, 27, -0.5F, -2.5643F, 9.9234F, 1, 3, 6, 0.0F));
        bone22.l.add(new bcp(bone22, 0, 12, -0.5F, -3.5F, 13.5F, 1, 1, 1, 0.0F));
        bone22.l.add(new bcp(bone22, 0, 10, -0.5F, -3.0F, 15.5F, 1, 1, 1, 0.0F));
        bone22.l.add(new bcp(bone22, 9, 0, -0.5F, -1.0F, 15.75F, 1, 1, 1, 0.0F));

        youchi = new bcu(this);
        youchi.a(-6.0F, 12.0F, -6.0F);
        setRotationAngle(youchi, 0.0F, -0.8727F, 0.0F);
        youchi.l.add(new bcp(youchi, 46, 23, -6.0F, -1.0F, -2.0F, 11, 2, 2, 0.0F));

        bone24 = new bcu(this);
        bone24.a(-7.0F, 0.0F, 5.5F);
        setRotationAngle(bone24, 0.0F, -0.2618F, 0.0F);
        youchi.a(bone24);
        bone24.l.add(new bcp(bone24, 14, 46, -1.0F, 0.0F, -7.25F, 1, 1, 10, 0.0F));

        bone25 = new bcu(this);
        bone25.a(-0.5F, -0.5F, 4.5F);
        setRotationAngle(bone25, 0.0F, 1.0472F, 0.0F);
        youchi.a(bone25);
        bone25.l.add(new bcp(bone25, 0, 30, 1.5F, 0.0F, -6.5F, 1, 1, 12, 0.0F));

        bone27 = new bcu(this);
        bone27.a(-3.5F, 0.0F, 9.5F);
        setRotationAngle(bone27, 0.0F, 0.5236F, 0.0F);
        youchi.a(bone27);
        bone27.l.add(new bcp(bone27, 34, 0, 2.5F, -0.25F, -9.5F, 1, 1, 11, 0.0F));

        bone30 = new bcu(this);
        bone30.a(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone30, 0.0F, -0.2618F, 0.0F);
        youchi.a(bone30);
        bone30.l.add(new bcp(bone30, 0, 20, -6.0F, 0.25F, 0.0F, 8, 0, 10, 0.0F));

        bone31 = new bcu(this);
        bone31.a(0.0F, 0.0F, 0.0F);
        youchi.a(bone31);
        bone31.l.add(new bcp(bone31, 20, 27, -6.0F, 0.25F, 0.0F, 11, 0, 6, 0.0F));

        zuochi = new bcu(this);
        zuochi.a(6.0F, 12.0F, -6.0F);
        setRotationAngle(zuochi, 0.0F, 0.7854F, 0.0F);
        zuochi.l.add(new bcp(zuochi, 47, 0, -4.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F));

        bone23 = new bcu(this);
        bone23.a(5.0F, 0.0F, 5.5F);
        setRotationAngle(bone23, 0.0F, 0.1745F, 0.0F);
        zuochi.a(bone23);
        bone23.l.add(new bcp(bone23, 29, 43, 2.0F, 0.0F, -5.5F, 1, 1, 11, 0.0F));

        bone26 = new bcu(this);
        bone26.a(-27.5F, 0.0F, 80.0F);
        setRotationAngle(bone26, 0.0F, 2.5307F, 0.0F);
        bone23.a(bone26);
        bone26.l.add(new bcp(bone26, 14, 33, 23.5F, -0.25F, 74.5F, 1, 1, 12, 0.0F));

        bone28 = new bcu(this);
        bone28.a(-14.5F, 0.0F, 5.5F);
        setRotationAngle(bone28, 0.0F, -1.0472F, 0.0F);
        zuochi.a(bone28);
        bone28.l.add(new bcp(bone28, 0, 43, 5.5F, -0.5F, -19.5F, 1, 1, 11, 0.0F));

        bone29 = new bcu(this);
        bone29.a(0.0F, 0.0F, 0.0F);
        zuochi.a(bone29);

        bone32 = new bcu(this);
        bone32.a(0.0F, 0.0F, 0.0F);
        zuochi.a(bone32);
        bone32.l.add(new bcp(bone32, 19, 20, -4.0F, 0.25F, 0.0F, 10, 0, 7, 0.0F));

        bone33 = new bcu(this);
        bone33.a(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone33, 0.0F, 0.1745F, 0.0F);
        zuochi.a(bone33);
        bone33.l.add(new bcp(bone33, 19, 0, -1.0F, 0.0F, 2.0F, 8, 0, 10, 0.0F));

        jiao = new bcu(this);
        jiao.a(0.0F, 24.0F, 0.0F);
    }

    @Override
    public void a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        bb_main.a(scale);
		a.a(scale);
        bone.a(scale);
        shengti.a(scale);
        leftArm.a(scale);
        rightArm.a(scale);
        rightLeg.a(scale);
        leftLeg.a(scale);
        bozi.a(scale);
        weib.a(scale);
        youchi.a(scale);
        zuochi.a(scale);
        jiao.a(scale);
    }

    @Override
    public void a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = 1.0F;

        this.rightLeg.f = MathHelper.b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f + 0.75f;
        this.leftLeg.f = MathHelper.b(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f + 0.75f;
        this.rightLeg.g = 0.0F;
        this.leftLeg.g = 0.0F;
        this.rightLeg.h = 0.0F;
        this.leftLeg.h = 0.0F;

        this.rightArm.f = MathHelper.b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 5f -1f;
        this.leftArm.f = MathHelper.b(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / 5f -1f;
        this.rightArm.g = -185.5F;
        this.leftArm.g = -185.5F;
        this.rightArm.h = 0.0F;
        this.leftArm.h = 0.0F;
    }

    public void setRotationAngle(bcu modelRenderer, float x, float y, float z) {
        modelRenderer.f = x;
        modelRenderer.g = y;
        modelRenderer.h = z;
    }
}
