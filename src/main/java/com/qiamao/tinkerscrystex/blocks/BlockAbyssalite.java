package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAbyssalite extends BlockBase {
    public BlockAbyssalite() {
        super("abyssalite_block", Material.IRON);
        setHardness(6.0f);
        setResistance(40.0f);
        setHarvestLevel("pickaxe", 3); // 钻石级
        setSoundType(SoundType.METAL);
    }
}