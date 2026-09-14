package com.qiamao.tinkerscrystex.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.world.biome.BiomeSoulwoodHollow;

@Mod.EventBusSubscriber(modid = TinkersCrystex.MODID)
public class ModBiomes {
    
    public static final Biome SOULWOOD_HOLLOW = new BiomeSoulwoodHollow().setRegistryName("soulwood_hollow");

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(SOULWOOD_HOLLOW);
        
        // 注册群系类型字典
        BiomeDictionary.addTypes(SOULWOOD_HOLLOW, 
            BiomeDictionary.Type.FOREST, 
            BiomeDictionary.Type.MAGICAL, 
            BiomeDictionary.Type.SPOOKY); // 阴森的氛围
            
        // 权重5，添加到温和气候列表
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(SOULWOOD_HOLLOW, 5));
    }
}
