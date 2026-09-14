package com.qiamao.tinkerscrystex.materials;

import com.qiamao.tinkerscrystex.fluids.ModFluids;
import com.qiamao.tinkerscrystex.init.ModItems;
import com.qiamao.tinkerscrystex.materials.traits.TraitDetonate;
import com.qiamao.tinkerscrystex.materials.traits.TraitFrost;
import com.qiamao.tinkerscrystex.materials.traits.TraitAbyssalPressure;
import com.qiamao.tinkerscrystex.materials.traits.TraitUnstable;
import com.qiamao.tinkerscrystex.materials.traits.TraitPhotosynthesis;
import com.qiamao.tinkerscrystex.materials.traits.TraitBriarSting;
import com.qiamao.tinkerscrystex.materials.traits.TraitHardened;
import com.qiamao.tinkerscrystex.materials.traits.TraitStable;
import com.qiamao.tinkerscrystex.materials.traits.TraitAura;
import com.qiamao.tinkerscrystex.materials.traits.TraitSoulbound;
import com.qiamao.tinkerscrystex.materials.traits.TraitAncientEcho;
import com.qiamao.tinkerscrystex.materials.traits.TraitSymbiosis;
import com.qiamao.tinkerscrystex.materials.traits.TraitRegrowth;
import com.qiamao.tinkerscrystex.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class ModMaterials {
    public static final Material frostSteel = new Material("froststeel", 0x65A2C8);
    public static final Material wolframite = new Material("wolframite", 0x333333); // 黑钨矿
    public static final Material abyssalite = new Material("abyssalite", 0x003366);
    public static final Material pyroclastCrystal = new Material("pyroclast_crystal", 0xFF4500);
    public static final Material thorniron = new Material("thorniron", 0x2E8B57);
    public static final Material antimonyAlloy = new Material("antimony_alloy", 0xA66B7C);
    public static final Material relicWood = new Material("relic_wood", 0x8B6B4A);
    public static final ITrait traitMoltenCore = new com.qiamao.tinkerscrystex.materials.traits.TraitMoltenCore();
    public static final ITrait traitHeavyCrush = new com.qiamao.tinkerscrystex.materials.traits.TraitHeavyCrush();
    public static final ITrait traitHydrolysis = new com.qiamao.tinkerscrystex.materials.traits.TraitHydrolysis();

    public static void preInit(FMLPreInitializationEvent event) {
        setupMaterials();
    }

    public static void setupMaterials() {
        // 基础属性设置
        frostSteel.addItem(ModItems.FROST_STEEL_INGOT, 1, Material.VALUE_Ingot);
        frostSteel.setRepresentativeItem(ModItems.FROST_STEEL_INGOT);
        frostSteel.setCraftable(false).setCastable(true); // 仅能浇筑
        frostSteel.setFluid(ModFluids.fluidFrostSteel);

        // 注册材料到匠魂
        TinkerRegistry.addMaterial(frostSteel);

        // 设置各个部件的数值
        // 顶端部件 (Head): 耐久 650, 挖掘速度 5.5, 攻击力 4.5, 挖掘等级 4(钴级)
        TinkerRegistry.addMaterialStats(frostSteel, 
            new HeadMaterialStats(650, 5.5f, 4.5f, HarvestLevels.COBALT));
        
        // 手柄部件 (Handle): 手柄系数 1.3, 基础耐久 120
        TinkerRegistry.addMaterialStats(frostSteel, 
            new HandleMaterialStats(1.3f, 120));
        
        // 额外部件 (Extra): 耐久 180
        TinkerRegistry.addMaterialStats(frostSteel, 
            new ExtraMaterialStats(180));
        
        // 远程部件 (Bow): 拉弓速度 0.8 (越低越快), 射程系数 1.2, 附加伤害 3
        TinkerRegistry.addMaterialStats(frostSteel, 
            new BowMaterialStats(0.8f, 1.2f, 3f));

        // 添加特性
        frostSteel.addTrait(TraitFrost.frost);

        TinkerRegistry.addMaterial(wolframite);
        wolframite.addTrait(traitMoltenCore, MaterialTypes.HEAD);
        wolframite.addTrait(traitHeavyCrush, MaterialTypes.HANDLE);
        wolframite.addTrait(traitHydrolysis); // 所有部件都有水蚀
        TinkerRegistry.addMaterialStats(wolframite,
                new HeadMaterialStats(1350, 10.0f, 7.5f, HarvestLevels.COBALT),
                new HandleMaterialStats(1.45f, 0),
                new ExtraMaterialStats(320));
        
        // ================= 深渊幽矿 (Abyssalite) =================
        abyssalite.addItem(ModItems.ABYSSALITE_INGOT, 1, Material.VALUE_Ingot);
        abyssalite.setCraftable(false).setCastable(true); // 仅能浇筑
        abyssalite.setFluid(ModFluids.abyssaliteFluid);
        TinkerRegistry.addMaterial(abyssalite);

        // 镐头 (Head): 耐久 950, 挖掘速度 6.5, 攻击力 3, 挖掘等级 3(钻石级)
        TinkerRegistry.addMaterialStats(abyssalite, 
            new HeadMaterialStats(950, 6.5f, 3.0f, HarvestLevels.DIAMOND));
        
        // 手柄 (Handle): 耐久倍率 1.3x, 不附加额外耐久
        TinkerRegistry.addMaterialStats(abyssalite, 
            new HandleMaterialStats(1.3f, 0));
        
        // 额外部件 (Extra): 耐久 50
        TinkerRegistry.addMaterialStats(abyssalite, 
            new ExtraMaterialStats(50));
        
        // 远程部件 (Bow): 拉弓速度 1.1, 射程系数 1.5, 附加伤害 1
        TinkerRegistry.addMaterialStats(abyssalite, 
            new BowMaterialStats(1.1f, 1.5f, 1f));

        // 添加特性
        abyssalite.addTrait(TraitAbyssalPressure.abyssal_pressure);
        
        // ================= 烬晶 (Pyroclast Crystal) =================
        pyroclastCrystal.addItem(ModItems.PYROCLAST_CRYSTAL, 1, Material.VALUE_Ingot);
        pyroclastCrystal.setCraftable(false).setCastable(true); // 仅能浇筑
        pyroclastCrystal.setFluid(ModFluids.pyroclastCrystalFluid);
        TinkerRegistry.addMaterial(pyroclastCrystal);

        // 镐头 (Head): 耐久 420, 挖掘速度 8.0, 攻击力 6.5, 挖掘等级 4(黑曜石/钴级)
        TinkerRegistry.addMaterialStats(pyroclastCrystal, 
            new HeadMaterialStats(420, 8.0f, 6.5f, HarvestLevels.OBSIDIAN));
        
        // 手柄 (Handle): 耐久倍率 0.7x, 不附加额外耐久
        TinkerRegistry.addMaterialStats(pyroclastCrystal, 
            new HandleMaterialStats(0.7f, 0));
        
        // 额外部件 (Extra): 耐久 80
        TinkerRegistry.addMaterialStats(pyroclastCrystal, 
            new ExtraMaterialStats(80));

        // 顶端部件添加爆燃特性
        pyroclastCrystal.addTrait(TraitDetonate.detonate, slimeknights.tconstruct.library.materials.MaterialTypes.HEAD);
        
        // 手柄和额外部件添加不稳定特性
        pyroclastCrystal.addTrait(TraitUnstable.unstable, slimeknights.tconstruct.library.materials.MaterialTypes.HANDLE);
        pyroclastCrystal.addTrait(TraitUnstable.unstable, slimeknights.tconstruct.library.materials.MaterialTypes.EXTRA);

        // ================= 荆棘生铁 (Thorniron) =================
        thorniron.addItem(ModItems.THORNIRON_INGOT, 1, Material.VALUE_Ingot);
        thorniron.setCraftable(false).setCastable(true); 
        thorniron.setFluid(ModFluids.thornironFluid);
        TinkerRegistry.addMaterial(thorniron);

        // 镐头 (Head): 耐久 550, 挖掘速度 5.0, 攻击力 4.0, 挖掘等级 2(铁级)
        TinkerRegistry.addMaterialStats(thorniron, 
            new HeadMaterialStats(550, 5.0f, 4.0f, HarvestLevels.IRON));
        
        // 手柄 (Handle): 耐久倍率 1.4x, 附加额外耐久 60
        TinkerRegistry.addMaterialStats(thorniron, 
            new HandleMaterialStats(1.4f, 60));
        
        // 额外部件 (Extra): 耐久 120
        TinkerRegistry.addMaterialStats(thorniron, 
            new ExtraMaterialStats(120));

        // 全部部件添加光合共生特性
        thorniron.addTrait(TraitPhotosynthesis.photosynthesis);
        // 仅顶端部件添加毒刺缠绕特性
        thorniron.addTrait(TraitBriarSting.briar_sting, slimeknights.tconstruct.library.materials.MaterialTypes.HEAD);
        
        // ================= 辉锑合金 (Antimony Alloy) =================
        antimonyAlloy.addItem(ModItems.ANTIMONY_ALLOY_INGOT, 1, Material.VALUE_Ingot);
        antimonyAlloy.setRepresentativeItem(ModItems.ANTIMONY_ALLOY_INGOT);
        antimonyAlloy.setCraftable(false).setCastable(true);
        antimonyAlloy.setFluid(ModFluids.antimonyAlloyFluid);
        TinkerRegistry.addMaterial(antimonyAlloy);
        TinkerRegistry.addMaterialStats(antimonyAlloy,
                new HeadMaterialStats(250, 6.0f, 5.0f, 3), // 黑曜石级
                new HandleMaterialStats(1.1f, 50),
                new ExtraMaterialStats(60)
        );

        antimonyAlloy.addTrait(TraitHardened.hardened, slimeknights.tconstruct.library.materials.MaterialTypes.HEAD);
        antimonyAlloy.addTrait(TraitStable.stable, slimeknights.tconstruct.library.materials.MaterialTypes.HANDLE);
        antimonyAlloy.addTrait(TraitStable.stable, slimeknights.tconstruct.library.materials.MaterialTypes.EXTRA);

        // ================= 古遗木 (Relic Wood) =================
        relicWood.addItem(net.minecraft.item.Item.getItemFromBlock(com.qiamao.tinkerscrystex.init.ModBlocks.RELIC_WOOD_LOG), 1, Material.VALUE_Ingot);
        relicWood.addItem(net.minecraft.item.Item.getItemFromBlock(com.qiamao.tinkerscrystex.init.ModBlocks.RELIC_WOOD_PLANKS), 1, Material.VALUE_Ingot / 4);
        relicWood.setRepresentativeItem(net.minecraft.item.Item.getItemFromBlock(com.qiamao.tinkerscrystex.init.ModBlocks.RELIC_WOOD_LOG));
        relicWood.setCraftable(true).setCastable(false); // 木质，只能在部件加工台制作
        TinkerRegistry.addMaterial(relicWood);
        TinkerRegistry.addMaterialStats(relicWood,
                new HeadMaterialStats(55, 3.5f, 2.0f, HarvestLevels.STONE),
                new HandleMaterialStats(0.9f, 25),
                new ExtraMaterialStats(25)
        );

        relicWood.addTrait(TraitSoulbound.soulbound, slimeknights.tconstruct.library.materials.MaterialTypes.HEAD);
        relicWood.addTrait(TraitAncientEcho.ancient_echo, slimeknights.tconstruct.library.materials.MaterialTypes.HANDLE);
        relicWood.addTrait(TraitAncientEcho.ancient_echo, slimeknights.tconstruct.library.materials.MaterialTypes.EXTRA);
    }
}
