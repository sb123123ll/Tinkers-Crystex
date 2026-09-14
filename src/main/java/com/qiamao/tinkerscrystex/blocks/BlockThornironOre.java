package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockThornironOre extends BlockBase {
    public BlockThornironOre() {
        super("thorniron_ore", Material.ROCK);
        setHardness(3.0f); 
        setResistance(15.0f);
        setHarvestLevel("pickaxe", 1); // 石头级即可挖掘 (铁矿级别)
        setSoundType(SoundType.STONE);
    }
}
