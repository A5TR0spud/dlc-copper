package net.astrospud.dl_copper.registration;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.BarometerModelPredicates;
import net.astrospud.dl_copper.items.CopperHammerItem;
import net.astrospud.dl_copper.items.copper_armor.CopperArmorItem;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.FlamewakerModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.JetfallModuleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DLC_Items {
    public static Item BAROMETER;
    public static Item FLAMEWAKER_MODULE_BOOTS;
    public static Item JETFALL_MODULE_BOOTS;
    public static Item COPPER_BOOTS = registerItem(new CopperArmorItem(EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.TOOLS)), "copper_boots");
    public static Item COPPER_LEGGINGS = registerItem(new CopperArmorItem(EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.TOOLS)), "copper_leggings");
    public static Item COPPER_HAMMER;
    public static Item EMPTY_MODULE;

    public static Item registerItem(Item item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(DL_Copper.MOD_ID, name), item);
    }

    public static void init() {
        DL_Copper.LOGGER.info("Items - Registering");
        BAROMETER = registerItem(new Item(new FabricItemSettings().group(ItemGroup.TOOLS)), "barometer");
        BarometerModelPredicates.register();
        FLAMEWAKER_MODULE_BOOTS = registerItem(new FlamewakerModuleItem(new FabricItemSettings()), "flamewaker_module_boots");
        JETFALL_MODULE_BOOTS = registerItem(new JetfallModuleItem(new FabricItemSettings().group(ItemGroup.TOOLS)), "jetfall_module_boots");
        COPPER_HAMMER = registerItem(new CopperHammerItem(), "copper_hammer");
        EMPTY_MODULE = registerItem(new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), "empty_module");
        DL_Copper.LOGGER.info("Items - Registered");
    }
}
