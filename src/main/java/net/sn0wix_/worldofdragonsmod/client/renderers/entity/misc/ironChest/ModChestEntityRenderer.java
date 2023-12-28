package net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.ironChest;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;
import software.bernie.geckolib.model.GeoModel;

public class ModChestEntityRenderer extends GeoModel<ModChestEntity> {
    @Override
    public Identifier getModelResource(ModChestEntity animatable) {
        return animatable.getModelResource();
    }

    @Override
    public Identifier getTextureResource(ModChestEntity animatable) {
        return animatable.getTextureResource();
    }

    @Override
    public Identifier getAnimationResource(ModChestEntity animatable) {
        return animatable.getAnimationResource();
    }
}
