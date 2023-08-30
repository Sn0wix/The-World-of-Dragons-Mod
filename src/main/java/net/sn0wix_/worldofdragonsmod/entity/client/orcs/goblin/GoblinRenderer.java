package net.sn0wix_.worldofdragonsmod.entity.client.orcs.goblin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.GoblinEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GoblinRenderer extends GeoEntityRenderer<GoblinEntity> {
    public GoblinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GoblinModel());
    }

    @Override
    public Identifier getTextureLocation(GoblinEntity animatable) {
        return animatable.infected ? GoblinModel.INFECTED_TEXTURE_RESOURCE : GoblinModel.TEXTURE_RESOURCE;
    }
}
