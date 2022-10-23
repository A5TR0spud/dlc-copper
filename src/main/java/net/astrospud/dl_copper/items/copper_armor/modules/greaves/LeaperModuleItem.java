package net.astrospud.dl_copper.items.copper_armor.modules.greaves;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LeaperModuleItem extends ModuleItem {
    int counter = 0;
    public LeaperModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_LEGGINGS);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        boolean didSomething = false;
        if (index == 0) {
            if (!player.isTouchingWater() && !player.isOnGround() && !player.getAbilities().flying && player.getVelocity().multiply(1, 0, 1).length() > 0.125f && player.getVelocity().y > 0 && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
                float yaw = player.getYaw();
                float f = -MathHelper.sin(yaw * 0.017453292F);
                float h = MathHelper.cos(yaw * 0.017453292F);
                player.addVelocity(0.0175f * f, 0.015, 0.0175f * h);
                world.addParticle(ParticleTypes.CLOUD.getType(), player.getX(), ((player.getY()+player.getEyeY())/2)-(player.getStandingEyeHeight()/2)+(player.getRandom().nextFloat()*0.75), player.getZ(), (0.5f*player.getVelocity().x)+(0.25*(0.5-player.getRandom().nextFloat())), 0, (0.5f*player.getVelocity().z)+(0.25*(0.5-player.getRandom().nextFloat())));
                didSomething = true;
                if (counter == 10) {
                    stack.damage(1, player, (p) -> {
                        p.getInventory().setStack(slot, ItemStack.EMPTY);
                    });
                }
            }
        }
        if (index == 1) {
            if (!player.isOnGround() && !player.getAbilities().flying && player.getVelocity().multiply(1, 0, 1).length() > 0.125f && player.getVelocity().y > 0 && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
                float yaw = player.getYaw();
                float f = -MathHelper.sin(yaw * 0.017453292F);
                float h = MathHelper.cos(yaw * 0.017453292F);
                player.addVelocity(0.0125f * f, 0.015, 0.0125f * h);
                didSomething = true;
                if (counter == 10) {
                    stack.damage(1, player, (p) -> {
                        p.getInventory().setStack(slot, ItemStack.EMPTY);
                    });
                }
            }
        }
        if (counter > 20) counter = 0;
        if (didSomething) counter++;
    }
}
