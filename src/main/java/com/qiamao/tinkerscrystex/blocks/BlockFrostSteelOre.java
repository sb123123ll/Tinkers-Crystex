package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFrostSteelOre extends BlockBase {
    public BlockFrostSteelOre() {
        super("frost_steel_ore", Material.ROCK);
        setHardness(3.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 2); // 铁镐级别
        setSoundType(SoundType.STONE);
    }
}
