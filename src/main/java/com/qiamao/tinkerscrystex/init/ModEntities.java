package com.qiamao.tinkerscrystex.init;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.entity.EntityBrownShell;
import com.qiamao.tinkerscrystex.entity.EntityFrostZombie;
import com.qiamao.tinkerscrystex.entity.render.RenderBrownShell;
import com.qiamao.tinkerscrystex.entity.render.RenderFrostZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TinkersCrystex.MODID)
public class ModEntities {
    
    private static int entityId = 0;

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
            createEntityEntry(EntityFrostZombie.class, "frost_zombie", 0x6BA3C7, 0x003366),
            createEntityEntry(EntityBrownShell.class, "brown_shell", 0x5C3A21, 0x1E120B)
        );
        
        // 注册自然生成
        registerEntitySpawns();
    }
    
    private static void registerEntitySpawns() {
        // 找出所有属于寒冷 (SNOWY / COLD) 类型的群系
        Set<Biome> coldBiomes = new HashSet<>();
        coldBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY));
        coldBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD));
        
        Biome[] biomeArray = coldBiomes.toArray(new Biome[0]);
        
        if (biomeArray.length > 0) {
            // 权重为80，和原版僵尸的权重类似，但只在上述筛选的寒冷群系生成
            // 最少生成 1 只，最多生成 4 只一群
            EntityRegistry.addSpawn(EntityFrostZombie.class, 80, 1, 4, EnumCreatureType.MONSTER, biomeArray);
        }
    }

    private static EntityEntry createEntityEntry(Class<? extends Entity> entityClass, String name, int primaryColor, int secondaryColor) {
        return EntityEntryBuilder.create()
                .entity(entityClass)
                .id(new ResourceLocation(TinkersCrystex.MODID, name), entityId++)
                .name(TinkersCrystex.MODID + "." + name)
                .tracker(64, 3, true)
                .egg(primaryColor, secondaryColor)
                .build();
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFrostZombie.class, RenderFrostZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBrownShell.class, RenderBrownShell::new);
    }
}
