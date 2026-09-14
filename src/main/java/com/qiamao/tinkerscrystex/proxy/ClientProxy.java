package com.qiamao.tinkerscrystex.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.init.ModBlocks;
import com.qiamao.tinkerscrystex.init.ModItems;
import com.qiamao.tinkerscrystex.init.ModEntities;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersCrystex.MODID)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModEntities.registerModels();
        registerFluidModels();
    }
    
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    private static void registerFluidModels() {
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockFrostSteelFluid);
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockWolframiteFluid);
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAbyssaliteFluid);
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockPyroclastCrystalFluid);
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockThornironFluid);
        registerFluidModel(com.qiamao.tinkerscrystex.fluids.ModFluids.blockAntimonyAlloyFluid);
    }

    private static void registerFluidModel(Block fluidBlock) {
        if (fluidBlock != null) {
            Item item = Item.getItemFromBlock(fluidBlock);
            if (item != net.minecraft.init.Items.AIR) {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, stack -> new ModelResourceLocation(fluidBlock.getRegistryName(), "fluid"));
            }
            ModelLoader.setCustomStateMapper(fluidBlock, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // 物品模型
        registerModel(ModItems.FROST_STEEL_INGOT);
        registerModel(ModItems.FROST_STEEL_NUGGET);
        registerModel(ModItems.ABYSSALITE_INGOT);
        registerModel(ModItems.ABYSSALITE_NUGGET);
        registerModel(ModItems.WOLFRAMITE_INGOT);
        registerModel(ModItems.WOLFRAMITE_NUGGET);
        registerModel(ModItems.PYROCLAST_CRYSTAL);
        registerModel(ModItems.PYROCLAST_CRYSTAL_NUGGET);
        registerModel(ModItems.THORNIRON_INGOT);
        registerModel(ModItems.THORNIRON_NUGGET);
        registerModel(ModItems.ANTIMONY_ALLOY_INGOT);
        registerModel(ModItems.ANTIMONY_ALLOY_NUGGET);
        
        // 方块模型
        registerModel(Item.getItemFromBlock(ModBlocks.FROST_STEEL_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.FROST_STEEL_BLOCK));
        registerModel(Item.getItemFromBlock(ModBlocks.ABYSSALITE_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.ABYSSALITE_BLOCK));
        registerModel(Item.getItemFromBlock(ModBlocks.PYROCLAST_CRYSTAL_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.PYROCLAST_CRYSTAL_BLOCK));
        registerModel(Item.getItemFromBlock(ModBlocks.THORNIRON_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.THORNIRON_BLOCK));
        registerModel(Item.getItemFromBlock(ModBlocks.ANTIMONY_ALLOY_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.ANTIMONY_ALLOY_BLOCK));
        registerModel(Item.getItemFromBlock(ModBlocks.ZEOLITE));
        registerModel(Item.getItemFromBlock(ModBlocks.WOLFRAMITE_ORE));
        registerModel(Item.getItemFromBlock(ModBlocks.WOLFRAMITE_BLOCK));
        
        registerModel(Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_LOG));
        registerModel(Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_PLANKS));
        registerModel(Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_LEAVES));
        registerModel(Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_SAPLING));
        registerModel(Item.getItemFromBlock(ModBlocks.VOID_WEEPING_VINES));
        registerModel(Item.getItemFromBlock(ModBlocks.RELIC_KNOT));
        registerModel(Item.getItemFromBlock(ModBlocks.RELIC_WOOD_LOG));
        registerModel(Item.getItemFromBlock(ModBlocks.RELIC_WOOD_PLANKS));
    }
    
    private static void registerModel(Item item) {
        if (item != null && item.getRegistryName() != null) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
