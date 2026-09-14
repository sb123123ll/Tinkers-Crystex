package com.qiamao.tinkerscrystex.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * 棕壳 (Brown Shell) - 昆虫类友好生物
 */
public class EntityBrownShell extends EntityCreature {

    public EntityBrownShell(World worldIn) {
        super(worldIn);
        // 小型昆虫碰撞箱
        this.setSize(0.4F, 0.25F);
    }

    @Override
    protected void initEntityAI() {
        // 游泳 AI
        this.tasks.addTask(0, new EntityAISwimming(this));
        
        // 害怕玩家与村民：距离小于 3 格时触发疯狂逃逸，逃离至 5 格以外
        // 闲时基础速度 0.0625（约1.25格/秒），逃逸倍率 1.76（约2.2格/秒）
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 3.0F, 1.76D, 1.76D));
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityVillager.class, 3.0F, 1.76D, 1.76D));
        
        // 闲时在陆地移动
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        // 2 点生命值 (1 颗心)
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        // 基础移动速度 0.0625（Minecraft 1 单位速度约 20 格/秒，0.0625 * 20 = 1.25 格/秒）
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0625D);
    }

    @Override
    public boolean canBreatheUnderwater() {
        // 不会被水淹死
        return true;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        // 击杀后 15% 概率掉落 1 点经验
        return this.rand.nextFloat() < 0.15F ? 1 : 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // 无音效
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        // 无音效
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        // 无音效
        return null;
    }
}
