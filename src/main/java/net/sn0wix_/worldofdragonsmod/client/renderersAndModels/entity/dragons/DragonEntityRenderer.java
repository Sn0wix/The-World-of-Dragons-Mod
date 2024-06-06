package net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.dragons;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.GenericGeoModel;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons.TameableDragonEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonEntityRenderer<T extends TameableDragonEntity> extends GeoEntityRenderer<T> {
    public static final String ARMOR_BONE_PREFIX = "armor";
    public static final String SADDLE_BONE_PREFIX = "saddle";

    public DragonEntityRenderer(EntityRendererFactory.Context renderManager, String name) {
        super(renderManager, new GenericGeoModel<>("dragons/" + name));
    }

    @Override
    public float getMotionAnimThreshold(T animatable) {
        return 0.012f;
    }

    @Override
    public void renderCubesOfBone(MatrixStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (bone.getName().contains(ARMOR_BONE_PREFIX) && !animatable.getDataTracker().get(TameableDragonEntity.HAS_ARMOR)) {
            return;
        }

        if (bone.getName().contains(SADDLE_BONE_PREFIX) && !animatable.getDataTracker().get(TameableDragonEntity.HAS_SADDLE)) {
            return;
        }

        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
