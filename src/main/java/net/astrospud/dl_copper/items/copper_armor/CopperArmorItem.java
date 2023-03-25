package net.astrospud.dl_copper.items.copper_armor;

import net.astrospud.dl_copper.items.materials.DLCArmorMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperArmorItem extends ArmorItem {
    static ArmorMaterial material = DLCArmorMaterials.COPPER;

    public CopperArmorItem(Type type, Settings settings) {
        super(material, type, settings);
    }


    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.dl_copper.copper_armor").formatted(Formatting.GRAY));
    }
}
