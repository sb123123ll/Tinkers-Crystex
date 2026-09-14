package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockAncientSpiritwoodLeaves extends BlockLeaves {

    public BlockAncientSpiritwoodLeaves() {
        super();
        this.setRegistryName("ancient_spiritwood_leaves");
        this.setUnlocalizedName("tinkerscrystex.ancient_spiritwood_leaves");
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{CHECK_DECAY, DECAYABLE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!((Boolean) state.getValue(DECAYABLE)).booleanValue()) {
            i |= 4;
        }
        if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue()) {
            i |= 8;
        }
        return i;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK; // 只是为了实现抽象方法，我们这个树叶不使用原版类型
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return java.util.Arrays.asList(new ItemStack(this));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // 由于没有单独注册树苗的物品实例，此处暂掉落原木或者未来可以改为掉落树苗
        return Item.getItemFromBlock(com.qiamao.tinkerscrystex.init.ModBlocks.ANCIENT_SPIRITWOOD_LOG);
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (rand.nextInt(20) == 0) { // 5% 概率掉落原木(或树苗)
            drops.add(new ItemStack(this.getItemDropped(state, rand, fortune), 1, this.damageDropped(state)));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        // 让树叶有透明像素镂空
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true; // 强制渲染所有面，避免由于相邻树叶导致内部面消失
    }
}
