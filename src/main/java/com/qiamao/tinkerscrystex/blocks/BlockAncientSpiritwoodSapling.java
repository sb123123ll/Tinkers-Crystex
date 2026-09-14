package com.qiamao.tinkerscrystex.blocks;

import com.qiamao.tinkerscrystex.world.ModWorldGen;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAncientSpiritwoodSapling extends BlockBush implements IGrowable {

    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockAncientSpiritwoodSapling() {
        this.setRegistryName("ancient_spiritwood_sapling");
        this.setUnlocalizedName("tinkerscrystex.ancient_spiritwood_sapling");
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex.INSTANCE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    /**
     * 约束：只能放置在末地石 (END_STONE) 上
     */
    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.END_STONE;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && canSustainBush(soil);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return; // 避免在边界生成时崩溃
            
            // 每次随机刻有几率自然生长
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        
        // 生成前先清空树苗
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
        
        // 调用我们刚才完善好的 ModWorldGen 中的 generateTree() 逻辑
        // 因为这是私有方法，我们需要稍微反射或者通过 public 暴露，这里我们采用直接移植逻辑或公共调用的方式
        // 为了方便，我在下面写一个基于相同逻辑的生成，或者你之后把 ModWorldGen 的方法设为 public 并在里面调用
        // 【注】当前我们直接写在这里保证独立功能可用
        int height = 5 + rand.nextInt(4);
        boolean canGrow = true;

        for (int y = pos.getY(); y <= pos.getY() + 1 + height; y++) {
            if (y < 0 || y >= 256) { canGrow = false; break; }
            for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
                for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    if (!worldIn.isAirBlock(checkPos) && !worldIn.getBlockState(checkPos).getBlock().isLeaves(worldIn.getBlockState(checkPos), worldIn, checkPos)) {
                        canGrow = false;
                        break;
                    }
                }
            }
        }

        if (canGrow) {
            // 调用 ModWorldGen 里的树生成
            new ModWorldGen().generateSpiritwoodTreePublic(worldIn, rand, pos);
        } else {
            // 空间不足，放回树苗
            worldIn.setBlockState(pos, state, 4);
        }
    }

    // --- IGrowable 接口实现 (骨粉支持) ---
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state, rand);
    }

    // --- State 设置 ---
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE) << 3;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }
}
