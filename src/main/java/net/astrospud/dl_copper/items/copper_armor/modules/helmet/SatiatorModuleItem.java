package net.astrospud.dl_copper.items.copper_armor.modules.helmet;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SatiatorModuleItem extends ModuleItem {
    public SatiatorModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_HELMET);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        if (index == 0 && player.getHungerManager().isNotFull()) {
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack item = player.getInventory().getStack(i);
                if (item.isFood() && item.getItem().getFoodComponent() != null && item.getItem().getFoodComponent().getHunger() <= player.getHungerManager().getFoodLevel()) {
                    player.eatFood(world, item);
                    player.getInventory().setStack(i, item);
                    stack.damage(1, player, (p) -> {
                        p.getInventory().setStack(slot, DLC_Items.EMPTY_MODULE.getDefaultStack());
                    });
                }
            }
        }
    }

    @Override
    public String id() {
        return "satiator_helmet";
    }
}
