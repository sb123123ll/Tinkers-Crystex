package com.qiamao.tinkerscrystex.world;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import com.qiamao.tinkerscrystex.init.ModBlocks;
import net.minecraft.init.Blocks;

import java.util.Random;

public class WorldGenRelicWood extends WorldGenAbstractTree {

    public WorldGenRelicWood() {
        super(false);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        // 树干高度 10~15格
        int height = 10 + rand.nextInt(6);
        
        // 确保有空间生成
        if (position.getY() >= 1 && position.getY() + height + 1 <= 256) {
            
            // 检查泥土
            IBlockState stateDown = world.getBlockState(position.down());
            if (stateDown.getBlock() != Blocks.GRASS && stateDown.getBlock() != Blocks.DIRT) {
                return false;
            }

            IBlockState logState = ModBlocks.RELIC_WOOD_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
            IBlockState knotState = ModBlocks.RELIC_KNOT.getDefaultState();
            
            // 1. 生成树干 (2x2)
            for (int y = 0; y < height; y++) {
                for (int x = 0; x <= 1; x++) {
                    for (int z = 0; z <= 1; z++) {
                        this.setBlockAndNotifyAdequately(world, position.add(x, y, z), logState);
                    }
                }
            }

            // 2. 生成板根
            for (int i = 0; i < 4; i++) {
                int rootLength = 2 + rand.nextInt(3);
                int dirX = (i == 0 || i == 1) ? 1 : -1;
                int dirZ = (i == 0 || i == 2) ? 1 : -1;
                
                int currentX = position.getX() + (dirX > 0 ? 1 : 0);
                int currentZ = position.getZ() + (dirZ > 0 ? 1 : 0);
                
                for (int len = 1; len <= rootLength; len++) {
                    int rX = currentX + (dirX * len);
                    int rZ = currentZ + (dirZ * len);
                    // 根部高度递减
                    int rootHeight = Math.max(1, 3 - len);
                    for (int rh = 0; rootHeight - rh > 0; rh++) {
                        BlockPos rootPos = new BlockPos(rX, position.getY() + rh, rZ);
                        this.setBlockAndNotifyAdequately(world, rootPos, logState);
                    }
                }
            }

            // 3. 生成树枝和树瘤
            // 从树干中间部分开始往上长树枝
            int branchCount = 3 + rand.nextInt(4);
            for (int i = 0; i < branchCount; i++) {
                int branchY = position.getY() + 4 + rand.nextInt(height - 4);
                int branchDir = rand.nextInt(4);
                
                int bX = position.getX() + (branchDir == 0 ? 2 : branchDir == 1 ? -1 : rand.nextInt(2));
                int bZ = position.getZ() + (branchDir == 2 ? 2 : branchDir == 3 ? -1 : rand.nextInt(2));
                
                int branchLength = 2 + rand.nextInt(3);
                
                BlockLog.EnumAxis axis = (branchDir < 2) ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z;
                IBlockState branchState = ModBlocks.RELIC_WOOD_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, axis);
                
                BlockPos currentBranchPos = new BlockPos(bX, branchY, bZ);
                for (int len = 0; len < branchLength; len++) {
                    this.setBlockAndNotifyAdequately(world, currentBranchPos, branchState);
                    
                    int mX = (branchDir == 0) ? 1 : (branchDir == 1) ? -1 : 0;
                    int mZ = (branchDir == 2) ? 1 : (branchDir == 3) ? -1 : 0;
                    currentBranchPos = currentBranchPos.add(mX, rand.nextInt(2), mZ);
                }
                
                // 末端挂 1-3 个树瘤
                int knotCount = 1 + rand.nextInt(3);
                for (int k = 0; k < knotCount; k++) {
                    BlockPos knotPos = currentBranchPos.add(rand.nextInt(3)-1, rand.nextInt(3)-1, rand.nextInt(3)-1);
                    if (world.isAirBlock(knotPos)) {
                        this.setBlockAndNotifyAdequately(world, knotPos, knotState);
                    }
                }
            }
            
            return true;
        }
        return false;
    }
}
