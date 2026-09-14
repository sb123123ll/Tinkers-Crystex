package com.qiamao.tinkerscrystex.materials.traits;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
public class TraitStable extends AbstractTrait {
    public static final TraitStable stable = new TraitStable();
    public TraitStable() {
        super("stable", TextFormatting.DARK_PURPLE);
    }
    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getNewSpeed() * 0.95f);
    }
    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        int reduced = 0;
        for (int i = 0; i < newDamage; i++) {
            if (random.nextFloat() >= 0.15f) {
                reduced++;
            }
        }
        return reduced;
    }
}
