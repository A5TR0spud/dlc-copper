package net.astrospud.dl_copper.items.copper_armor;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleItem extends Item {
    Item linkedItem;
    protected int counter = 0;
    boolean computer;
    protected boolean didSomething;
    public ModuleItem(Settings settings, Item armorItem, boolean isComputer) {
        super(settings.maxDamage(256));
        this.linkedItem = armorItem;
        this.computer = isComputer;
    }

    public ModuleItem(Settings settings, Item armorItem) {
        super(settings.maxDamage(256));
        this.linkedItem = armorItem;
        this.computer = false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.dl_copper.module").formatted(Formatting.GRAY));
        Text t = this.isComputer() ?
                Text.translatable("tooltip.dl_copper.comp." + this.maxLevel()).formatted(Formatting.GRAY)
                : Text.translatable("tooltip.dl_copper.max." + this.maxLevel()).formatted(Formatting.GRAY);
        tooltip.add(t);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        NbtCompound nbt = stack.getOrCreateNbt();

        if (nbt.contains("DLC_COUNTER")) {
            counter = nbt.getInt("DLC_COUNTER");
        }

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
            for (int i = 0; i <= slot && count < this.maxLevel(); i++) {
                ItemStack itemStack = player.getInventory().getStack(i);
                if (itemStack.getItem() instanceof ModuleItem mod && Objects.equals(mod.id(), this.id())) {
                    //if there's a computer in the inventory of the same type, do nothing
                    if (mod.isComputer() && slot != i)
                        break;

                    //this i == slot statement runs when the code has found its physical form
                    if (i == slot) {
                        if (this.isComputer()) {
                            for (int g = count; g < this.maxLevel(); g++) {
                                specialTick(stack, world, player, slot, selected, g);
                            }
                            if (count < this.maxLevel())
                                afterTick(stack, world, player, slot, selected);
                            break;
                        } else {
                            specialTick(stack, world, player, slot, selected, count);
                            afterTick(stack, world, player, slot, selected);
                        }
                    }
                    count++;
                }
            }
        }

        nbt.putInt("DLC_COUNTER", counter);
    }

    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int index) {

    }

    public void afterTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {

    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    public int maxLevel() {
        return 1;
    }

    public boolean isComputer() {
        return computer;
    }

    public String id() {
        return "";
    }
}
