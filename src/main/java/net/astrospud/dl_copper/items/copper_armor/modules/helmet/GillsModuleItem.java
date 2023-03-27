package net.astrospud.dl_copper.items.copper_armor.modules.helmet;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GillsModuleItem extends ModuleItem {
    public GillsModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_HELMET);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        didSomething = false;
        if (index <=2 && player.getAir() < player.getMaxAir()) {
            if (counter % 2 == 0) player.setAir(player.getAir() + 1);
            didSomething = true;
            if (counter == 20) {
                stack.damage(1, player, (p) -> {
                    p.getInventory().setStack(slot, DLC_Items.EMPTY_MODULE.getDefaultStack());
                });
            }
        }
    }

    @Override
    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {
        super.afterTick(stack, world, player, slot, selected);

        if (counter > 40) counter = 0;
        if (didSomething) counter++;
    }

    @Override
    public int maxLevel() {
        return 3;
    }

    @Override
    public String id() {
        return "gills_helmet";
    }
}
