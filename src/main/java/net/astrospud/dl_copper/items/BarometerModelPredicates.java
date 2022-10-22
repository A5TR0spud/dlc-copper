package net.astrospud.dl_copper.items;

import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

import javax.annotation.Nullable;

public class BarometerModelPredicates {
    public static void register(){
        ModelPredicateProviderRegistry.register(DLC_Items.BAROMETER, new Identifier("barometer_weather"), BarometerModelPredicates::barometerPredicateProvider);
    }

    public static float barometerPredicateProvider(ItemStack stack, @Nullable ClientWorld w, @Nullable LivingEntity e, int seed) {
        Entity entity = e;
        World world = w;

        //0: blank
        float weather = 0;

        if (entity == null) {
            entity = stack.getHolder();
        }

        if (world == null && entity != null) {
            world = entity.getWorld();
        }

        if (world == null || entity == null) {
            return 0.875f;
        }

        //7: unknown
        if (world.getDimensionKey() != DimensionTypes.THE_END
        && world.getDimensionKey() != DimensionTypes.OVERWORLD
        && world.getDimensionKey() != DimensionTypes.OVERWORLD_CAVES
        && world.getDimensionKey() != DimensionTypes.THE_NETHER) {
            weather = 0.875f;
        }

        //1: rain
        if (world.isRaining()) {
            weather = 0.125f;
        }
        //2: thunder
        if (world.isThundering()) {
            weather = 0.25f;
        }
        //3: snow
        if (world.isRaining() && !world.getBiome(entity.getBlockPos()).value().doesNotSnow(entity.getBlockPos())) {
            weather = 0.375f;
        }
        //4: hell
        if (world.getDimension().ultrawarm()) {
            weather = 0.5f;
        }
        //5: pressure (void or seafloor)
        if (entity.getY() < world.getBottomY() || (entity.isSubmergedInWater() && entity.getY() < world.getSeaLevel()-30)) {
            weather = 0.625f;
        }
        //6: end
        if (world.getDimensionKey() == DimensionTypes.THE_END) {
            weather = 0.75f;
        }
        return weather;
    }
}
