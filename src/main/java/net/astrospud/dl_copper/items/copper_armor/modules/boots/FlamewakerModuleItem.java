package net.astrospud.dl_copper.items.copper_armor.modules.boots;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.copper_armor.CopperArmorItem;
import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlamewakerModuleItem extends ModuleItem {
    BlockPos oldPos = null;

    public FlamewakerModuleItem(Settings settings) {
        super(settings.maxDamage(256), DLC_Items.COPPER_BOOTS);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {
        if (index == 2) {
            BlockPos pos = player.getBlockPos();
            if (oldPos != null && oldPos != pos && world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).hasSolidTopSurface(world, pos.down(), player)) {
                stack.damage(1, player, (p) -> {
                    p.getInventory().setStack(slot, ItemStack.EMPTY);
                });
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }
            this.oldPos = pos;
        }
    }
}
