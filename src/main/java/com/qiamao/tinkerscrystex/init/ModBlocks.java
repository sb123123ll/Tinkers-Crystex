package com.qiamao.tinkerscrystex.init;

import com.qiamao.tinkerscrystex.blocks.BlockFrostSteel;
import com.qiamao.tinkerscrystex.blocks.BlockFrostSteelOre;
import com.qiamao.tinkerscrystex.blocks.BlockAbyssalite;
import com.qiamao.tinkerscrystex.blocks.BlockAbyssaliteOre;
import com.qiamao.tinkerscrystex.blocks.BlockPyroclastCrystal;
import com.qiamao.tinkerscrystex.blocks.BlockPyroclastCrystalOre;
import com.qiamao.tinkerscrystex.blocks.BlockThorniron;
import com.qiamao.tinkerscrystex.blocks.BlockThornironOre;
import com.qiamao.tinkerscrystex.blocks.BlockAntimonyAlloy;
import com.qiamao.tinkerscrystex.blocks.BlockAntimonyAlloyOre;
import com.qiamao.tinkerscrystex.blocks.BlockAncientSpiritwoodLog;
import com.qiamao.tinkerscrystex.blocks.BlockAncientSpiritwoodPlanks;
import com.qiamao.tinkerscrystex.blocks.BlockAncientSpiritwoodSapling;
import com.qiamao.tinkerscrystex.blocks.BlockRelicWoodLog;
import com.qiamao.tinkerscrystex.blocks.BlockRelicWoodPlanks;
import com.qiamao.tinkerscrystex.blocks.BlockRelicKnot;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.qiamao.tinkerscrystex.blocks.BlockZeolite;
import com.qiamao.tinkerscrystex.blocks.BlockWolframite;
import com.qiamao.tinkerscrystex.blocks.BlockWolframiteOre;
import com.qiamao.tinkerscrystex.blocks.BlockAncientSpiritwoodLeaves;
import com.qiamao.tinkerscrystex.blocks.BlockVoidWeepingVines;

import com.qiamao.tinkerscrystex.TinkersCrystex;

@Mod.EventBusSubscriber(modid = TinkersCrystex.MODID)
public class ModBlocks {
    public static final Block FROST_STEEL_ORE = new BlockFrostSteelOre();
    public static final Block ZEOLITE = new BlockZeolite("zeolite");
    public static final Block WOLFRAMITE_ORE = new BlockWolframiteOre();
    public static final Block WOLFRAMITE_BLOCK = new BlockWolframite();
    public static final Block FROST_STEEL_BLOCK = new BlockFrostSteel();
    
    public static final Block ABYSSALITE_ORE = new BlockAbyssaliteOre();
    public static final Block ABYSSALITE_BLOCK = new BlockAbyssalite();
    public static final Block PYROCLAST_CRYSTAL_ORE = new BlockPyroclastCrystalOre();
    public static final Block PYROCLAST_CRYSTAL_BLOCK = new BlockPyroclastCrystal();

    public static final Block THORNIRON_ORE = new BlockThornironOre();
    public static final Block THORNIRON_BLOCK = new BlockThorniron();
    public static final Block ANTIMONY_ALLOY_ORE = new BlockAntimonyAlloyOre();
    public static final Block ANTIMONY_ALLOY_BLOCK = new BlockAntimonyAlloy();
    
    public static final Block ANCIENT_SPIRITWOOD_LOG = new BlockAncientSpiritwoodLog();
    public static final Block ANCIENT_SPIRITWOOD_PLANKS = new BlockAncientSpiritwoodPlanks();
    public static final Block ANCIENT_SPIRITWOOD_LEAVES = new BlockAncientSpiritwoodLeaves();
    public static final Block ANCIENT_SPIRITWOOD_SAPLING = new com.qiamao.tinkerscrystex.blocks.BlockAncientSpiritwoodSapling();
    public static final Block VOID_WEEPING_VINES = new BlockVoidWeepingVines();
    public static final Block RELIC_KNOT = new BlockRelicKnot();
    public static final Block RELIC_WOOD_LOG = new BlockRelicWoodLog();
    public static final Block RELIC_WOOD_PLANKS = new BlockRelicWoodPlanks();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            FROST_STEEL_ORE,
            FROST_STEEL_BLOCK,
            ABYSSALITE_ORE,
            ABYSSALITE_BLOCK,
            PYROCLAST_CRYSTAL_ORE,
            PYROCLAST_CRYSTAL_BLOCK,
            THORNIRON_ORE,
            THORNIRON_BLOCK,
            ANTIMONY_ALLOY_ORE,
            ANTIMONY_ALLOY_BLOCK,
            ANCIENT_SPIRITWOOD_LOG,
            ANCIENT_SPIRITWOOD_PLANKS,
            ANCIENT_SPIRITWOOD_LEAVES,
            ANCIENT_SPIRITWOOD_SAPLING,
            VOID_WEEPING_VINES,
            RELIC_KNOT,
            RELIC_WOOD_LOG,
            RELIC_WOOD_PLANKS,
            ZEOLITE,
            WOLFRAMITE_ORE,
            WOLFRAMITE_BLOCK,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockFrostSteelFluid,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockWolframiteFluid,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockAbyssaliteFluid,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockPyroclastCrystalFluid,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockThornironFluid,
            com.qiamao.tinkerscrystex.fluids.ModFluids.blockAntimonyAlloyFluid
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new ItemBlock(FROST_STEEL_ORE).setRegistryName(FROST_STEEL_ORE.getRegistryName()),
            new ItemBlock(FROST_STEEL_BLOCK).setRegistryName(FROST_STEEL_BLOCK.getRegistryName()),
            new ItemBlock(ABYSSALITE_ORE).setRegistryName(ABYSSALITE_ORE.getRegistryName()),
            new ItemBlock(ABYSSALITE_BLOCK).setRegistryName(ABYSSALITE_BLOCK.getRegistryName()),
            new ItemBlock(PYROCLAST_CRYSTAL_ORE).setRegistryName(PYROCLAST_CRYSTAL_ORE.getRegistryName()),
            new ItemBlock(PYROCLAST_CRYSTAL_BLOCK).setRegistryName(PYROCLAST_CRYSTAL_BLOCK.getRegistryName()),
            new ItemBlock(THORNIRON_ORE).setRegistryName(THORNIRON_ORE.getRegistryName()),
            new ItemBlock(THORNIRON_BLOCK).setRegistryName(THORNIRON_BLOCK.getRegistryName()),
            new ItemBlock(ANTIMONY_ALLOY_ORE).setRegistryName(ANTIMONY_ALLOY_ORE.getRegistryName()),
            new ItemBlock(ANTIMONY_ALLOY_BLOCK).setRegistryName(ANTIMONY_ALLOY_BLOCK.getRegistryName()),
            new ItemBlock(ANCIENT_SPIRITWOOD_LOG).setRegistryName(ANCIENT_SPIRITWOOD_LOG.getRegistryName()),
            new ItemBlock(ANCIENT_SPIRITWOOD_PLANKS).setRegistryName(ANCIENT_SPIRITWOOD_PLANKS.getRegistryName()),
            new ItemBlock(ANCIENT_SPIRITWOOD_LEAVES).setRegistryName(ANCIENT_SPIRITWOOD_LEAVES.getRegistryName()),
            new ItemBlock(ANCIENT_SPIRITWOOD_SAPLING).setRegistryName(ANCIENT_SPIRITWOOD_SAPLING.getRegistryName()),
            new ItemBlock(VOID_WEEPING_VINES).setRegistryName(VOID_WEEPING_VINES.getRegistryName()),
            new ItemBlock(RELIC_KNOT).setRegistryName(RELIC_KNOT.getRegistryName()),
            new ItemBlock(RELIC_WOOD_LOG).setRegistryName(RELIC_WOOD_LOG.getRegistryName()),
            new ItemBlock(RELIC_WOOD_PLANKS).setRegistryName(RELIC_WOOD_PLANKS.getRegistryName()),
            new ItemBlock(ZEOLITE).setRegistryName(ZEOLITE.getRegistryName()),
            new ItemBlock(WOLFRAMITE_ORE).setRegistryName(WOLFRAMITE_ORE.getRegistryName()),
            new ItemBlock(WOLFRAMITE_BLOCK).setRegistryName(WOLFRAMITE_BLOCK.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockFrostSteelFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockFrostSteelFluid.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockWolframiteFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockWolframiteFluid.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAbyssaliteFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAbyssaliteFluid.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockPyroclastCrystalFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockPyroclastCrystalFluid.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockThornironFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockThornironFluid.getRegistryName()),
            new ItemBlock(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAntimonyAlloyFluid).setRegistryName(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAntimonyAlloyFluid.getRegistryName())
        );
    }
}
