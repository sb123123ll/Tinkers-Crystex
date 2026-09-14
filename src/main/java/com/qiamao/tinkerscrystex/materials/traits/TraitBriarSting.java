package com.qiamao.tinkerscrystex.materials.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitBriarSting extends AbstractTrait {
    public static final TraitBriarSting briar_sting = new TraitBriarSting();

    public TraitBriarSting() {
        super("crystex_briar_sting", 0x006400); // 深绿色
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target != null && target.isEntityAlive()) {
            // 毒刺缠绕：中毒 I (3秒) + 缓慢 II (1.5秒，模拟缠绕)
            target.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 0));
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 1));
        }
    }

    // 处理攻击被格挡时的反弹伤害逻辑
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        // 如果受伤的是生物，且伤害来源是玩家
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack weapon = attacker.getHeldItemMainhand();
            
            // 检查武器是否带有此特性
            if (!weapon.isEmpty() && slimeknights.tconstruct.library.utils.TinkerUtil.hasTrait(slimeknights.tconstruct.library.utils.TagUtil.getTagSafe(weapon), this.identifier)) {
                EntityLivingBase target = event.getEntityLiving();
                
                // 如果目标成功格挡了这次攻击 (比如举盾)
                if (target.isActiveItemStackBlocking()) {
                    // 反弹 25% 的刺伤伤害给目标 (无法被普通护甲抵消的魔法/荆棘伤害)
                    float reflectDamage = event.getAmount() * 0.25f;
                    if (reflectDamage > 0) {
                        target.attackEntityFrom(DamageSource.causeThornsDamage(attacker), reflectDamage);
                    }
                }
            }
        }
    }
}
