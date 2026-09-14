package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAbyssaliteOre extends BlockBase {
    public BlockAbyssaliteOre() {
        super("abyssalite_ore", Material.ROCK);
        setHardness(4.5f); // 略微提升硬度以体现海底高压产物
        setResistance(30.0f); // 高抗爆性
        setHarvestLevel("pickaxe", 3); // 钻石级
        setSoundType(SoundType.STONE);
        setLightLevel(0.4f); // 增强微弱发光
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        // 在深海环境或平时，周围随机产生深海的蓝色或发光粒子，体现幽深特质
        if (rand.nextInt(3) == 0) {
            double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.5D;
            double d1 = (double) pos.getY() + 0.5D + (rand.nextDouble() - 0.5D) * 0.5D;
            double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.5D;
            // 使用原版的附魔台字态粒子或水滴粒子，结合颜色表现微光
            worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            if (rand.nextBoolean()) {
                worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, d0, d1, d2, 0.0D, 0.05D, 0.0D);
            }
        }
    }
}