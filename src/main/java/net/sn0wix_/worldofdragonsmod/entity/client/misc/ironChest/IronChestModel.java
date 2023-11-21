package net.sn0wix_.worldofdragonsmod.entity.client.misc.ironChest;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.entity.custom.misc.IronChestEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class IronChestModel extends GeoModel<IronChestEntity> {
    @Override
    public Identifier getModelResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMain.MOD_ID, "geo/iron_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/iron_chest/iron_chest.png");
    }

    @Override
    public Identifier getAnimationResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMain.MOD_ID, "animations/iron_chest.animation.json");
    }

    @Override
    public void setCustomAnimations(IronChestEntity animatable, long instanceId, AnimationState<IronChestEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("chest_iron");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
