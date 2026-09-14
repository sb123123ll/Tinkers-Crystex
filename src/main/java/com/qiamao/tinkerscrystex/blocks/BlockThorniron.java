package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockThorniron extends BlockBase {
    public BlockThorniron() {
        super("thorniron_block", Material.IRON);
        setHardness(5.0f);
        setResistance(30.0f);
        setHarvestLevel("pickaxe", 1);
        setSoundType(SoundType.METAL);
    }
}
