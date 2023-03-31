package net.astrospud.dl_copper.items.copper_armor.modules.boots;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.astrospud.dl_copper.registration.DLC_Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class JetfallModuleItem extends ModuleItem {
    int soundCooldown = 0;
    public JetfallModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_BOOTS);
    }

    public JetfallModuleItem(Settings settings, boolean isComputer) {
        super(settings, DLC_Items.COPPER_BOOTS, isComputer);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        if (soundCooldown > -1) soundCooldown--;
        if ((MinecraftClient.getInstance().player != null
                && MinecraftClient.getInstance().player.input.sneaking)
                || player.getVelocity().y >= 0)
            return;

        if (index == 0 && player.fallDistance > player.getSafeFallDistance()) {
            stack.damage(1, player, (p) -> {
                p.getInventory().setStack(slot, getBrokenState(stack));
            });
            float multiplier = 0.7f;
            player.fallDistance *= multiplier;
            player.setVelocity(player.getVelocity().multiply(1, multiplier, 1));
            player.playSound(DLC_Sounds.JETFALL_BURN, SoundCategory.PLAYERS, 0.275f, 0.9f + 0.2f * player.getRandom().nextFloat());
            if (player.getVelocity().y < -0.4 && soundCooldown <= 0) {
                player.playSound(DLC_Sounds.JETFALL_SAVE, SoundCategory.PLAYERS, 0.6f, 0.9f + 0.2f * player.getRandom().nextFloat());
                soundCooldown = 20;
            }
            for (int i = 0; i < 3; i++) {
                world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), player.getVelocity().x + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())), player.getVelocity().y * (2 - multiplier), player.getVelocity().z + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())));
            }
        }

        if (index == 1 && player.fallDistance > 0) {
            if (counter == 30) {
                stack.damage(1, player, (p) -> {
                    p.getInventory().setStack(slot, getBrokenState(stack));
                });
            }
            float multiplier = 0.7f;
            player.setVelocity(player.getVelocity().multiply(1, multiplier, 1));
            player.playSound(DLC_Sounds.JETFALL_BURN, SoundCategory.PLAYERS, 0.21f, 1.3f + 0.2f * player.getRandom().nextFloat());
            world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), player.getVelocity().x + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())), player.getVelocity().y * (2 - multiplier), player.getVelocity().z + (multiplier * 0.25 * (0.5 - player.getRandom().nextFloat())));
            didSomething = true;
        }
    }

    @Override
    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {
        super.afterTick(stack, world, player, slot, selected);

        if (counter > 90) counter = 0;
        if (didSomething) counter++;
        didSomething = false;
    }

    @Override
    public int maxLevel() {
        return 2;
    }

    @Override
    public String id() {
        return "jetfall_boots";
    }
}
