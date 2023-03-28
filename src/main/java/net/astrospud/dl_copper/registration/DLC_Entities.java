package net.astrospud.dl_copper.registration;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.entities.MoltenCopperEntity;
import net.astrospud.dl_copper.entities.MoltenCopperEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DLC_Entities {
    public static EntityType<MoltenCopperEntity> MOLTEN_COPPER;

    public static void init() {
        DL_Copper.LOGGER.info("Entities - Registering");
        MOLTEN_COPPER = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(DL_Copper.MOD_ID, "molten_copper"),
                FabricEntityTypeBuilder.<MoltenCopperEntity>create(SpawnGroup.MISC, MoltenCopperEntity::new)
                .dimensions(EntityDimensions.fixed(0.75F, 0.25F))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .build());
        DL_Copper.LOGGER.info("Entities - Registered");
        registerRenderers();
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderers() {
        DL_Copper.LOGGER.info("Entity Rendering - Registering - I hate renderers.");
        EntityRendererRegistry.register(MOLTEN_COPPER, MoltenCopperEntityRenderer::new);
        DL_Copper.LOGGER.info("Entity Rendering - Registered");
    }
}
