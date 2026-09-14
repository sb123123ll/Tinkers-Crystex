package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.material.Material;

public class BlockZeolite extends BlockBase {

    public BlockZeolite(String name) {
        super(name, Material.IRON);
        setHardness(5.0f);
        setResistance(10.0f);
        setHarvestLevel("pickaxe", 1);
    }
}
