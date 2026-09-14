package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPyroclastCrystalOre extends BlockBase {
    public BlockPyroclastCrystalOre() {
        super("pyroclast_crystal_ore", Material.ROCK);
        setHardness(3.0f); // 类似下界石英的硬度，稍微硬一点
        setResistance(15.0f);
        setHarvestLevel("pickaxe", 2); // 铁级
        setSoundType(SoundType.STONE);
        setLightLevel(6.0f / 15.0f); // 亮度6
    }
}
