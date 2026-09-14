package com.qiamao.tinkerscrystex.init;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TinkersCrystex.MODID)
public class ModItems {
    public static final Item FROST_STEEL_INGOT = new ItemBase("frost_steel_ingot");
    public static final Item FROST_STEEL_NUGGET = new ItemBase("frost_steel_nugget");
    
    public static final Item ABYSSALITE_INGOT = new ItemBase("abyssalite_ingot");
    public static final Item ABYSSALITE_NUGGET = new ItemBase("abyssalite_nugget");
    
    public static final Item WOLFRAMITE_INGOT = new ItemBase("wolframite_ingot");
    public static final Item WOLFRAMITE_NUGGET = new ItemBase("wolframite_nugget");

    public static final Item PYROCLAST_CRYSTAL = new ItemBase("pyroclast_crystal");
    public static final Item PYROCLAST_CRYSTAL_NUGGET = new ItemBase("pyroclast_crystal_nugget");
    
    public static final Item THORNIRON_INGOT = new ItemBase("thorniron_ingot");
    public static final Item THORNIRON_NUGGET = new ItemBase("thorniron_nugget");

    public static final Item ANTIMONY_ALLOY_INGOT = new ItemBase("antimony_alloy_ingot");
    public static final Item ANTIMONY_ALLOY_NUGGET = new ItemBase("antimony_alloy_nugget");

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                FROST_STEEL_INGOT,
                FROST_STEEL_NUGGET,
                ABYSSALITE_INGOT,
                ABYSSALITE_NUGGET,
                WOLFRAMITE_INGOT,
                WOLFRAMITE_NUGGET,
                PYROCLAST_CRYSTAL,
                PYROCLAST_CRYSTAL_NUGGET,
                THORNIRON_INGOT,
                THORNIRON_NUGGET,
                ANTIMONY_ALLOY_INGOT,
                ANTIMONY_ALLOY_NUGGET
        );
    }
}
