package net.sn0wix_.worldofdragonsmod.client.renderers.entity.hostile.lavaElemental;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.LavaElementalEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class LavaElementalModel extends GeoModel<LavaElementalEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "geo/lava_elemental.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/lava_elemental/lava_elemental.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "animations/lava_elemental.animation.json");

    @Override
    public Identifier getModelResource(LavaElementalEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(LavaElementalEntity animatable) {
        return TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(LavaElementalEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(LavaElementalEntity animatable, long instanceId, AnimationState<LavaElementalEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
