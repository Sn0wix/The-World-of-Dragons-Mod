package net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.ironChest;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;
import software.bernie.geckolib.model.GeoModel;

public class ModChestEntityModel extends GeoModel<ModChestEntity> {
    public final Identifier MODEL_RESOURCE;
    public final Identifier TEXTURE_RESOURCE;
    public final Identifier ANIMATION_RESOURCE;

    public ModChestEntityModel(String model, String texture, String animations) {
        MODEL_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, model);
        TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, texture);
        ANIMATION_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, animations);
    }

    @Override
    public Identifier getModelResource(ModChestEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(ModChestEntity animatable) {
        return TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(ModChestEntity animatable) {
        return ANIMATION_RESOURCE;
    }
}
