package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWolframiteOre extends BlockBase {
    public BlockWolframiteOre() {
        super("wolframite_ore", Material.ROCK);
        setHardness(15.0f);
        setResistance(800.0f);
        setHarvestLevel("pickaxe", 3); // 3级镐子才能挖掘
    }

    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        return MathHelper.getInt(rand, 5, 10);
    }
}
