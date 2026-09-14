package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import java.util.Random;

public class TraitDetonate extends AbstractTrait {
    public static final TraitDetonate detonate = new TraitDetonate();
    private static final Random rand = new Random();

    public TraitDetonate() {
        super("crystex_detonate", 0xFF4500); // 橙红色
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target != null && target.isEntityAlive()) {
            // 20% 概率触发爆炸
            if (rand.nextFloat() < 0.2f) {
                World world = player.getEntityWorld();
                if (!world.isRemote) {
                    // 创建小型爆炸，强度为 1.5，不破坏方块，不产生火焰
                    world.createExplosion(player, target.posX, target.posY + (target.height / 2.0F), target.posZ, 1.5F, false);
                    
                    // 玩家承受少量的自伤 (约最大生命值的 5%)
                    float selfDamage = player.getMaxHealth() * 0.05f;
                    // causeExplosionDamage accepts Explosion object in 1.12.2
                    Explosion explosion = new Explosion(world, player, target.posX, target.posY + (target.height / 2.0F), target.posZ, 1.5F, false, false);
                    player.attackEntityFrom(DamageSource.causeExplosionDamage(explosion), selfDamage);
                }
            }
        }
    }
}