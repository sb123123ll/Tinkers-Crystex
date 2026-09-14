package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.material.Material;

public class BlockAntimonyAlloy extends BlockBase {
    public BlockAntimonyAlloy() {
        super("antimony_alloy_block", Material.IRON);
        setHardness(6.0f);
        setResistance(25.0f);
        setHarvestLevel("pickaxe", 2); // 铁镐级别
    }
}
