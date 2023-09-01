package net.sn0wix_.worldofdragonsmod.entity.client.orcs.slasherOrc;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.SlasherOrcEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SlasherOrcModel extends GeoModel<SlasherOrcEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "geo/slasher_orc.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/slasher_orc/slasher_orc.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/slasher_orc/slasher_orc_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "animations/slasher_orc.animation.json");

    @Override
    public Identifier getModelResource(SlasherOrcEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(SlasherOrcEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(SlasherOrcEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(SlasherOrcEntity animatable, long instanceId, AnimationState<SlasherOrcEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
