package com.qiamao.tinkerscrystex.potions;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionFrostSlow extends Potion {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TinkersCrystex.MODID, "textures/gui/potion_effects.png");

    public PotionFrostSlow() {
        super(true, 0x6BA3C7); // isBadEffect = true, 液体颜色为霜钢的冰蓝色
        this.setPotionName("effect." + TinkersCrystex.MODID + ".frost_slow");
        this.setRegistryName(new ResourceLocation(TinkersCrystex.MODID, "frost_slow"));
        
        // 核心效果与原版缓慢完全一致：每级降低 15% 移动速度，修饰符类型为乘法修饰(2)
        // 使用独立的 UUID 确保与其他加速/减速效果正常叠加和计算
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.15D, 2);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        // 限制 buff 等级仅在 1-3 级之间生效 (即 amplifier 0-2)
        int clampedAmplifier = Math.min(amplifier, 2);
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, clampedAmplifier);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            // 绘制 18x18 的 buff 图标 (UV从 0,0 开始)
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 256, 256);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        // 绘制 18x18 的 HUD 图标 (UV从 0,0 开始)
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 256, 256);
    }
}
