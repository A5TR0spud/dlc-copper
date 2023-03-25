package net.astrospud.dl_copper.registration;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.BarometerModelPredicates;
import net.astrospud.dl_copper.items.BatteryPackItem;
import net.astrospud.dl_copper.items.CopperHammerItem;
import net.astrospud.dl_copper.items.copper_armor.CopperArmorItem;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.FlamewakerModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.FlipperModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.JetfallModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.greaves.LeaperModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.helmet.GillsModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.helmet.SatiatorModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.plating.VitalsModuleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DLC_Items {
    public static Item BAROMETER;
    public static Item FLAMEWAKER_MODULE_BOOTS;
    public static Item JETFALL_MODULE_BOOTS;
    public static Item LEAPER_MODULE_LEGGINGS;
    public static Item VITALS_MODULE_PLATING;
    public static Item FLIPPER_MODULE_BOOTS;
    public static Item GILLS_MODULE_HELMET;
    public static Item SATIATOR_MODULE_HELMET;
    public static Item COPPER_HELMET = registerItem(new CopperArmorItem(ArmorItem.Type.HELMET, new FabricItemSettings()), "copper_helmet");
    public static Item COPPER_CHESTPLATE = registerItem(new CopperArmorItem(ArmorItem.Type.CHESTPLATE, new FabricItemSettings()), "copper_chestplate");
    public static Item COPPER_LEGGINGS = registerItem(new CopperArmorItem(ArmorItem.Type.LEGGINGS, new FabricItemSettings()), "copper_leggings");
    public static Item COPPER_BOOTS = registerItem(new CopperArmorItem(ArmorItem.Type.BOOTS, new FabricItemSettings()), "copper_boots");
    public static Item BATTERY_PACK;
    public static Item BATTERY_PACK_EMPTY;
    public static Item COPPER_HAMMER;
    public static Item EMPTY_MODULE;

    public static Item registerItem(Item item, String name) {
        return Registry.register(Registries.ITEM, new Identifier(DL_Copper.MOD_ID, name), item);
    }

    public static void init() {
        DL_Copper.LOGGER.info("Items - Registering");
        BAROMETER = registerItem(new Item(new FabricItemSettings()), "barometer");
        BarometerModelPredicates.register();
        COPPER_HAMMER = registerItem(new CopperHammerItem(), "copper_hammer");
        BATTERY_PACK = registerItem(new BatteryPackItem(new FabricItemSettings().maxDamage(256)), "battery_pack");
        BATTERY_PACK_EMPTY = registerItem(new Item(new FabricItemSettings().maxCount(1)), "battery_pack_empty");
        EMPTY_MODULE = registerItem(new Item(new FabricItemSettings()), "empty_module");
        FLAMEWAKER_MODULE_BOOTS = registerItem(new FlamewakerModuleItem(new FabricItemSettings()), "flamewaker_module_boots");
        JETFALL_MODULE_BOOTS = registerItem(new JetfallModuleItem(new FabricItemSettings()), "jetfall_module_boots");
        LEAPER_MODULE_LEGGINGS = registerItem(new LeaperModuleItem(new FabricItemSettings()), "leaper_module_leggings");
        VITALS_MODULE_PLATING = registerItem(new VitalsModuleItem(new FabricItemSettings()), "vitals_module_plating");
        FLIPPER_MODULE_BOOTS = registerItem(new FlipperModuleItem(new FabricItemSettings()), "flipper_module_boots");
        GILLS_MODULE_HELMET = registerItem(new GillsModuleItem(new FabricItemSettings()), "gills_module_helmet");
        SATIATOR_MODULE_HELMET = registerItem(new SatiatorModuleItem(new FabricItemSettings()), "satiator_module_helmet");
        DL_Copper.LOGGER.info("Items - Registered");
        DL_Copper.LOGGER.info("Item Groups - Registering");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(Items.CLOCK, BAROMETER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(Items.STONE_HOE, COPPER_HAMMER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addBefore(Items.BUCKET, COPPER_HELMET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(COPPER_HELMET, COPPER_CHESTPLATE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(COPPER_CHESTPLATE, COPPER_LEGGINGS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(COPPER_LEGGINGS, COPPER_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.NETHERITE_INGOT, EMPTY_MODULE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(BATTERY_PACK);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(BATTERY_PACK, SATIATOR_MODULE_HELMET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(SATIATOR_MODULE_HELMET, GILLS_MODULE_HELMET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(VITALS_MODULE_PLATING);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(LEAPER_MODULE_LEGGINGS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(FLAMEWAKER_MODULE_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(FLAMEWAKER_MODULE_BOOTS, JETFALL_MODULE_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(JETFALL_MODULE_BOOTS, FLIPPER_MODULE_BOOTS);
        });
        DL_Copper.LOGGER.info("Item Groups - Registered");
    }
}
