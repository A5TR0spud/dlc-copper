package net.astrospud.dl_copper.items.copper_armor.modules.boots;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.copper_armor.CopperArmorItem;
import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlamewakerModuleItem extends ModuleItem {
    BlockPos oldPos = null;

    public FlamewakerModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_BOOTS);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("DLC_INDEX", index);
        //immunity to   hot floor: 0
        //immunity to ground fire: 1
        if (index == 1 && player.isOnGround()) {
            world.addParticle(ParticleTypes.FLAME, player.getX()+0.3*(0.5 - player.getRandom().nextFloat()), player.getY(), player.getZ()+0.3*(0.5 - player.getRandom().nextFloat()), 0.05*(0.5 - player.getRandom().nextFloat()), 0.01, 0.05*(0.5 - player.getRandom().nextFloat()));
        }

        //floor fire
        if (index == 2) {
            BlockPos pos = player.getBlockPos();
            if (oldPos != null && oldPos != pos && world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).hasSolidTopSurface(world, pos.down(), player)) {
                stack.damage(1, player, (p) -> {
                    p.getInventory().setStack(slot, DLC_Items.EMPTY_MODULE.getDefaultStack());
                });
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }
            this.oldPos = pos;
        }
    }

    @Override
    public int maxLevel() {
        return 3;
    }

    @Override
    public String id() {
        return "flamewaker_boots";
    }
}
