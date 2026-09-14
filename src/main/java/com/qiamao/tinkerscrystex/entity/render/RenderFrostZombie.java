package com.qiamao.tinkerscrystex.entity.render;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.entity.EntityFrostZombie;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderFrostZombie extends RenderBiped<EntityFrostZombie> {

    // 指向我们刚才生成的冰蓝霜冻僵尸纹理
    private static final ResourceLocation FROST_ZOMBIE_TEXTURE = new ResourceLocation(TinkersCrystex.MODID, "textures/entity/frost_zombie.png");

    public RenderFrostZombie(RenderManager renderManagerIn) {
        // 直接使用原版的 ModelZombie，0.5F是影子的半透明度
        super(renderManagerIn, new ModelZombie(), 0.5F);
        // 添加衣服/盔甲的渲染层
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        });
        // 添加自发光眼睛层
        this.addLayer(new LayerFrostZombieEyes(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFrostZombie entity) {
        return FROST_ZOMBIE_TEXTURE;
    }
}
