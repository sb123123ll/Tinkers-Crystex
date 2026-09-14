package com.qiamao.tinkerscrystex.world;

import com.qiamao.tinkerscrystex.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator {
    private WorldGenerator frostSteelOre;
    private WorldGenerator abyssaliteOre;
    private WorldGenerator pyroclastCrystalOre;
    private WorldGenerator thornironOre;
    private WorldGenerator antimonyAlloyOre;
    private WorldGenerator wolframiteOre;

    public ModWorldGen() {
        // 霜钢矿脉大小 4~6
        frostSteelOre = new WorldGenMinable(ModBlocks.FROST_STEEL_ORE.getDefaultState(), 5);
        
        // 深渊幽矿矿脉大小 3~5
        abyssaliteOre = new WorldGenMinable(ModBlocks.ABYSSALITE_ORE.getDefaultState(), 4);
        
        // 烬晶矿脉大小 4~5 (下界生成) 
        pyroclastCrystalOre = new WorldGenMinable(ModBlocks.PYROCLAST_CRYSTAL_ORE.getDefaultState(), 5, BlockMatcher.forBlock(Blocks.NETHERRACK));
        
        // 荆棘生铁矿脉大小 5~7
        // 荆棘生铁 (Thorniron) 生成，替换安山岩、花岗岩和闪长岩
        Predicate<IBlockState> stoneVariantMatcher = new Predicate<IBlockState>() {
            @Override
            public boolean apply(IBlockState input) {
                if (input != null && input.getBlock() == Blocks.STONE) {
                    BlockStone.EnumType type = input.getValue(BlockStone.VARIANT);
                    return type == BlockStone.EnumType.ANDESITE || 
                           type == BlockStone.EnumType.GRANITE || 
                           type == BlockStone.EnumType.DIORITE;
                }
                return false;
            }
        };
        thornironOre = new WorldGenMinable(ModBlocks.THORNIRON_ORE.getDefaultState(), 6, stoneVariantMatcher);
        antimonyAlloyOre = new WorldGenMinable(ModBlocks.ANTIMONY_ALLOY_ORE.getDefaultState(), 5);
        wolframiteOre = new WorldGenMinable(ModBlocks.WOLFRAMITE_ORE.getDefaultState(), 3); // 黑钨矿每簇最多3个
    }

    private void generateAncientSpiritwood(World world, Random random, int chunkX, int chunkZ) {
        // 末地生成
        if (world.provider.getDimension() == 1) {
            // 每个区块 15% 的概率尝试生成
            if (random.nextFloat() < 0.15f) {
                // 尝试生成 1 到 2 次
                int chances = 1 + random.nextInt(2);
                for (int i = 0; i < chances; i++) {
                    int x = chunkX * 16 + random.nextInt(16);
                    int z = chunkZ * 16 + random.nextInt(16);
                    int y = getSurfaceY(world, x, z);
                    
                    // 放宽Y轴限制以适应末地中心岛屿和外岛 (Y > 40 通常是末地岛屿的表面)
                    if (y > 40 && y < 100) {
                        if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == net.minecraft.init.Blocks.END_STONE) {
                            generateTree(world, random, new BlockPos(x, y, z));
                        }
                    }
                }
            }
        } 
    }
    
    private int getSurfaceY(World world, int x, int z) {
        int y = 255;
        while (y > 0 && world.isAirBlock(new BlockPos(x, y, z))) {
            y--;
        }
        return y + 1;
    }
    
    public void generateSpiritwoodTreePublic(World world, Random random, BlockPos pos) {
        generateTree(world, random, pos);
    }

    private void generateTree(World world, Random random, BlockPos pos) {
        int height = 5 + random.nextInt(4); // 树干高度 5-8格高
        
        // 生成树干
        for (int i = 0; i < height; i++) {
            world.setBlockState(pos.up(i), ModBlocks.ANCIENT_SPIRITWOOD_LOG.getDefaultState());
        }
        
        // 生成顶端树叶 (球形/伞形树冠)
        IBlockState leaves = ModBlocks.ANCIENT_SPIRITWOOD_LEAVES.getDefaultState();
        int leafStart = height - 3; // 树叶从距离顶部3格的地方开始长
        
        for (int y = pos.getY() + leafStart; y <= pos.getY() + height + 1; ++y) {
            int radius = y - (pos.getY() + height);
            int leafRadius = 1 - radius / 2; // 控制不同高度的树叶范围，下大上小
            
            // 最底部和最顶部的树叶范围缩小
            if (y == pos.getY() + leafStart || y == pos.getY() + height + 1) {
                leafRadius = 1;
            } else {
                leafRadius = 2; // 中间部分半径为2
            }

            for (int x = pos.getX() - leafRadius; x <= pos.getX() + leafRadius; ++x) {
                int xOffset = x - pos.getX();

                for (int z = pos.getZ() - leafRadius; z <= pos.getZ() + leafRadius; ++z) {
                    int zOffset = z - pos.getZ();

                    // 去掉四个边角，让树叶看起来更圆润
                    if (Math.abs(xOffset) == leafRadius && Math.abs(zOffset) == leafRadius && (random.nextInt(2) == 0 || radius == 0)) {
                        continue;
                    }

                    BlockPos leafPos = new BlockPos(x, y, z);
                    // 只替换空气或者其他可以被树叶覆盖的方块
                    if (world.isAirBlock(leafPos) || world.getBlockState(leafPos).getBlock().isLeaves(world.getBlockState(leafPos), world, leafPos)) {
                        world.setBlockState(leafPos, leaves, 2);
                    }
                }
            }
        }
        
        // 在外侧树叶的侧面生成虚空垂藤 (Void Weeping Vines)
        // 采用原版沼泽树的机制：紧贴树叶侧面往下生长
        for (int y = pos.getY() + leafStart - 1; y <= pos.getY() + height; ++y) {
            for (int x = pos.getX() - 3; x <= pos.getX() + 3; ++x) {
                for (int z = pos.getZ() - 3; z <= pos.getZ() + 3; ++z) {
                    BlockPos leafPos = new BlockPos(x, y, z);

                    // 只有当这个位置是树叶，才会尝试在其侧面挂藤蔓
                    if (world.getBlockState(leafPos).getBlock() == ModBlocks.ANCIENT_SPIRITWOOD_LEAVES) {
                        // 随机判断是否在这个叶子方块的东、西、南、北挂藤蔓 (概率与沼泽树一致，约 1/6)
                        if (random.nextInt(6) == 0 && world.isAirBlock(leafPos.west())) {
                            addVines(world, leafPos.west(), net.minecraft.block.BlockVine.EAST);
                        }
                        if (random.nextInt(6) == 0 && world.isAirBlock(leafPos.east())) {
                            addVines(world, leafPos.east(), net.minecraft.block.BlockVine.WEST);
                        }
                        if (random.nextInt(6) == 0 && world.isAirBlock(leafPos.north())) {
                            addVines(world, leafPos.north(), net.minecraft.block.BlockVine.SOUTH);
                        }
                        if (random.nextInt(6) == 0 && world.isAirBlock(leafPos.south())) {
                            addVines(world, leafPos.south(), net.minecraft.block.BlockVine.NORTH);
                        }
                    }
                }
            }
        }
    }

    /**
     * 辅助方法：添加藤蔓并向下延伸
     */
    private void addVines(World world, BlockPos pos, net.minecraft.block.properties.PropertyBool prop) {
        IBlockState vineState = ModBlocks.VOID_WEEPING_VINES.getDefaultState().withProperty(prop, true);
        world.setBlockState(pos, vineState, 2);
        
        // 自然向下延伸
        int i = 4; // 最长往下延伸4格
        for (BlockPos blockpos = pos.down(); world.isAirBlock(blockpos) && i > 0; --i) {
            world.setBlockState(blockpos, vineState, 2);
            blockpos = blockpos.down();
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateAncientSpiritwood(world, random, chunkX, chunkZ);
        if (world.provider.getDimension() == 0) { // 主世界
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator);
        } else if (world.provider.getDimension() == -1) { // 下界
            generateNether(random, chunkX, chunkZ, world);
        }
    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world) {
        // 烬晶: 下界生成
        // 刷新概率设置为下界石英矿石刷新率(通常每个区块16簇)的0.25倍 -> 每个区块生成 4 次
        int chances = 4;
        for (int i = 0; i < chances; i++) {
            int genX = chunkX * 16 + random.nextInt(16);
            // Y=10 ~ 108
            int genY = 10 + random.nextInt(99);
            int genZ = chunkZ * 16 + random.nextInt(16);
            pyroclastCrystalOre.generate(world, random, new BlockPos(genX, genY, genZ));
        }
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator) {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        BlockPos pos = new BlockPos(x, 0, z);
        Biome biome = world.getBiome(pos);

        // 获取群系的注册名，作为兼容性后备判断
        String biomeName = "";
        if (biome.getRegistryName() != null) {
            biomeName = biome.getRegistryName().getResourcePath().toLowerCase();
        }

        // 霜钢: 寒冷群系
        boolean isColdBiome = BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD) || 
                              BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY) ||
                              biomeName.contains("icy") || biomeName.contains("tundra") || 
                              biomeName.contains("frost") || biomeName.contains("glacier") ||
                              biomeName.contains("snow") || biomeName.contains("cold");

        if (isColdBiome) {
            // 每区块 2~4 次尝试
            int chances = 2 + random.nextInt(3);
            for (int i = 0; i < chances; i++) {
                int genX = chunkX * 16 + random.nextInt(16);
                // Y=10 ~ 40
                int genY = 10 + random.nextInt(31);
                int genZ = chunkZ * 16 + random.nextInt(16);
                frostSteelOre.generate(world, random, new BlockPos(genX, genY, genZ));
            }
        }
        
        // 深渊幽矿: 海洋群系
        boolean isWaterBiome = BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) || 
                               BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER) ||
                               biomeName.contains("ocean") || biomeName.contains("lake") || 
                               biomeName.contains("abyss") || biomeName.contains("water");

        if (isWaterBiome) {
            // 每区块 3~5 次尝试
            int chances = 3 + random.nextInt(3);
            for (int i = 0; i < chances; i++) {
                int genX = chunkX * 16 + random.nextInt(16);
                // Y=20 ~ 45
                int genY = 20 + random.nextInt(26);
                int genZ = chunkZ * 16 + random.nextInt(16);
                abyssaliteOre.generate(world, random, new BlockPos(genX, genY, genZ));
            }
        }

        // 荆棘生铁: 废弃矿井机制及植被群系生成
        boolean isNearMineshaft = false;
        if (chunkGenerator != null) {
            isNearMineshaft = chunkGenerator.isInsideStructure(world, "Mineshaft", pos);
        }
        
        boolean isJungleOrForest = BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE) || 
                                   BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || 
                                   BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP) ||
                                   biomeName.contains("jungle") || biomeName.contains("forest") ||
                                   biomeName.contains("wood") || biomeName.contains("swamp") ||
                                   biomeName.contains("grove") || biomeName.contains("thicket") ||
                                   biomeName.contains("marsh") || biomeName.contains("bayou");

        if (isNearMineshaft) {
            // 在废弃矿井内部及附近: 尝试生成 5~7 次
            int chances = 5 + random.nextInt(3);
            for (int i = 0; i < chances; i++) {
                int genX = chunkX * 16 + random.nextInt(16);
                // 废弃矿井通常在较低的Y轴，这里设置在 Y=10 ~ 45
                int genY = 10 + random.nextInt(36);
                int genZ = chunkZ * 16 + random.nextInt(16);
                thornironOre.generate(world, random, new BlockPos(genX, genY, genZ));
            }
        } else if (isJungleOrForest) {
            // 植被群系: 每区块 4~6 次尝试
            int chances = 4 + random.nextInt(3);
            for (int i = 0; i < chances; i++) {
                int genX = chunkX * 16 + random.nextInt(16);
                // Y=15 ~ 55
                int genY = 15 + random.nextInt(41);
                int genZ = chunkZ * 16 + random.nextInt(16);
                thornironOre.generate(world, random, new BlockPos(genX, genY, genZ));
            }
        } else {
             // 其他群系: 每区块 2 次尝试
            int chances = 2;
            for (int i = 0; i < chances; i++) {
                int genX = chunkX * 16 + random.nextInt(16);
                // Y=15 ~ 55
                int genY = 15 + random.nextInt(41);
                int genZ = chunkZ * 16 + random.nextInt(16);
                thornironOre.generate(world, random, new BlockPos(genX, genY, genZ));
            }
        }

        // 辉锑矿 (锑合金矿石) 生成：高度 0-30，生成频率 3次/区块
        runGenerator(antimonyAlloyOre, world, random, chunkX, chunkZ, 3, 0, 30);
        
        // 黑钨矿生成：高度 5-24，生成频率 2次/区块 (比钻石低)
        runGenerator(wolframiteOre, world, random, chunkX, chunkZ, 2, 5, 24);
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chances, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        for (int i = 0; i < chances; i++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(maxHeight - minHeight + 1);
            int z = chunkZ * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }
}
