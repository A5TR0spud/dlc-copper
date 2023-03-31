package net.astrospud.dl_copper.items.copper_armor.modules.plating;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.astrospud.dl_copper.registration.DLC_Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class JetpackModuleItem extends ModuleItem {
    boolean side = false;
    boolean lastTickThrust = false;
    public JetpackModuleItem(Settings settings, boolean isComputer) {
        super(settings, DLC_Items.COPPER_CHESTPLATE, isComputer);
    }

    public JetpackModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_CHESTPLATE);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        didSomething = false;
        if (index <= 1) {
            if (!player.isOnGround() && !player.getAbilities().flying && !player.hasStatusEffect(StatusEffects.LEVITATION) && !player.isSwimming() && !player.isSubmergedInWater()) {
                if (MinecraftClient.getInstance().player != null
                        && MinecraftClient.getInstance().player.input.jumping) {
                    float yaw = player.bodyYaw;
                    float width = player.getWidth();

                    if (!lastTickThrust)
                        player.playSound(DLC_Sounds.JETPACK_IGNITE, SoundCategory.PLAYERS, 0.45f, 0.9f + 0.2f * player.getRandom().nextFloat());
                    else
                        player.playSound(DLC_Sounds.JETPACK_BURN, SoundCategory.PLAYERS, 0.275f, 0.9f + 0.2f * player.getRandom().nextFloat());

                    if (player.getVelocity().y < 0.3) {
                        player.addVelocity(0, 0.08, 0);
                    }
                    lastTickThrust = true;
                    if (index == 0) {
                        world.addParticle(ParticleTypes.FLAME.getType(), player.getX() + 0.5 * width * MathHelper.sin(yaw * 0.017453292F) + (side ? 0.36 : -0.36) * width * MathHelper.cos(yaw * 0.017453292F), player.getY() + player.getBoundingBox().getYLength() * 0.68f, player.getZ() - 0.5 * width * MathHelper.cos(yaw * 0.017453292F) + (side ? 0.36 : -0.36) * width * MathHelper.sin(yaw * 0.017453292F), 0, -1, 0);
                        side = !side;
                        world.addParticle(ParticleTypes.FLAME.getType(), player.getX() + 0.5 * width * MathHelper.sin(yaw * 0.017453292F) + (side ? 0.36 : -0.36) * width * MathHelper.cos(yaw * 0.017453292F), player.getY() + player.getBoundingBox().getYLength() * 0.68f, player.getZ() - 0.5 * width * MathHelper.cos(yaw * 0.017453292F) + (side ? 0.36 : -0.36) * width * MathHelper.sin(yaw * 0.017453292F), 0, -1, 0);
                        side = !side;
                        player.fallDistance *= 0.9;
                    }
                    if (index == 1)
                        player.fallDistance *= 0.3;
                    didSomething = true;
                    if (counter == 1) {
                        stack.damage(1, player, (p) -> {
                            p.getInventory().setStack(slot, getBrokenState(stack));
                        });
                    }
                }
            }
            if (MinecraftClient.getInstance().player != null
                    && !MinecraftClient.getInstance().player.input.jumping) {
                lastTickThrust = false;
            }
        }
    }

    @Override
    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {
        super.afterTick(stack, world, player, slot, selected);

        if (counter > 3) counter = 0;
        if (didSomething) counter++;
    }

    @Override
    public int maxLevel() {
        return 2;
    }

    @Override
    public String id() {
        return "jetpack_plating";
    }
}
