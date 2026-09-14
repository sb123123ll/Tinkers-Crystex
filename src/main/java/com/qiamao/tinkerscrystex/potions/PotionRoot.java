package com.qiamao.tinkerscrystex.potions;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import com.qiamao.tinkerscrystex.TinkersCrystex;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionRoot extends Potion {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(TinkersCrystex.MODID, "textures/gui/potion_root.png");

    public PotionRoot() {
        super(true, 0x4B3A2C); // Bad effect, root color
        setPotionName("effect.tinkerscrystex.root");
        setRegistryName(new ResourceLocation(TinkersCrystex.MODID, "root"));
        setIconIndex(0, 0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // Stop movement
        entity.motionX = 0;
        entity.motionZ = 0;
        // Optionally prevent jumping
        entity.setJumping(false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}
