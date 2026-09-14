package com.qiamao.tinkerscrystex.entity.render;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.entity.EntityFrostZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerFrostZombieEyes implements LayerRenderer<EntityFrostZombie> {

    // 指向我们刚才生成的只有眼睛的透明发光纹理
    private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(TinkersCrystex.MODID, "textures/entity/frost_zombie_eyes.png");
    private final RenderFrostZombie renderFrostZombie;

    public LayerFrostZombieEyes(RenderFrostZombie renderFrostZombieIn) {
        this.renderFrostZombie = renderFrostZombieIn;
    }

    @Override
    public void doRenderLayer(EntityFrostZombie entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        // 绑定发光纹理
        this.renderFrostZombie.bindTexture(GLOW_TEXTURE);
        
        // 开启混合，这是实现发光的关键步骤
        GlStateManager.enableBlend();
        // 关闭透明度测试
        GlStateManager.disableAlpha();
        // 设置加法混合模式 (使得亮色叠加，实现发光效果)
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        if (entitylivingbaseIn.isInvisible()) {
            GlStateManager.depthMask(false);
        } else {
            GlStateManager.depthMask(true);
        }

        // 屏蔽环境光照，将其强制设置为最大亮度（模拟光源）
        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        
        // 设置绘制的全局颜色（1.0，即原色）
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 委托主模型的 getMainModel 去渲染，这个时候因为绑定了眼睛的纹理，并且光照拉满，就只画了发光的眼睛
        this.renderFrostZombie.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        // 恢复原来的光照设置，恢复混合和透明度测试配置
        i = entitylivingbaseIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        
        this.renderFrostZombie.setLightmap(entitylivingbaseIn);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
