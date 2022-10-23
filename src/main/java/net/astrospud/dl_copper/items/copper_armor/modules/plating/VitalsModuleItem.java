package net.astrospud.dl_copper.items.copper_armor.modules.plating;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class VitalsModuleItem extends ModuleItem {
    int counter = 0;
    public VitalsModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_CHESTPLATE);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        if (index <=2 && player.getHealth() < player.getMaxHealth() && counter == 40) {
            player.heal(1);
            stack.damage(1, player, (p) -> {
                p.getInventory().setStack(slot, ItemStack.EMPTY);
            });
            for (int i = 0; i < 3; i++) {
                world.addParticle(ParticleTypes.HEART, player.getX()+(0.5-player.getRandom().nextFloat()), (player.getY()+player.getEyeY())/2, player.getZ()+(0.5-player.getRandom().nextFloat()), 0,0,0);
            }
        }

        if (counter > 80) counter = 0;
        counter++;
    }
}
