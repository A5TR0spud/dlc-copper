package net.astrospud.dl_copper.items.copper_armor.modules.helmet;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.item.Item;

public class NightVisionModuleItem extends ModuleItem {
    public NightVisionModuleItem(Settings settings, boolean isComputer) {
        super(settings, DLC_Items.COPPER_HELMET, isComputer);
    }

    public NightVisionModuleItem(Settings settings) {
        super(settings, DLC_Items.COPPER_HELMET);
    }

    @Override
    public String id() {
        return "night_vision_helmet";
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
