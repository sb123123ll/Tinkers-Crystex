package com.qiamao.tinkerscrystex.world;

import com.qiamao.tinkerscrystex.init.ModBiomes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ServerEnvironmentEvents {
    // 处理进入群系 Mood 值的增加逻辑 (假设mood作为NBT数据，此处做示范性自增)
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
            EntityPlayer player = event.player;
            // 1秒 = 20 ticks
            if (player.ticksExisted % 20 == 0) {
                Biome biome = player.world.getBiome(player.getPosition());
                if (biome == ModBiomes.SOULWOOD_HOLLOW) {
                    float currentMood = player.getEntityData().getFloat("tc_mood");
                    // 一秒增加 2%
                    currentMood += 0.02f; 
                    if (currentMood > 1.0f) currentMood = 1.0f; // 封顶 100%
                    player.getEntityData().setFloat("tc_mood", currentMood);
                }
            }
        }
    }
}
