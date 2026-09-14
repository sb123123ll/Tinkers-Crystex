package com.qiamao.tinkerscrystex.blocks;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.creativetab.TabTinkersCrystex;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBase extends Block {
    private String tooltipKey;

    public BlockBase(String name, Material material) {
        super(material);
        setUnlocalizedName(TinkersCrystex.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(TabTinkersCrystex.INSTANCE);
        this.tooltipKey = "tooltip." + TinkersCrystex.MODID + "." + name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (I18n.hasKey(this.tooltipKey)) {
            String[] lines = I18n.format(this.tooltipKey).split("\\\\n");
            for (String line : lines) {
                tooltip.add(line);
            }
        }
    }
}
