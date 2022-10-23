package net.astrospud.dl_copper.items.copper_armor;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModuleItem extends Item {
    Item linkedItem;
    int count = 0;
    public ModuleItem(Settings settings, Item armorItem) {
        super(settings.maxDamage(256));
        this.linkedItem = armorItem;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.dl_copper.module").formatted(Formatting.GRAY));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player) {
            ArrayList<ItemStack> armor = new ArrayList<>();
            entity.getArmorItems().forEach(armor::add);
            boolean wearing = false;
            for (ItemStack itemStack : armor) {
                if (itemStack.getItem() == this.linkedItem) {
                    wearing = true;
                    break;
                }
            }
            if (!wearing) return;

            int count = 0;
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i).getItem() == this.asItem()) {
                    if (i == slot) {
                        specialTick(stack, world, player, slot, selected, count);
                    }
                    count++;
                }
            }
            this.count = count;
        }
    }

    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {

    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }
}
