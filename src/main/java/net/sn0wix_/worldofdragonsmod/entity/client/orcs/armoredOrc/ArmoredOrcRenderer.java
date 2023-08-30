package net.sn0wix_.worldofdragonsmod.entity.client.orcs.armoredOrc;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.ArmoredOrcEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArmoredOrcRenderer extends GeoEntityRenderer<ArmoredOrcEntity> {
    public ArmoredOrcRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArmoredOrcModel());
    }

    @Override
    public Identifier getTextureLocation(ArmoredOrcEntity animatable) {
        return animatable.infected ? ArmoredOrcModel.INFECTED_TEXTURE_RESOURCE : ArmoredOrcModel.TEXTURE_RESOURCE;
    }
}
