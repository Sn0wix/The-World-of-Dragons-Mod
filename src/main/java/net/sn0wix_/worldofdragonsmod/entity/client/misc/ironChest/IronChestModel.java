package net.sn0wix_.worldofdragonsmod.entity.client.misc.ironChest;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.misc.IronChestEntity;
import software.bernie.geckolib.model.GeoModel;

public class IronChestModel extends GeoModel<IronChestEntity> {
    @Override
    public Identifier getModelResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMod.MOD_ID, "geo/iron_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/iron_chest/iron_chest.png");
    }

    @Override
    public Identifier getAnimationResource(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMod.MOD_ID, "animations/iron_chest.animation.json");
    }
}
