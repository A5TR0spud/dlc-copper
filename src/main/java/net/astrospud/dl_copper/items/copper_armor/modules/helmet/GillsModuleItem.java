package net.astrospud.dl_copper.items.copper_armor.modules.helmet;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GillsModuleItem extends ModuleItem {
    int counter = 0;

    public GillsModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_HELMET);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        boolean didSomething = false;
        if (index == 0 && player.getAir() < player.getMaxAir()) {
            player.setAir(player.getAir() + 1);
            didSomething = true;
            if (counter == 20) {
                stack.damage(1, player, (p) -> {
                    p.getInventory().setStack(slot, ItemStack.EMPTY);
                });
            }
        }

        if (counter > 40) counter = 0;
        if (didSomething) counter++;
    }
}
