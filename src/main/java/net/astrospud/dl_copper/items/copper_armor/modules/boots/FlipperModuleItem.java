package net.astrospud.dl_copper.items.copper_armor.modules.boots;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FlipperModuleItem extends ModuleItem {
    int counter = 0;
    public FlipperModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_BOOTS);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        boolean didSomething = false;
        if (index <= 1) {
            if (!player.isOnGround() && !player.getAbilities().flying && player.getVelocity().length() > 0.125f && !player.hasStatusEffect(StatusEffects.LEVITATION) && player.isSwimming()) {
                float yaw = player.getYaw();
                float pitch = player.getPitch();
                float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                float g = -MathHelper.sin(pitch * 0.017453292F);
                float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                player.addVelocity(0.015f * f, 0.015 * g, 0.015f * h);
                if (index == 0) world.addParticle(ParticleTypes.BUBBLE.getType(), player.getX()+(0.5-player.getRandom().nextFloat()), player.getY()+(0.5-player.getRandom().nextFloat()), player.getZ()+(0.5-player.getRandom().nextFloat()), 0,0,0);
                didSomething = true;
                if (counter == 30) {
                    stack.damage(1, player, (p) -> {
                        p.getInventory().setStack(slot, ItemStack.EMPTY);
                    });
                }
            }
        }
        if (counter > 60) counter = 0;
        if (didSomething) counter++;
    }
}
