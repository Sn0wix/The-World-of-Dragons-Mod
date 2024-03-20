package net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.archerOrc;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.orcs.ArcherOrcEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArcherOrcModel extends GeoModel<ArcherOrcEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "geo/archer_orc.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/archer_orc/archer_orc.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/archer_orc/archer_orc_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "animations/archer_orc.animation.json");

    @Override
    public Identifier getModelResource(ArcherOrcEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(ArcherOrcEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(ArcherOrcEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(ArcherOrcEntity animatable, long instanceId, AnimationState<ArcherOrcEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
