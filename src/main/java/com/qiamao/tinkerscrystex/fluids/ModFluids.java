package com.qiamao.tinkerscrystex.fluids;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraft.block.material.Material;

public class ModFluids {
    public static Fluid fluidFrostSteel;
    public static BlockFluidClassic blockFrostSteelFluid;

    public static Fluid fluidWolframite;
    public static BlockFluidClassic blockWolframiteFluid;
    
    public static Fluid abyssaliteFluid;
    public static BlockFluidClassic blockAbyssaliteFluid;

    public static Fluid pyroclastCrystalFluid;
    public static BlockFluidClassic blockPyroclastCrystalFluid;

    public static Fluid thornironFluid;
    public static BlockFluidClassic blockThornironFluid;
    public static Fluid antimonyAlloyFluid;
    public static BlockFluidClassic blockAntimonyAlloyFluid;


    public static void registerFluids() {
        // 霜钢流体（极寒流体，不引燃）
        fluidFrostSteel = new Fluid("froststeel", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/froststeel_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/froststeel_flow"))
            .setColor(0xFF65A2C8)
            .setTemperature(100) // 低温
            .setDensity(3000);
        FluidRegistry.registerFluid(fluidFrostSteel);
        FluidRegistry.addBucketForFluid(fluidFrostSteel);

        // 使用 Material.WATER 材质以确保不会引燃周围方块或点燃实体
        blockFrostSteelFluid = new BlockFluidClassic(fluidFrostSteel, Material.WATER);
        blockFrostSteelFluid.setRegistryName(TinkersCrystex.MODID, "fluid_froststeel");
        blockFrostSteelFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_froststeel");

        // 黑钨矿流体（超高温，引燃）
        fluidWolframite = new Fluid("wolframite", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/molten_wolframite_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/molten_wolframite_flow"))
            .setColor(0xFFFFFFFF)
            .setTemperature(3400) // 高温
            .setDensity(4000);
        FluidRegistry.registerFluid(fluidWolframite);
        FluidRegistry.addBucketForFluid(fluidWolframite);

        blockWolframiteFluid = new BlockFluidClassic(fluidWolframite, Material.LAVA);
        blockWolframiteFluid.setRegistryName(TinkersCrystex.MODID, "fluid_wolframite");
        blockWolframiteFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_wolframite");
        
        // 深渊幽矿流体（高压炽热深海熔浆，引燃）
        abyssaliteFluid = new Fluid("abyssalite", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/abyssalite_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/abyssalite_flow"))
            .setColor(0xFF003366)
            .setTemperature(1200)
            .setLuminosity(10)
            .setDensity(4000)
            .setViscosity(15000);
        FluidRegistry.registerFluid(abyssaliteFluid);
        FluidRegistry.addBucketForFluid(abyssaliteFluid);
        
        blockAbyssaliteFluid = new BlockFluidClassic(abyssaliteFluid, Material.LAVA);
        blockAbyssaliteFluid.setRegistryName(TinkersCrystex.MODID, "fluid_abyssalite");
        blockAbyssaliteFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_abyssalite");

        // 烬晶流体（暴烈火流，引燃）
        pyroclastCrystalFluid = new Fluid("pyroclast_crystal", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/pyroclast_crystal_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/pyroclast_crystal_flow"))
            .setColor(0xFFFF4500)
            .setTemperature(1800)
            .setLuminosity(15)
            .setDensity(3000)
            .setViscosity(8000);
        FluidRegistry.registerFluid(pyroclastCrystalFluid);
        FluidRegistry.addBucketForFluid(pyroclastCrystalFluid);
        
        blockPyroclastCrystalFluid = new BlockFluidClassic(pyroclastCrystalFluid, Material.LAVA);
        blockPyroclastCrystalFluid.setRegistryName(TinkersCrystex.MODID, "fluid_pyroclast_crystal");
        blockPyroclastCrystalFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_pyroclast_crystal");

        // 荆棘生铁流体（熔融金属，引燃）
        thornironFluid = new Fluid("thorniron", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/thorniron_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/thorniron_flow"))
            .setColor(0xFF2E8B57)
            .setTemperature(1000) 
            .setLuminosity(8) 
            .setDensity(2500)
            .setViscosity(9000);
        FluidRegistry.registerFluid(thornironFluid);
        FluidRegistry.addBucketForFluid(thornironFluid);
        
        blockThornironFluid = new BlockFluidClassic(thornironFluid, Material.LAVA);
        blockThornironFluid.setRegistryName(TinkersCrystex.MODID, "fluid_thorniron");
        blockThornironFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_thorniron");

        // 辉锑合金流体（熔融合金，引燃）
        antimonyAlloyFluid = new Fluid("antimony_alloy", 
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/antimony_alloy_still"),
            new ResourceLocation(TinkersCrystex.MODID, "blocks/fluids/antimony_alloy_flow"))
            .setColor(0xFF8B8B83)
            .setTemperature(1100) 
            .setLuminosity(8) 
            .setDensity(3500)
            .setViscosity(11000);
        FluidRegistry.registerFluid(antimonyAlloyFluid);
        FluidRegistry.addBucketForFluid(antimonyAlloyFluid);
        
        blockAntimonyAlloyFluid = new BlockFluidClassic(antimonyAlloyFluid, Material.LAVA);
        blockAntimonyAlloyFluid.setRegistryName(TinkersCrystex.MODID, "fluid_antimony_alloy");
        blockAntimonyAlloyFluid.setUnlocalizedName(TinkersCrystex.MODID + ".fluid_antimony_alloy");
    }
}
