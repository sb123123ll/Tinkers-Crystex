package com.qiamao.tinkerscrystex.materials.traits;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
public class TraitHardened extends AbstractTrait {
    public static final TraitHardened hardened = new TraitHardened();
    public TraitHardened() {
        super("hardened", TextFormatting.RED);
    }
    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (ToolHelper.isBroken(tool)) return;
        int max = ToolHelper.getMaxDurability(tool);
        if (max == 0) return;
        int damage = tool.getItemDamage();
        float ratio = (float)(max - damage) / max;
        float modifier = 1.0f;
        if (ratio >= 0.8f) {
            modifier = 1.2f;
        } else if (ratio >= 0.5f) {
            modifier = 1.1f;
        } else if (ratio < 0.2f) {
            modifier = 0.9f;
        }
        if (modifier != 1.0f) {
            event.setNewSpeed(event.getNewSpeed() * modifier);
        }
    }
}
