package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFrostSteel extends BlockBase {
    public BlockFrostSteel() {
        super("frost_steel_block", Material.IRON);
        setHardness(5.0F);
        setResistance(30.0F);
        setHarvestLevel("pickaxe", 2);
        setSoundType(SoundType.METAL);
    }
}
