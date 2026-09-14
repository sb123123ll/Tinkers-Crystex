package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.material.Material;

public class BlockWolframite extends BlockBase {
    public BlockWolframite() {
        super("wolframite_block", Material.IRON);
        setHardness(25.0f);
        setResistance(1200.0f); // 极高防爆
        setHarvestLevel("pickaxe", 3); // 3级镐子(钴/黑曜石)
    }
}
