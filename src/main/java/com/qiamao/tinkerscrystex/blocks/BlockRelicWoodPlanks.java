package com.qiamao.tinkerscrystex.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex;

public class BlockRelicWoodPlanks extends BlockBase {
    public BlockRelicWoodPlanks() {
        super("relic_wood_planks", Material.WOOD);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(TabTinkersCrystex.TINKERS_CRYSTEX_TAB);
    }
}
