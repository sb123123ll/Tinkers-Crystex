package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitSoulbound extends AbstractTrait {
    public static final TraitSoulbound soulbound = new TraitSoulbound();

    public TraitSoulbound() {
        super("crystex_soulbound", TextFormatting.GOLD);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            int currentDamage = tool.getItemDamage();
            int maxDamage = tool.getMaxDamage();
            
            // 如果工具即将损坏
            if (currentDamage + newDamage >= maxDamage && !ToolHelper.isBroken(tool)) {
                // 检查冷却时间
                long lastTime = player.getEntityData().getLong("TraitSoulboundLastTime");
                long currentTime = player.world.getTotalWorldTime();
                
                if (currentTime - lastTime >= 60) {
                    if (player.experienceLevel >= 5) {
                        player.addExperienceLevel(-5);
                        
                        // 重置耐久（其实就是返回一个能把新伤害抵消掉的负数）
                        // 当前伤害 currentDamage，新伤害 newDamage，要将其恢复到满耐久（即伤害为0）
                        // 因为 tcon 源码里是 newDamage = originalDamage + onToolDamage
                        // 所以这里返回 -(currentDamage + newDamage)
                        
                        player.getEntityData().setLong("TraitSoulboundLastTime", currentTime);
                        
                        if (player.world instanceof WorldServer) {
                            ((WorldServer) player.world).spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, 
                                player.posX, player.posY + 1, player.posZ, 
                                30, 0.5, 0.5, 0.5, 0.1);
                        }
                        
                        return -(currentDamage + newDamage);
                    }
                }
            }
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
