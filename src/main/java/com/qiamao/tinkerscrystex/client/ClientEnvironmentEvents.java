package com.qiamao.tinkerscrystex.client;

import com.qiamao.tinkerscrystex.init.ModBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEnvironmentEvents {
    
    // 控制群系内暗色雾气
    @SubscribeEvent
    public static void onFogColors(EntityViewRenderEvent.FogColors event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && player.world != null) {
            Biome biome = player.world.getBiome(player.getPosition());
            if (biome == ModBiomes.SOULWOOD_HOLLOW) {
                // 变暗30%的雾气
                event.setRed(event.getRed() * 0.7f);
                event.setGreen(event.getGreen() * 0.7f);
                event.setBlue(event.getBlue() * 0.7f);
            }
        }
    }
    
    // 控制群系内的水面粒子与氛围
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        World world = mc.world;
        
        if (player != null && world != null && !mc.isGamePaused()) {
            BlockPos playerPos = player.getPosition();
            Biome biome = world.getBiome(playerPos);
            
            if (biome == ModBiomes.SOULWOOD_HOLLOW) {
                // 1. 生成水面淡蓝色星点粒子
                if (world.rand.nextInt(3) == 0) {
                    int rx = playerPos.getX() + world.rand.nextInt(32) - 16;
                    int rz = playerPos.getZ() + world.rand.nextInt(32) - 16;
                    int ry = playerPos.getY() + world.rand.nextInt(8) - 4;
                    BlockPos targetPos = new BlockPos(rx, ry, rz);
                    
                    if (world.getBlockState(targetPos).getBlock() == Blocks.WATER && 
                        world.isAirBlock(targetPos.up())) {
                        world.spawnParticle(EnumParticleTypes.TOWN_AURA, 
                            rx + world.rand.nextFloat(), 
                            ry + 1.0f + world.rand.nextFloat() * 0.2f, 
                            rz + world.rand.nextFloat(), 
                            0.0D, 0.01D, 0.0D);
                    }
                }
            }
        }
    }
}
