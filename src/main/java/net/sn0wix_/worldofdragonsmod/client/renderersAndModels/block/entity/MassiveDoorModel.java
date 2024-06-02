package net.sn0wix_.worldofdragonsmod.client.renderersAndModels.block.entity;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.blocks.entity.MassiveDoorBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class MassiveDoorModel extends GeoModel<MassiveDoorBlockEntity> {
    @Override
    public Identifier getModelResource(MassiveDoorBlockEntity animatable) {
        return new Identifier(WorldOfDragons.MOD_ID, "geo/misc/massive_door.geo.json");
    }

    @Override
    public Identifier getTextureResource(MassiveDoorBlockEntity animatable) {
        return new Identifier(WorldOfDragons.MOD_ID, "textures/block/massive_door.png");
    }

    @Override
    public Identifier getAnimationResource(MassiveDoorBlockEntity animatable) {
        return new Identifier(WorldOfDragons.MOD_ID, "animations/misc/massive_door.animation.json");
    }
}
