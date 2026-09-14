package com.qiamao.tinkerscrystex.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * 棕壳 (Brown Shell) 模型类
 * 包含六足交替爬行与触角微动动画
 */
public class ModelBrownShell extends ModelBase {

    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer antenna_left;
    private final ModelRenderer antenna_right;
    private final ModelRenderer leg_front_left;
    private final ModelRenderer leg_front_right;
    private final ModelRenderer leg_mid_left;
    private final ModelRenderer leg_mid_right;
    private final ModelRenderer leg_back_left;
    private final ModelRenderer leg_back_right;

    public ModelBrownShell() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 22.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 0, -2.5F, -1.0F, -4.0F, 5, 2, 4, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 12, -3.0F, -0.8F, 0.0F, 6, 2, 7, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, -4.0F);
        body.addChild(head);
        setRotationAngle(head, -0.2618F, 0.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 36, -1.5F, -0.5F, -1.5F, 3, 2, 2, 0.0F, false));

        antenna_left = new ModelRenderer(this);
        antenna_left.setRotationPoint(-1.0F, 0.0F, -1.0F);
        head.addChild(antenna_left);
        setRotationAngle(antenna_left, -0.1745F, -0.4363F, 0.0F);
        antenna_left.cubeList.add(new ModelBox(antenna_left, 24, 36, -0.5F, -0.2F, -6.0F, 1, 0, 6, 0.0F, false));

        antenna_right = new ModelRenderer(this);
        antenna_right.setRotationPoint(1.0F, 0.0F, -1.0F);
        head.addChild(antenna_right);
        setRotationAngle(antenna_right, -0.1745F, 0.4363F, 0.0F);
        antenna_right.cubeList.add(new ModelBox(antenna_right, 24, 36, -0.5F, -0.2F, -6.0F, 1, 0, 6, 0.0F, false));

        leg_front_left = new ModelRenderer(this);
        leg_front_left.setRotationPoint(-2.5F, 0.0F, -2.0F);
        body.addChild(leg_front_left);
        setRotationAngle(leg_front_left, 0.0F, -0.3491F, -0.1745F);
        leg_front_left.cubeList.add(new ModelBox(leg_front_left, 0, 48, -2.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));

        leg_front_right = new ModelRenderer(this);
        leg_front_right.setRotationPoint(2.5F, 0.0F, -2.0F);
        body.addChild(leg_front_right);
        setRotationAngle(leg_front_right, 0.0F, 0.3491F, 0.1745F);
        leg_front_right.cubeList.add(new ModelBox(leg_front_right, 0, 48, -0.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));

        leg_mid_left = new ModelRenderer(this);
        leg_mid_left.setRotationPoint(-3.0F, 0.0F, 0.0F);
        body.addChild(leg_mid_left);
        setRotationAngle(leg_mid_left, 0.0F, 0.0F, -0.1745F);
        leg_mid_left.cubeList.add(new ModelBox(leg_mid_left, 0, 52, -3.0F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));

        leg_mid_right = new ModelRenderer(this);
        leg_mid_right.setRotationPoint(3.0F, 0.0F, 0.0F);
        body.addChild(leg_mid_right);
        setRotationAngle(leg_mid_right, 0.0F, 0.0F, 0.1745F);
        leg_mid_right.cubeList.add(new ModelBox(leg_mid_right, 0, 52, -0.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));

        leg_back_left = new ModelRenderer(this);
        leg_back_left.setRotationPoint(-2.5F, 0.0F, 2.0F);
        body.addChild(leg_back_left);
        setRotationAngle(leg_back_left, 0.0F, 0.4363F, -0.1745F);
        leg_back_left.cubeList.add(new ModelBox(leg_back_left, 0, 56, -3.5F, 0.0F, -0.5F, 4, 0, 1, 0.0F, false));

        leg_back_right = new ModelRenderer(this);
        leg_back_right.setRotationPoint(2.5F, 0.0F, 2.0F);
        body.addChild(leg_back_right);
        setRotationAngle(leg_back_right, 0.0F, -0.4363F, 0.1745F);
        leg_back_right.cubeList.add(new ModelBox(leg_back_right, 0, 56, -0.5F, 0.0F, -0.5F, 4, 0, 1, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        body.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        // 头部轻微随视线转动
        this.head.rotateAngleY = netHeadYaw * 0.017453292F * 0.5F;
        this.head.rotateAngleX = -0.2618F + headPitch * 0.017453292F * 0.5F;

        // 触角待机自然颤动
        float antennaWave = MathHelper.sin(ageInTicks * 0.2F) * 0.08F;
        this.antenna_left.rotateAngleY = -0.4363F + antennaWave;
        this.antenna_right.rotateAngleY = 0.4363F - antennaWave;

        // 昆虫六足经典三足步态 (Tripod gait)
        float legSwing = MathHelper.cos(limbSwing * 1.5F) * 0.6F * limbSwingAmount;
        this.leg_front_left.rotateAngleY = -0.3491F + legSwing;
        this.leg_mid_right.rotateAngleY = legSwing;
        this.leg_back_left.rotateAngleY = 0.4363F + legSwing;

        this.leg_front_right.rotateAngleY = 0.3491F - legSwing;
        this.leg_mid_left.rotateAngleY = -legSwing;
        this.leg_back_right.rotateAngleY = -0.4363F - legSwing;
    }
}
