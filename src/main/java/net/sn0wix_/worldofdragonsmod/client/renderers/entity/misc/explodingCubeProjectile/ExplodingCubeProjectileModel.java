package net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.explodingCubeProjectile;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class ExplodingCubeProjectileModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(WorldOfDragons.MOD_ID, "exploding_cube_entity"), "block");

    private final ModelPart block;

    public ExplodingCubeProjectileModel(ModelPart root) {
        this.block = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData block = modelPartData.addChild("block", ModelPartBuilder.create().uv(0, 0).cuboid(-8F, -8F, -8F, 16.0F, 16.0F, 16.0F, Dilation.NONE), ModelTransform.pivot(0f, 0, 0f));
        return TexturedModelData.of(modelData, 16, 16);
    }


    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        block.setPivot(0,8,0);
        block.yaw = animationProgress / 10;
        block.roll = animationProgress / 10;
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        block.render(matrices, vertices, light, overlay);
    }
}
