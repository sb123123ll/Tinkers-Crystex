package com.qiamao.tinkerscrystex.entity.render;

import com.qiamao.tinkerscrystex.entity.EntityBrownShell;
import com.qiamao.tinkerscrystex.entity.model.ModelBrownShell;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 棕壳 (Brown Shell) 实体渲染器
 */
@SideOnly(Side.CLIENT)
public class RenderBrownShell extends RenderLiving<EntityBrownShell> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("tinkerscrystex:textures/entity/brown_shell.png");

    public RenderBrownShell(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBrownShell(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBrownShell entity) {
        return TEXTURE;
    }
}
