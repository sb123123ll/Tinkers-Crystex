package com.qiamao.tinkerscrystex.materials.traits;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemSeeds;
import net.minecraft.init.Items;
import java.util.HashSet;
import java.util.Set;
public class TraitSymbiosis extends AbstractTrait {

    public static final TraitSymbiosis symbiosis = new TraitSymbiosis();

    public TraitSymbiosis() {
        super("crystex_symbiosis", 0x8B4513);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private boolean isPlant(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item instanceof ItemSeeds) return true;
        if (item instanceof ItemBlock) {
            net.minecraft.block.Block block = ((ItemBlock) item).getBlock();
            return block instanceof BlockSapling || block instanceof BlockVine || block instanceof BlockLeaves;
        }
        // Could expand this check further
        return false;
    }
    private int countPlantTypes(EntityPlayer player) {
        Set<Item> plantTypes = new HashSet<>();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (isPlant(stack)) {
                plantTypes.add(stack.getItem());
            }
        }
        return Math.min(plantTypes.size(), 10);
    }
    private boolean hasGoldenApple(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.GOLDEN_APPLE && stack.getMetadata() == 1) { // Enchanted Golden Apple
                return true;
            }
        }
        return false;
    }
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack tool = player.getHeldItemMainhand();
        if (!tool.isEmpty() && isToolWithTrait(tool)) {
            int plantCount = countPlantTypes(player);
            float speedBonus = 1.0f + (plantCount * 0.02f);
            event.setNewSpeed(event.getNewSpeed() * speedBonus);
        }
    }
    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (hasGoldenApple(player)) {
                // 10% chance to ignore damage
                if (random.nextFloat() < 0.1f) {
                    return Math.max(0, newDamage - damage);
                }
            }
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
