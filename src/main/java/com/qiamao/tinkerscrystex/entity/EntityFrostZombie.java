package com.qiamao.tinkerscrystex.entity;

import com.qiamao.tinkerscrystex.init.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import com.qiamao.tinkerscrystex.network.MessageFrostSync;
import com.qiamao.tinkerscrystex.network.ModNetwork;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.math.BlockPos;

public class EntityFrostZombie extends EntityZombie {

    public EntityFrostZombie(World worldIn) {
        super(worldIn);
    }

    /**
     * 判断实体是否可以在当前位置自然生成
     */
    @Override
    public boolean getCanSpawnHere() {
        // 先调用原版僵尸的生成条件 (包含了光照、碰撞箱、所在方块是否合法等判断)
        // 从而保证了 "像原版僵尸一样仅在晚上自然生成"
        if (!super.getCanSpawnHere()) {
            return false;
        }

        // 获取实体将要生成位置的群系
        BlockPos pos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        Biome biome = this.world.getBiome(pos);

        // 判断该群系是否属于寒冷群系
        // isSnowyBiome() 涵盖了大部分雪原、雪山群系，温度通常 <= 0.15
        if (biome.isSnowyBiome() || biome.getTemperature(pos) <= 0.2F) {
            return true;
        }

        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        
        // 血量：25滴 (原版为20)
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        
        // 速度是原版僵尸的1.3x (原版基础速度大概是 0.23D)
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D * 1.3D);
        
        // 伤害是原版僵尸的0.85 (原版普通僵尸伤害是 3.0D)
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D * 0.85D);
        
        // 如果想让它不怕白天太阳，可以解除这行注释，目前保持与普通僵尸类似
        // this.isImmuneToFire = true;
    }

    @Override
    public boolean attackEntityAsMob(net.minecraft.entity.Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        // 如果成功击中玩家
        if (flag && entityIn instanceof EntityLivingBase) {
            // 27% 的概率让玩家获得 3-4 秒的霜冻 buff
            if (this.rand.nextFloat() < 0.27F) {
                // 3-4 秒，即 60-80 ticks
                int duration = 60 + this.rand.nextInt(21);
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.FROST_SLOW, duration, 0));
                
                // 发送网络包以同步冰冻 UI (如果是玩家被攻击)
                ModNetwork.INSTANCE.sendToAllAround(
                    new MessageFrostSync(entityIn.getEntityId(), true), 
                    new NetworkRegistry.TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64)
                );
            }
        }

        return flag;
    }
}
