package net.astrospud.dl_copper.items;

import net.astrospud.dl_copper.items.copper_armor.ModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatteryPackItem extends Item {
    public BatteryPackItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity player) {
            int r = chargeInventory(stack, world, entity, slot, selected, 1);
            if (r > 0) {
                stack.damage(r, player, (p) -> {
                    p.getInventory().setStack(slot, DLC_Items.BATTERY_PACK_EMPTY.getDefaultStack());
                });
            }
        }
    }

    public static int chargeInventory(ItemStack stack, World world, Entity entity, int slot, boolean selected, int charge) {
        if (!(entity instanceof PlayerEntity player))
            return 0;
        int r = 0;
        for (int i = 0; i < player.getInventory().size() && r < charge; i++) {
            ItemStack stack1 = player.getInventory().getStack(i);
            if (i != slot && stack1 != stack) {
                if (stack1.isDamaged() && stack1.getItem() instanceof ModuleItem) {
                    stack1.setDamage(stack1.getDamage() - 1);
                    r++;
                } else if (stack1.getItem() == DLC_Items.BATTERY_PACK_EMPTY) {
                    ItemStack initPack = DLC_Items.BATTERY_PACK.getDefaultStack();
                    initPack.setDamage(initPack.getMaxDamage() - 1);
                    player.getInventory().setStack(i, initPack);
                    r++;
                } else if (stack1.getItem() instanceof BatteryPackItem && stack.getItem() != DLC_Items.BATTERY_PACK) {
                    stack1.setDamage(stack1.getDamage() - 1);
                    r++;
                }
            }
        }
        return r;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.dl_copper.battery_pack").formatted(Formatting.GRAY));
    }
}
