package net.astrospud.dl_copper.items.copper_armor.modules.helmet;

import net.astrospud.dl_copper.items.BatteryPackItem;
import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolarModuleItem extends ModuleItem {
    public SolarModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_HELMET);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        super.specialTick(stack, world, player, slot, selected, index);

        if (index <= 2) {
            if (counter >= 150) {
                BatteryPackItem.chargeInventory(stack, world, player, slot, selected, 1);
            }
        }
    }

    @Override
    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {
        super.afterTick(stack, world, player, slot, selected);

        int lightLevel = world.getLightLevel(player.getBlockPos());
        lightLevel = Math.max(lightLevel - 5, 0);
        if (counter >= 150) {
            counter = 0;
        }
        counter += lightLevel;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public int maxLevel() {
        return 3;
    }

    @Override
    public String id() {
        return "solar_helmet";
    }
}
