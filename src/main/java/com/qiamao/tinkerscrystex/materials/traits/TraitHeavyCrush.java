package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import com.qiamao.tinkerscrystex.init.ModPotions;

public class TraitHeavyCrush extends AbstractTrait {

    public TraitHeavyCrush() {
        super("crystex_heavy_crush", 0x333333); // 黑色/暗灰色
    }

    private boolean isHydrolyzed(EntityLivingBase player) {
        return player.isInWater() || player.world.isRainingAt(player.getPosition());
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && !isHydrolyzed(player) && !player.world.isRemote) {
            // 20% 概率施加禁锢效果，持续2秒 (40 tick)
            if (random.nextFloat() < 0.20f) {
                target.addPotionEffect(new PotionEffect(ModPotions.ROOT, 40, 0, false, true));
            }
        }
    }
}
