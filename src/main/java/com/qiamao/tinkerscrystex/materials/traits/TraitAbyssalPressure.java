package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import java.util.Random;

public class TraitAbyssalPressure extends AbstractTrait {
    public static final TraitAbyssalPressure abyssal_pressure = new TraitAbyssalPressure();
    private static final Random rand = new Random();

    public TraitAbyssalPressure() {
        super("crystex_abyssal_pressure", 0x1E90FF);
    }

    private boolean isPlayerInWater(EntityLivingBase player) {
        if (player == null) return false;
        
        // 检查头部是否在水中 (主要判定水下挖掘)
        if (player.isInsideOfMaterial(Material.WATER)) {
            return true;
        }
        
        // 检查脚下是否在水中或雨中 (稍微宽容的判定)
        World world = player.getEntityWorld();
        BlockPos pos = player.getPosition();
        return world.getBlockState(pos).getMaterial() == Material.WATER || 
               world.getBlockState(pos.down()).getMaterial() == Material.WATER ||
               (world.isRaining() && world.canSeeSky(pos));
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (isPlayerInWater(event.getEntityPlayer())) {
            // 水中：挖掘速度提升 80%
            event.setNewSpeed(event.getNewSpeed() * 1.8f);
        } else {
            // 旱地迟钝：挖掘速度降低 15%
            event.setNewSpeed(event.getNewSpeed() * 0.85f);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (isPlayerInWater(player)) {
            // 水中：攻击伤害提升 40%
            newDamage *= 1.4f;
        } else {
            // 旱地迟钝：攻击伤害恢复正常，我是神经病我要写疯了我操你的!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            newDamage *= 1.0f;
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target != null && target.isEntityAlive()) {
            
            // 守卫者特攻：触发率提升至 50%
            boolean isGuardian = target instanceof EntityGuardian || target instanceof EntityElderGuardian;
            float triggerChance = isGuardian ? 0.5f : 0.3f;
            
            if (rand.nextFloat() < triggerChance) {
                // 水压震荡触发
                if (target.isInsideOfMaterial(Material.WATER)) {
                    // 水中：击飞 8-12 格 (大致速度 1.2-1.5)，消耗空气
                    target.addVelocity(0, 1.2 + rand.nextDouble() * 0.3, 0);
                    
                    // 瞬间消耗 3 秒气泡 (3 * 20 = 60 ticks)
                    int currentAir = target.getAir();
                    target.setAir(Math.max(-20, currentAir - 60)); // -20 会立即导致溺水伤害
                } else {
                    // 陆地：击飞 4-6 格 (大致速度 0.8-1.0)
                    target.addVelocity(0, 0.8 + rand.nextDouble() * 0.2, 0);
                }
                target.velocityChanged = true;
            }
        }
    }
}