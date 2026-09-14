package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.entity.EntityLivingBase;
import com.qiamao.tinkerscrystex.init.ModPotions;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import com.qiamao.tinkerscrystex.network.MessageFrostSync;
import com.qiamao.tinkerscrystex.network.ModNetwork;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TraitFrost extends AbstractTrait {

    public static final TraitFrost frost = new TraitFrost();

    public TraitFrost() {
        super("crystex_frost", 0x4A90E2);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target != null && target.isEntityAlive()) {
            applyFrost(target);
        }
    }

    private void applyFrost(EntityLivingBase target) {
        // 霜缓，持续 2 秒 (40 ticks)
        int duration = 40;
        int amplifier = 0;
        
        PotionEffect currentEffect = target.getActivePotionEffect(ModPotions.FROST_SLOW);
        if (currentEffect != null) {
            amplifier = Math.min(currentEffect.getAmplifier() + 1, 2); // 最高 3 级 (amplifier 2)
            duration += currentEffect.getDuration();
        }
        
        if (!target.world.isRemote) {
            target.addPotionEffect(new PotionEffect(ModPotions.FROST_SLOW, duration, amplifier));
            // 向周围的玩家同步冰冻覆盖层状态
            ModNetwork.INSTANCE.sendToAllAround(
                new MessageFrostSync(target.getEntityId(), true), 
                new NetworkRegistry.TargetPoint(target.dimension, target.posX, target.posY, target.posZ, 64)
            );
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (target != null && target.isImmuneToFire()) {
            // 对火焰系生物额外造成 25% 伤害
            return newDamage * 1.25f;
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}
