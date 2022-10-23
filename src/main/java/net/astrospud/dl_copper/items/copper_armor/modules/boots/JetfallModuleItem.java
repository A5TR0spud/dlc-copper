package net.astrospud.dl_copper.items.copper_armor.modules.boots;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class JetfallModuleItem extends ModuleItem {
    public JetfallModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_BOOTS);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        if (index == 0 && player.fallDistance > player.getSafeFallDistance()) {
            stack.damage(1, player, (p) -> {
                p.getInventory().setStack(slot, ItemStack.EMPTY);
            });
            float multiplier = 0.7f;
            player.fallDistance *= multiplier;
            player.setVelocity(player.getVelocity().multiply(1, multiplier, 1));
            for (int i = 0; i < 3; i++) {
                world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), player.getVelocity().x + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())), player.getVelocity().y * (2 - multiplier), player.getVelocity().z + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())));
            }
        }
    }
}
