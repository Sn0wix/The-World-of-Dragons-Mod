package net.sn0wix_.worldofdragonsmod.entity.client.orcs.flyerOrc;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.FlyerOrcEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FlyerOrcRenderer extends GeoEntityRenderer<FlyerOrcEntity> {
    public FlyerOrcRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FlyerOrcModel());
    }

    @Override
    public Identifier getTextureLocation(FlyerOrcEntity animatable) {
        return animatable.infected ? FlyerOrcModel.INFECTED_TEXTURE_RESOURCE : FlyerOrcModel.TEXTURE_RESOURCE;
    }
}
