package com.qiamao.tinkerscrystex.blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
public class BlockAncientSpiritwoodLog extends BlockBase {
    public BlockAncientSpiritwoodLog() {
        // 硬度由 2.0F 翻倍为 4.0F
        super("ancient_spiritwood_log", Material.WOOD);
        this.setHardness(4.0F);
        this.setResistance(10.0F);
        setSoundType(SoundType.WOOD);
        // 收割等级设为 1（石质或以上级别才能有效挖掘）
        setHarvestLevel("axe", 1);
    }
}
