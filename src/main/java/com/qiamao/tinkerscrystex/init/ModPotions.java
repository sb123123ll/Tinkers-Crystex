package com.qiamao.tinkerscrystex.init;

import com.qiamao.tinkerscrystex.TinkersCrystex;
import com.qiamao.tinkerscrystex.potions.PotionFrostSlow;
import com.qiamao.tinkerscrystex.potions.PotionRoot;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TinkersCrystex.MODID)
public class ModPotions {
    public static final Potion FROST_SLOW = new PotionFrostSlow();
    public static final Potion ROOT = new PotionRoot();

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
            FROST_SLOW,
            ROOT
        );
    }
}
