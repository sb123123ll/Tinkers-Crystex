package com.qiamao.tinkerscrystex;

import com.qiamao.tinkerscrystex.fluids.ModFluids;
import com.qiamao.tinkerscrystex.init.ModBlocks;
import com.qiamao.tinkerscrystex.init.ModItems;
import com.qiamao.tinkerscrystex.materials.ModMaterials;
import com.qiamao.tinkerscrystex.network.ModNetwork;
import com.qiamao.tinkerscrystex.world.ModWorldGen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.item.Item;
import com.qiamao.tinkerscrystex.proxy.CommonProxy;

@Mod(modid = TinkersCrystex.MODID, name = TinkersCrystex.NAME, version = TinkersCrystex.VERSION, dependencies = "required-after:mantle;required-after:tconstruct")
public class TinkersCrystex
{
    public static final String MODID = "tinkerscrystex";
    public static final String NAME = "Tinkers Crystex";
    public static final String VERSION = "beta-0.1.0";

    @Mod.Instance(TinkersCrystex.MODID)
    public static TinkersCrystex instance;

    @SidedProxy(clientSide = "com.qiamao.tinkerscrystex.proxy.ClientProxy", serverSide = "com.qiamao.tinkerscrystex.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static org.apache.logging.log4j.Logger logger;

    static {
        // 允许 Forge 注入流体
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        
        // 注册流体
        ModFluids.registerFluids();
        
        // 初始化材料（在 preInit 注册到匠魂）
        ModMaterials.setupMaterials();
        proxy.preInit(event);
        ModNetwork.init(); // 注册网络通信
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Tinkers Crystex Mod Initialized");
        
        // 注册世界生成
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        
        proxy.init(event);
        
        // ================== 荆棘生铁 (Thorniron) ==================
        // 原版熔炉烧炼矿石 -> 锭
        GameRegistry.addSmelting(ModBlocks.THORNIRON_ORE, new ItemStack(ModItems.THORNIRON_INGOT), 1.0f);
        // 冶炼
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.THORNIRON_ORE), ModFluids.thornironFluid, 288);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.THORNIRON_INGOT), ModFluids.thornironFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.THORNIRON_NUGGET), ModFluids.thornironFluid, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.THORNIRON_BLOCK), ModFluids.thornironFluid, 1296);

        // 浇筑
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.THORNIRON_INGOT), TinkerSmeltery.castIngot, ModFluids.thornironFluid, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.THORNIRON_NUGGET), TinkerSmeltery.castNugget, ModFluids.thornironFluid, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.THORNIRON_BLOCK), ItemStack.EMPTY, ModFluids.thornironFluid, 1296);

        // 注册矿物词典
        OreDictionary.registerOre("oreFrostSteel", ModBlocks.FROST_STEEL_ORE);
        OreDictionary.registerOre("ingotFrostSteel", ModItems.FROST_STEEL_INGOT);
        OreDictionary.registerOre("nuggetFrostSteel", ModItems.FROST_STEEL_NUGGET);
        OreDictionary.registerOre("blockFrostSteel", ModBlocks.FROST_STEEL_BLOCK);
        
        OreDictionary.registerOre("oreAbyssalite", ModBlocks.ABYSSALITE_ORE);
        OreDictionary.registerOre("ingotAbyssalite", ModItems.ABYSSALITE_INGOT);
        OreDictionary.registerOre("nuggetAbyssalite", ModItems.ABYSSALITE_NUGGET);
        OreDictionary.registerOre("blockAbyssalite", ModBlocks.ABYSSALITE_BLOCK);

        // ================== 霜钢 (Frost Steel) ==================
        // 原版熔炉烧炼矿石 -> 锭
        GameRegistry.addSmelting(ModBlocks.FROST_STEEL_ORE, new ItemStack(ModItems.FROST_STEEL_INGOT), 1.0f);
        // 注册匠魂冶炼配方
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.FROST_STEEL_ORE), ModFluids.fluidFrostSteel, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.FROST_STEEL_INGOT), ModFluids.fluidFrostSteel, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.FROST_STEEL_NUGGET), ModFluids.fluidFrostSteel, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.FROST_STEEL_BLOCK), ModFluids.fluidFrostSteel, 1296);

        // 注册匠魂浇筑配方
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.FROST_STEEL_INGOT), TinkerSmeltery.castIngot, ModFluids.fluidFrostSteel, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.FROST_STEEL_NUGGET), TinkerSmeltery.castNugget, ModFluids.fluidFrostSteel, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.FROST_STEEL_BLOCK), ItemStack.EMPTY, ModFluids.fluidFrostSteel, 1296);

        // 黑钨矿熔炼配方
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.WOLFRAMITE_ORE), ModFluids.fluidWolframite, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.WOLFRAMITE_INGOT), ModFluids.fluidWolframite, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.WOLFRAMITE_NUGGET), ModFluids.fluidWolframite, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.WOLFRAMITE_BLOCK), ModFluids.fluidWolframite, 1296);

        // 黑钨矿浇筑配方
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.WOLFRAMITE_INGOT), TinkerSmeltery.castIngot, ModFluids.fluidWolframite, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.WOLFRAMITE_NUGGET), TinkerSmeltery.castNugget, ModFluids.fluidWolframite, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.WOLFRAMITE_BLOCK), ItemStack.EMPTY, ModFluids.fluidWolframite, 1296);
        
        // ================== 深渊幽矿 (Abyssalite) ==================
        // 原版熔炉烧炼矿石 -> 锭
        GameRegistry.addSmelting(ModBlocks.ABYSSALITE_ORE, new ItemStack(ModItems.ABYSSALITE_INGOT), 1.0f);
        // 冶炼 (产量翻倍: 1矿石 -> 2锭, 288 mb)
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.ABYSSALITE_ORE), ModFluids.abyssaliteFluid, 288);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.ABYSSALITE_INGOT), ModFluids.abyssaliteFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.ABYSSALITE_NUGGET), ModFluids.abyssaliteFluid, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.ABYSSALITE_BLOCK), ModFluids.abyssaliteFluid, 1296);
        
        // 浇筑
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.ABYSSALITE_INGOT), TinkerSmeltery.castIngot, ModFluids.abyssaliteFluid, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.ABYSSALITE_NUGGET), TinkerSmeltery.castNugget, ModFluids.abyssaliteFluid, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.ABYSSALITE_BLOCK), ItemStack.EMPTY, ModFluids.abyssaliteFluid, 1296);

        // ================== 烬晶 (Pyroclast Crystal) ==================
        // 原版熔炉烧炼矿石 -> 晶体
        GameRegistry.addSmelting(ModBlocks.PYROCLAST_CRYSTAL_ORE, new ItemStack(ModItems.PYROCLAST_CRYSTAL), 1.0f);
        // 冶炼 (产量翻倍: 1矿石 -> 2锭, 288 mb)
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.PYROCLAST_CRYSTAL_ORE), ModFluids.pyroclastCrystalFluid, 288);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.PYROCLAST_CRYSTAL), ModFluids.pyroclastCrystalFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.PYROCLAST_CRYSTAL_NUGGET), ModFluids.pyroclastCrystalFluid, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.PYROCLAST_CRYSTAL_BLOCK), ModFluids.pyroclastCrystalFluid, 1296);

        // 浇筑
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.PYROCLAST_CRYSTAL), TinkerSmeltery.castIngot, ModFluids.pyroclastCrystalFluid, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.PYROCLAST_CRYSTAL_NUGGET), TinkerSmeltery.castNugget, ModFluids.pyroclastCrystalFluid, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.PYROCLAST_CRYSTAL_BLOCK), ItemStack.EMPTY, ModFluids.pyroclastCrystalFluid, 1296);
        
        // 注册矿物词典
        OreDictionary.registerOre("orePyroclastCrystal", ModBlocks.PYROCLAST_CRYSTAL_ORE);
        OreDictionary.registerOre("ingotPyroclastCrystal", ModItems.PYROCLAST_CRYSTAL);
        OreDictionary.registerOre("nuggetPyroclastCrystal", ModItems.PYROCLAST_CRYSTAL_NUGGET);
        OreDictionary.registerOre("blockPyroclastCrystal", ModBlocks.PYROCLAST_CRYSTAL_BLOCK);
        
        // 荆棘生铁 (Thorniron) Oredict
        OreDictionary.registerOre("oreThorniron", ModBlocks.THORNIRON_ORE);
        OreDictionary.registerOre("ingotThorniron", ModItems.THORNIRON_INGOT);
        OreDictionary.registerOre("nuggetThorniron", ModItems.THORNIRON_NUGGET);
        OreDictionary.registerOre("blockThorniron", ModBlocks.THORNIRON_BLOCK);
        
        // ================== 辉锑合金 (Antimony Alloy) ==================
        // 原版熔炉烧炼矿石 -> 锭
        GameRegistry.addSmelting(ModBlocks.ANTIMONY_ALLOY_ORE, new ItemStack(ModItems.ANTIMONY_ALLOY_INGOT), 1.0f);
        // 冶炼
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.ANTIMONY_ALLOY_ORE), ModFluids.antimonyAlloyFluid, 288); // 矿石双倍
        TinkerRegistry.registerMelting(new ItemStack(ModItems.ANTIMONY_ALLOY_INGOT), ModFluids.antimonyAlloyFluid, 144);
        TinkerRegistry.registerMelting(new ItemStack(ModItems.ANTIMONY_ALLOY_NUGGET), ModFluids.antimonyAlloyFluid, 16);
        TinkerRegistry.registerMelting(new ItemStack(ModBlocks.ANTIMONY_ALLOY_BLOCK), ModFluids.antimonyAlloyFluid, 1296);

        // 浇筑
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.ANTIMONY_ALLOY_INGOT), TinkerSmeltery.castIngot, ModFluids.antimonyAlloyFluid, 144);
        TinkerRegistry.registerTableCasting(new ItemStack(ModItems.ANTIMONY_ALLOY_NUGGET), TinkerSmeltery.castNugget, ModFluids.antimonyAlloyFluid, 16);
        TinkerRegistry.registerBasinCasting(new ItemStack(ModBlocks.ANTIMONY_ALLOY_BLOCK), ItemStack.EMPTY, ModFluids.antimonyAlloyFluid, 1296);

        // 合金配方 (1铅 + 1铜 + 1红石(作为催化剂) = 2辉锑合金)
        // 假设铅和铜的流体名称为 lead 和 copper (需由其他模组提供，若无则配方无法生效)，红石熔融为 redstone
        Fluid lead = FluidRegistry.getFluid("lead");
        Fluid copper = FluidRegistry.getFluid("copper");
        Fluid redstone = FluidRegistry.getFluid("redstone");
        
        if (lead != null && copper != null && redstone != null) {
            TinkerRegistry.registerAlloy(
                new FluidStack(ModFluids.antimonyAlloyFluid, 288),
                new FluidStack(lead, 144),
                new FluidStack(copper, 144),
                new FluidStack(redstone, 144)
            );
        }

        // 注册矿物词典
        OreDictionary.registerOre("oreAntimonyAlloy", ModBlocks.ANTIMONY_ALLOY_ORE);
        OreDictionary.registerOre("ingotAntimonyAlloy", ModItems.ANTIMONY_ALLOY_INGOT);
        OreDictionary.registerOre("nuggetAntimonyAlloy", ModItems.ANTIMONY_ALLOY_NUGGET);
        OreDictionary.registerOre("blockAntimonyAlloy", ModBlocks.ANTIMONY_ALLOY_BLOCK);
        
        // ================== 远古灵木 (Ancient Spiritwood) ==================
        OreDictionary.registerOre("logWood", ModBlocks.ANCIENT_SPIRITWOOD_LOG);
        OreDictionary.registerOre("plankWood", ModBlocks.ANCIENT_SPIRITWOOD_PLANKS);
        
        GameRegistry.addShapelessRecipe(
            new net.minecraft.util.ResourceLocation(MODID, "ancient_spiritwood_planks"),
            new net.minecraft.util.ResourceLocation(MODID, "planks"),
            new ItemStack(ModBlocks.ANCIENT_SPIRITWOOD_PLANKS, 4),
            net.minecraft.item.crafting.Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_LOG))
        );
        
        // 远古灵木不能熔化，但可作为熔炉燃料
        GameRegistry.registerFuelHandler(new net.minecraftforge.fml.common.IFuelHandler() {
            @Override
            public int getBurnTime(ItemStack fuel) {
                if (fuel.getItem() == Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_LOG) ||
                    fuel.getItem() == Item.getItemFromBlock(ModBlocks.ANCIENT_SPIRITWOOD_PLANKS)) {
                    return 16000; // 相当于煤炭块 (10个物品，800秒)
                }
                return 0;
            }
        });
    }
}
