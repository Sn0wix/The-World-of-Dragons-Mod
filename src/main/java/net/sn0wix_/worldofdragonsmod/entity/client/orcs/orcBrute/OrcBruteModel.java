package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBrute;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcBruteEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OrcBruteModel extends GeoModel<OrcBruteEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "geo/orc_brute.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/orc_brute/orc_brute.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/orc_brute/orc_brute_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "animations/orc_brute.animation.json");

    @Override
    public Identifier getModelResource(OrcBruteEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(OrcBruteEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(OrcBruteEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(OrcBruteEntity animatable, long instanceId, AnimationState<OrcBruteEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
