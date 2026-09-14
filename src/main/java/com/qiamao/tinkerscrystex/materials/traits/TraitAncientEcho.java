package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.TagUtil;
public class TraitAncientEcho extends AbstractTrait {
    public static final TraitAncientEcho ancient_echo = new TraitAncientEcho();

    public TraitAncientEcho() {
        super("crystex_ancient_echo", 0x8B6B4A);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void miningSpeed(ItemStack tool, net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        // 手柄挖掘速度降低10%
        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), this.identifier)) {
            // 这个特性加在各个部位，但因为匠魂的挖掘速度事件只能获取到整个工具是否有这个trait，
            // 没法在这个事件里判断它是不是作为手柄安装的。
            // 简单的方法是：只要有这个特性，挖掘速度就降低10%
            event.setNewSpeed(event.getNewSpeed() * 0.9f);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onBlockDropExp(BlockEvent.BreakEvent event) {
        ItemStack tool = event.getPlayer().getHeldItemMainhand();
        if (!tool.isEmpty() && TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), this.identifier)) {
            if (event.getExpToDrop() > 0) {
                event.setExpToDrop(event.getExpToDrop() * 2);
            }
        }
    }
}
