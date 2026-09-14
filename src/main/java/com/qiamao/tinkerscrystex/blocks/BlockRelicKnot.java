package com.qiamao.tinkerscrystex.blocks;

import com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRelicKnot extends BlockBase {
    public BlockRelicKnot() {
        super("relic_knot", Material.WOOD);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(TabTinkersCrystex.TINKERS_CRYSTEX_TAB);
    }
}
