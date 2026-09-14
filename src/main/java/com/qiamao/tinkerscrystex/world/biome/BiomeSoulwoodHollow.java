package com.qiamao.tinkerscrystex.world.biome;

import com.qiamao.tinkerscrystex.entity.EntityBrownShell;
import com.qiamao.tinkerscrystex.world.WorldGenRelicWood;
import net.minecraft.block.BlockDirt;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BiomeSoulwoodHollow extends Biome {

    public BiomeSoulwoodHollow() {
        super(new BiomeProperties("Soulwood Hollow")
                .setTemperature(0.5F)
                .setRainfall(0.7F)
                .setBaseHeight(-0.2F)
                .setHeightVariation(0.8F));

        // 替换湖底圆石为灵魂沙(这里使用水下替换块配置，原版通常在ChunkGenerator里或者Biome装饰时)
        // 草是淡灰色(通过下方的颜色覆写完成)
        
        // 移除所有自然生成的生物
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();

        // 仅添加群系专属友好生物：棕壳 (权重40，相当于平原中羊12+猪10+牛8+鸡10的总和，2-4只成群刷新)
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBrownShell.class, 40, 2, 4));

        // 我们会通过装饰事件单独控制每区块生成古遗木
        this.decorator.treesPerChunk = 0; 
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 2; // 一些灰色的草
    }
    
    @Override
    public void decorate(net.minecraft.world.World worldIn, Random rand, net.minecraft.util.math.BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        
        // 每个区块尝试生成 2-3 次古遗木
        int treeCount = 2 + rand.nextInt(2);
        WorldGenRelicWood relicWoodGen = new WorldGenRelicWood();
        
        for (int i = 0; i < treeCount; i++) {
            int x = pos.getX() + rand.nextInt(16) + 8;
            int z = pos.getZ() + rand.nextInt(16) + 8;
            int y = worldIn.getHeight(new net.minecraft.util.math.BlockPos(x, 0, z)).getY();
            relicWoodGen.generate(worldIn, rand, new net.minecraft.util.math.BlockPos(x, y, z));
        }
        
        // 替换水体底部的方块为灵魂沙
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    net.minecraft.util.math.BlockPos currentPos = pos.add(x, y, z);
                    if (worldIn.getBlockState(currentPos).getBlock() == Blocks.WATER) {
                        net.minecraft.util.math.BlockPos downPos = currentPos.down();
                        if (worldIn.getBlockState(downPos).getBlock() == Blocks.DIRT || 
                            worldIn.getBlockState(downPos).getBlock() == Blocks.GRAVEL ||
                            worldIn.getBlockState(downPos).getBlock() == Blocks.SAND) {
                            worldIn.setBlockState(downPos, Blocks.SOUL_SAND.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(net.minecraft.util.math.BlockPos pos) {
        // 暗灰绿色
        return 0x4C4C34;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(net.minecraft.util.math.BlockPos pos) {
        // 枯木棕
        return 0x59411E;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float currentTemperature) {
        // 天空亮度调暗，返回一种暗淡的色调 (偏深沉的灰蓝)
        return 0x1A1F24;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getWaterColorMultiplier() {
        return 0x304030; // 暗绿色
    }
}
