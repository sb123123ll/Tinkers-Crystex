package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPyroclastCrystal extends BlockBase {
    public BlockPyroclastCrystal() {
        super("pyroclast_crystal_block", Material.IRON);
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 2);
        setLightLevel(1.0F); // 15
    }
}
