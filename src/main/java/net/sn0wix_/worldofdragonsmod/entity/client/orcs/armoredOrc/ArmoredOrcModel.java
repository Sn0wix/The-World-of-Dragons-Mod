package net.sn0wix_.worldofdragonsmod.entity.client.orcs.armoredOrc;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.ArmoredOrcEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArmoredOrcModel extends GeoModel<ArmoredOrcEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "geo/armored_orc.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/armored_orc/armored_orc.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/armored_orc/armored_orc_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "animations/armored_orc.animation.json");

    @Override
    public Identifier getModelResource(ArmoredOrcEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(ArmoredOrcEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(ArmoredOrcEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(ArmoredOrcEntity animatable, long instanceId, AnimationState<ArmoredOrcEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
