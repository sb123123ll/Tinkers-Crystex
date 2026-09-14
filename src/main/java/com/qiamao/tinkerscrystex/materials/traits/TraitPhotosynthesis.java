package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitPhotosynthesis extends AbstractTrait {

    public static final TraitPhotosynthesis photosynthesis = new TraitPhotosynthesis();

    public TraitPhotosynthesis() {
        super("crystex_photosynthesis", 0x7ED321);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        // 只有玩家持有且每 40 tick (约 2 秒) 检查一次，避免过度运算
        if (!world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 40 == 0) {
            EntityPlayer player = (EntityPlayer) entity;
            
            // 工具是否受损
            if (ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)) {
                BlockPos pos = player.getPosition();
                
                // 判断 1: 是否处于白天且露天 (光合作用核心)
                boolean isSunlight = world.isDaytime() && world.canSeeSky(pos);
                
                // 判断 2: 是否站在草方块、树叶、泥土等自然方块上 (共生)
                Material floorMat = world.getBlockState(pos.down()).getMaterial();
                boolean isOnNature = floorMat == Material.GRASS || floorMat == Material.LEAVES || floorMat == Material.PLANTS || floorMat == Material.VINE || floorMat == Material.GROUND;
                
                if (isSunlight || isOnNature) {
                    // 恢复 1 点耐久
                    ToolHelper.healTool(tool, 1, player);
                }
            }
        }
    }
}
