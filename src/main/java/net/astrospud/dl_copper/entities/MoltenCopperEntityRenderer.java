package net.astrospud.dl_copper.entities;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MoltenCopperEntityRenderer extends EntityRenderer<MoltenCopperEntity> {
    public MoltenCopperEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(MoltenCopperEntity entity) {
        return null;
    }
}
