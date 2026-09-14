package com.qiamao.tinkerscrystex.materials.traits;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
public class TraitAura extends AbstractTrait {
    public static final TraitAura aura = new TraitAura();
    private int tickCounter = 0;
    public TraitAura() {
        super("crystex_aura", 0x4B0082); // 暗紫色
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack tool = player.getHeldItemMainhand();
        if (!tool.isEmpty() && isToolWithTrait(tool)) {
            World world = player.getEntityWorld();
            int dimension = world.provider.getDimension();
            long time = world.getWorldTime() % 24000;
            float speedMulti = 1.0f;
            if (dimension == 1) { // End
                speedMulti = 1.3f;
            } else if (player.posY < 20) { // Deep Dark/Underground
                speedMulti = 1.2f;
            } else if (time >= 13000 && time <= 23000) { // Night
                speedMulti = 1.1f;
            }
            event.setNewSpeed(event.getNewSpeed() * speedMulti);
        }
    }

    public void onUpdate(ItemStack tool, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer && isSelected) {
            tickCounter++;
            int dimension = world.provider.getDimension();
            if (dimension == 1) {
                if (tickCounter % 200 == 0) { // 10 seconds
                    ToolHelper.healTool(tool, 1, (EntityPlayer)entity);
                }
            } else if (entity.posY < 20) {
                if (tickCounter % 300 == 0) { // 15 seconds
                    ToolHelper.healTool(tool, 1, (EntityPlayer)entity);
                }
            }
        }
    }
}
