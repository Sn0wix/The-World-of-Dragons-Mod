package net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.goblin;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.orcs.GoblinEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GoblinModel extends GeoModel<GoblinEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "geo/goblin.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/goblin/goblin.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/goblin/goblin_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "animations/goblin.animation.json");

    @Override
    public Identifier getModelResource(GoblinEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(GoblinEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(GoblinEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(GoblinEntity animatable, long instanceId, AnimationState<GoblinEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
