package com.qiamao.tinkerscrystex.blocks;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;

public class BlockVoidWeepingVines extends BlockVine {

    public BlockVoidWeepingVines() {
        super();
        this.setRegistryName("void_weeping_vines");
        this.setUnlocalizedName("tinkerscrystex.void_weeping_vines");
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0.2F);
        this.setLightLevel(0.4F); // 维持微光
        // 设置创造模式物品栏，由于没单独做 ItemBase，用原版逻辑需要用 ItemBlock 注册
        this.setCreativeTab(com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex.INSTANCE);
    }

}
