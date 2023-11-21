package net.sn0wix_.worldofdragonsmod.entity.client.misc.explodingCubeProjectile;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.misc.ExplodingCubeProjectile;

public class ExplodingCubeProjectileRenderer extends EntityRenderer<ExplodingCubeProjectile> {
    private final Identifier TEXTURE;
    private final ExplodingCubeProjectileModel<ExplodingCubeProjectile> model;

    public ExplodingCubeProjectileRenderer(EntityRendererFactory.Context context, Identifier textureLocation) {
        super(context);
        TEXTURE = textureLocation;
        this.model = new ExplodingCubeProjectileModel<>(context.getPart(ExplodingCubeProjectileModel.LAYER_LOCATION));
    }

    @Override
    public Identifier getTexture(ExplodingCubeProjectile entity) {
        return TEXTURE;
    }

    @Override
    public void render(ExplodingCubeProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        this.model.setAngles(entity, 0.0f, 0.0f, getBob(entity, tickDelta), 0.0f, 0.0f);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    protected float getBob(Entity entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }
}
