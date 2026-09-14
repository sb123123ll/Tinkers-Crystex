package com.qiamao.tinkerscrystex.blocks;
import net.minecraft.block.material.Material;
public class BlockAntimonyAlloyOre extends BlockBase {
    public BlockAntimonyAlloyOre() {
        super("antimony_alloy_ore", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setHarvestLevel("pickaxe", 2);
    }
}
