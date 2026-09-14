package com.qiamao.tinkerscrystex.materials.traits;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import net.minecraft.entity.player.EntityPlayer;
public class TraitRegrowth extends AbstractTrait {
    public static final TraitRegrowth regrowth = new TraitRegrowth();
    private int tickCounter = 0;
    public TraitRegrowth() {
        super("crystex_regrowth", 0x228B22); // 森林绿
    }

    public void onUpdate(ItemStack tool, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            tickCounter++;
            // maxRepairPercent = 0.8
            int maxDamageAllowed = (int)(ToolHelper.getMaxDurability(tool) * 0.2f);
            int currentDamage = tool.getItemDamage();
            if (currentDamage > maxDamageAllowed) {
                int interval = isSelected ? 600 : 1200; // 30s held, 60s inventory
                if (tickCounter % interval == 0) {
                    ToolHelper.healTool(tool, 1, (EntityPlayer)entity);
                }
            }
        }
    }
}
