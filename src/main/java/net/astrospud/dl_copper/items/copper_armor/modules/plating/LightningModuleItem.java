package net.astrospud.dl_copper.items.copper_armor.modules.plating;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class LightningModuleItem extends ModuleItem {
    //this ditch empty!
    public LightningModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_CHESTPLATE);
    }
    public LightningModuleItem(Settings settings, boolean isComputer) {
        super(settings, DLC_Items.COPPER_CHESTPLATE, isComputer);
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {

    }

    @Override
    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {

    }

    @Override
    public int maxLevel() {
        return 3;
    }

    @Override
    public String id() {
        return "lightning_plating";
    }
}
