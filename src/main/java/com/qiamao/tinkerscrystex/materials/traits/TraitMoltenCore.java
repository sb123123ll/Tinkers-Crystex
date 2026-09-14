package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import net.minecraft.entity.player.EntityPlayer;

public class TraitMoltenCore extends AbstractTrait {

    public TraitMoltenCore() {
        super("crystex_molten_core", 0xFF6600); // 橙红色
        MinecraftForge.EVENT_BUS.register(this);
    }

    private boolean isHydrolyzed(EntityLivingBase player) {
        // 判断玩家是否在水中，或者被雨淋到
        return player.isInWater() || player.world.isRainingAt(player.getPosition());
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (isHydrolyzed(event.getEntityPlayer())) {
            // 水蚀状态下：速度降低 70%
            event.setNewSpeed(event.getOriginalSpeed() * 0.3f);
        } else {
            // 熔火之心：速度增加 45%
            event.setNewSpeed(event.getOriginalSpeed() * 1.45f);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (isHydrolyzed(player)) {
            // 水蚀状态下：伤害降低 50%
            return newDamage * 0.5f;
        } else {
            // 熔火之心：伤害增加 25%
            return newDamage * 1.25f;
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && !isHydrolyzed(player) && !player.world.isRemote) {
            // 打击迸发火星
            ((net.minecraft.world.WorldServer)player.world).spawnParticle(
                EnumParticleTypes.LAVA, 
                target.posX, target.posY + target.height / 2, target.posZ, 
                5, 0.2, 0.2, 0.2, 0.05
            );
        }
    }

    // 自动冶炼逻辑
    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        if (event.getHarvester() == null || event.getWorld().isRemote) return;
        
        ItemStack tool = event.getHarvester().getHeldItemMainhand();
        if (!tool.isEmpty() && TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), this.identifier)) {
            if (isHydrolyzed(event.getHarvester())) return; // 处于水蚀状态不触发自动冶炼

            boolean smelted = false;
            for (int i = 0; i < event.getDrops().size(); i++) {
                ItemStack drop = event.getDrops().get(i);
                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(drop);
                if (!result.isEmpty()) {
                    ItemStack smeltedStack = result.copy();
                    smeltedStack.setCount(drop.getCount());
                    event.getDrops().set(i, smeltedStack);
                    smelted = true;
                }
            }

            if (smelted) {
                // 播放燃烧声音和粒子
                BlockPos pos = event.getPos();
                event.getWorld().playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (event.getWorld().rand.nextFloat() - event.getWorld().rand.nextFloat()) * 0.8F);
                ((net.minecraft.world.WorldServer)event.getWorld()).spawnParticle(
                    EnumParticleTypes.FLAME, 
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                    8, 0.3, 0.3, 0.3, 0.05
                );
            }
        }
    }
}
