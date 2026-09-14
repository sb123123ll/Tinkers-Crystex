package com.qiamao.tinkerscrystex.creativetab;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabTinkersCrystex extends CreativeTabs {
    public static final TabTinkersCrystex TINKERS_CRYSTEX_TAB = new TabTinkersCrystex();
    public static final TabTinkersCrystex INSTANCE = TINKERS_CRYSTEX_TAB;

    public TabTinkersCrystex() {
        super(TinkersCrystex.MODID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.FROST_STEEL_INGOT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(net.minecraft.util.NonNullList<ItemStack> p_78018_1_) {
        super.displayAllRelevantItems(p_78018_1_);
    }
}
