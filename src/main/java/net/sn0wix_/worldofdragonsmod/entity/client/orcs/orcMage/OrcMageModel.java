package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcMage;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcMageEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OrcMageModel extends GeoModel<OrcMageEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "geo/orc_mage.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/orc_mage/orc_mage.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/orc_mage/orc_mage_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMod.MOD_ID, "animations/orc_mage.animation.json");

    @Override
    public Identifier getModelResource(OrcMageEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(OrcMageEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(OrcMageEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setCustomAnimations(OrcMageEntity animatable, long instanceId, AnimationState<OrcMageEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("h_head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
