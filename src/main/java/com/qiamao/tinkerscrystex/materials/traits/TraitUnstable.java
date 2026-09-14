package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import java.util.Random;

public class TraitUnstable extends AbstractTrait {
    public static final TraitUnstable unstable = new TraitUnstable();
    private static final Random rand = new Random();

    public TraitUnstable() {
        super("crystex_unstable", 0xFF6347); // 番茄红
    }

    private boolean isLowDurability(ItemStack tool) {
        if (!tool.isEmpty() && tool.hasTagCompound()) {
            int maxDamage = ToolHelper.getMaxDurability(tool);
            int currentDamage = ToolHelper.getCurrentDurability(tool);
            float ratio = (float) currentDamage / (float) maxDamage;
            return ratio < 0.30f; // 低于 30% 耐久
        }
        return false;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (isLowDurability(tool)) {
            // 挖掘速度提升 20%
            event.setNewSpeed(event.getNewSpeed() * 1.2f);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (isLowDurability(tool)) {
            // 攻击力提升 20%
            newDamage *= 1.2f;
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (isLowDurability(tool) && rand.nextFloat() < 0.05f) {
            // 额外消耗 2 点耐久
            ToolHelper.damageTool(tool, 2, player);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && isLowDurability(tool) && rand.nextFloat() < 0.05f) {
            // 额外消耗 2 点耐久
            ToolHelper.damageTool(tool, 2, player);
        }
    }
}