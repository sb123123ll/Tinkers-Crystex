package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex;

public class BlockRelicWoodLog extends BlockLog {
    public BlockRelicWoodLog() {
        this.setRegistryName("relic_wood_log");
        this.setUnlocalizedName("relic_wood_log");
        this.setCreativeTab(TabTinkersCrystex.TINKERS_CRYSTEX_TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        
        // 注册到列表便于自动化处理，前提是项目有相应的机制，或者手动添加
        // 这里为了兼容性直接通过 ModBlocks 控制
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        switch (meta & 12) {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        switch (state.getValue(LOG_AXIS)) {
            case X:
                meta |= 4;
                break;
            case Z:
                meta |= 8;
                break;
            case NONE:
                meta |= 12;
                break;
            case Y:
            default:
                meta |= 0;
        }
        return meta;
    }
}
