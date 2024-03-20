package net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.orcWarg;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.orcs.OrcWargEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OrcWargModel extends GeoModel<OrcWargEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "geo/orc_warg.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/orc_warg/orc_warg.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/orc_warg/orc_warg_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "animations/orc_warg.animation.json");

    @Override
    public Identifier getModelResource(OrcWargEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(OrcWargEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(OrcWargEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(OrcWargEntity animatable, long instanceId, AnimationState<OrcWargEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
