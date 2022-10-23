package net.astrospud.dl_copper.items;

import net.astrospud.dl_copper.items.materials.DLCToolMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperHammerItem extends MiningToolItem {
    static ToolMaterial material = DLCToolMaterials.COPPER;
    static float attackDamage = 2;
    static float attackSpeed = -3.2F;
    static Settings settings = (new Item.Settings()).group(ItemGroup.TOOLS).fireproof().maxDamage(2048);

    public CopperHammerItem() {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings.maxDamage(190));
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(BlockTags.PICKAXE_MINEABLE) || state.isIn(BlockTags.SHOVEL_MINEABLE) ? this.miningSpeed : 1.0F;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        int i = this.getMaterial().getMiningLevel();
        if (i < 3 && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return (i >= 1 || !state.isIn(BlockTags.NEEDS_STONE_TOOL)) && (state.isIn(BlockTags.PICKAXE_MINEABLE) || state.isIn(BlockTags.SHOVEL_MINEABLE));
        }
    }
}
