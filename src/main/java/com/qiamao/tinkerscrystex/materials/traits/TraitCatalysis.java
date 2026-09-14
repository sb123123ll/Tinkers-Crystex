package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.TagUtil;
import net.minecraft.nbt.NBTTagList;

public class TraitCatalysis extends AbstractTrait {

    public TraitCatalysis() {
        super("crystex_catalysis", 0x8CB4C8);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private boolean hasTrait(ItemStack stack) {
        return !stack.isEmpty() && TinkerUtil.hasTrait(TagUtil.getTagSafe(stack), this.identifier);
    }

    private boolean isHead(ItemStack stack) {
        if (stack.isEmpty()) return false;
        NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
        // 通常顶端材料是第一个
        if (materials.tagCount() > 0) {
            String mat = materials.getStringTagAt(0);
            return "zeolite".equals(mat);
        }
        return false;
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null || event.getWorld().isRemote) return;
        
        ItemStack tool = event.getPlayer().getHeldItemMainhand();
        if (hasTrait(tool)) {
            float chance = isHead(tool) ? 0.20f : 0.10f;
            if (random.nextFloat() < chance) {
                event.setExpToDrop(event.getExpToDrop() * 2);
            }
        }
    }

    @SubscribeEvent
    public void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() == null || event.getEntityLiving().world.isRemote) return;

        ItemStack tool = event.getAttackingPlayer().getHeldItemMainhand();
        if (hasTrait(tool)) {
            float chance = isHead(tool) ? 0.20f : 0.10f;
            if (random.nextFloat() < chance) {
                event.setDroppedExperience(event.getDroppedExperience() * 2);
            }
        }
    }
}
