package net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class GenericEntityModel<T extends GeoAnimatable> extends GeoModel<T> {
    public final Identifier MODEL_RESOURCE;
    public final Identifier TEXTURE_RESOURCE;
    public final Identifier ANIMATION_RESOURCE;
    public final String HEAD_BONE_NAME;
    public final boolean USE_CUSTOM_ANIM;
    public GenericEntityModel(String resLoc) {
        this(resLoc, resLoc, resLoc, "h_head", true);
    }

    public GenericEntityModel(String resLoc, String headBoneName) {
        this(resLoc, resLoc, resLoc, headBoneName, true);
    }

    public GenericEntityModel(String resLoc, boolean useCustomAnim) {
        this(resLoc, resLoc, resLoc, "h_head", useCustomAnim);
    }

    public GenericEntityModel(String modelLoc, String textureLoc, String animLoc) {
        this(modelLoc, textureLoc, animLoc, "h_head", true);
    }

    public GenericEntityModel(String modelLoc, String textureLoc, String animLoc, String headBoneName, boolean useCustomAnim) {
        MODEL_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "geo/" + modelLoc + ".geo.json");
        TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/" + textureLoc + ".png");
        ANIMATION_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "animations/" + animLoc + ".animation.json");
        HEAD_BONE_NAME = headBoneName;
        USE_CUSTOM_ANIM = useCustomAnim;
    }

    @Override
    public Identifier getModelResource(T animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(T animatable) {
        return TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        if (USE_CUSTOM_ANIM) {
            CoreGeoBone head = this.getAnimationProcessor().getBone(HEAD_BONE_NAME);

            if (head != null) {
                EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
                head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
                head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
            }
        }
    }
}
