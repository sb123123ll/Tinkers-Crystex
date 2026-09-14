package com.qiamao.tinkerscrystex.client;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.init.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersCrystex.MODID)
public class ClientEventHandler {
    private static float frostOverlayAlpha = 0.0f;
    private static final ResourceLocation FROST_OVERLAY = new ResourceLocation(TinkersCrystex.MODID, "textures/gui/frost_overlay.png");

    // 用于缓存网络包同步过来的实体冰冻状态（使用 ConcurrentHashMap 确保线程安全）
    private static final Map<Integer, Long> frostedEntities = new ConcurrentHashMap<>();

    /**
     * 由网络数据包调用：设置实体的冰冻状态。
     * 由于状态是瞬时的，我们给它一个时间戳，渲染时判断如果在 X 毫秒内，则显示冰冻。
     */
    public static void setEntityFrosted(int entityId, boolean isFrosted) {
        if (isFrosted) {
            // 记录冰冻发生的时间，药水通常持续 3-4 秒，我们在这里兜底存 3500 毫秒
            frostedEntities.put(entityId, System.currentTimeMillis() + 3500);
        } else {
            frostedEntities.remove(entityId);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null) {
            boolean hasFrost = false;

            // 1. 判断原版药水状态（首选机制）
            if (mc.player.isPotionActive(ModPotions.FROST_SLOW)) {
                hasFrost = true;
            } 
            // 2. 判断网络包同步的状态兜底（针对 PVP 等边缘情况延迟）
            else if (frostedEntities.containsKey(mc.player.getEntityId())) {
                long expireTime = frostedEntities.get(mc.player.getEntityId());
                if (System.currentTimeMillis() < expireTime) {
                    hasFrost = true;
                } else {
                    frostedEntities.remove(mc.player.getEntityId());
                }
            }

            if (hasFrost) {
                // 适中的过渡速度：每 tick 增加 0.025f (约 2 秒内拉满)
                frostOverlayAlpha = Math.min(1.0f, frostOverlayAlpha + 0.025f);
            } else {
                // 适中的消失速度：每 tick 减少 0.025f (约 2 秒内消失)
                frostOverlayAlpha = Math.max(0.0f, frostOverlayAlpha - 0.025f);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (frostOverlayAlpha > 0.0f) {
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution scaledRes = event.getResolution();
            
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, 
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, 
                GlStateManager.SourceFactor.ONE, 
                GlStateManager.DestFactor.ZERO
            );
            GlStateManager.color(1.0f, 1.0f, 1.0f, frostOverlayAlpha);
            
            mc.getTextureManager().bindTexture(FROST_OVERLAY);
            
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            
            double width = scaledRes.getScaledWidth();
            double height = scaledRes.getScaledHeight();
            
            // 绘制全屏覆盖，四周边缘分布雪花
            buffer.pos(0.0D, height, -90.0D).tex(0.0D, 1.0D).endVertex();
            buffer.pos(width, height, -90.0D).tex(1.0D, 1.0D).endVertex();
            buffer.pos(width, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
            buffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
            tessellator.draw();
            
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}
