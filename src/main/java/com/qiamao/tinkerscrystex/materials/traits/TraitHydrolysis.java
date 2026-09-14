package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitHydrolysis extends AbstractTrait {

    public TraitHydrolysis() {
        super("crystex_hydrolysis", 0x336699); // 暗蓝色，代表水蚀
        MinecraftForge.EVENT_BUS.register(this);
    }

    private boolean isHydrolyzed(Entity entity) {
        return entity.isInWater() || entity.world.isRainingAt(entity.getPosition());
    }

    private boolean isFullyInWater(Entity entity) {
        return entity.isInWater();
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote || !(entity instanceof EntityPlayer)) return;

        // 如果玩家拿着这个工具且处于水蚀条件，每 1秒/2秒 扣除耐久
        if (isSelected && isHydrolyzed(entity)) {
            // 每秒 20 tick。接触水每秒扣3点，仅在雨中每2秒扣1点 (40 tick)
            long time = world.getTotalWorldTime();
            if (isFullyInWater(entity)) {
                if (time % 20 == 0) {
                    ToolHelper.damageTool(tool, 3, (EntityLivingBase)entity);
                }
            } else { // 只是下雨
                if (time % 40 == 0) {
                    ToolHelper.damageTool(tool, 1, (EntityLivingBase)entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null || event.getWorld().isRemote) return;
        
        ItemStack tool = event.getPlayer().getHeldItemMainhand();
        if (!tool.isEmpty() && TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), this.identifier)) {
            // 挖掘含水方块或者在水中挖掘
            Material mat = event.getState().getMaterial();
            boolean isWaterBlock = mat == Material.WATER || mat == Material.SPONGE;
            
            if (isWaterBlock || isHydrolyzed(event.getPlayer())) {
                // 每次挖掘额外消耗 5 点耐久
                ToolHelper.damageTool(tool, 5, event.getPlayer());
            }
        }
    }
}