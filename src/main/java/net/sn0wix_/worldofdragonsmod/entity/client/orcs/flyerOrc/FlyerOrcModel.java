package net.sn0wix_.worldofdragonsmod.entity.client.orcs.flyerOrc;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.FlyerOrcEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FlyerOrcModel extends GeoModel<FlyerOrcEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "geo/flyer_orc.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/flyer_orc/flyer_orc.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/flyer_orc/flyer_orc_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "animations/flyer_orc.animation.json");

    @Override
    public Identifier getModelResource(FlyerOrcEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(FlyerOrcEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(FlyerOrcEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(FlyerOrcEntity animatable, long instanceId, AnimationState<FlyerOrcEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
