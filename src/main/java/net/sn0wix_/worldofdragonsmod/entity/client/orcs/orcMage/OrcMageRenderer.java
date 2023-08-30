package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcMage;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcMageEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrcMageRenderer extends GeoEntityRenderer<OrcMageEntity> {
    public OrcMageRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OrcMageModel());
    }

    @Override
    public Identifier getTextureLocation(OrcMageEntity animatable) {
        return animatable.infected ? OrcMageModel.INFECTED_TEXTURE_RESOURCE : OrcMageModel.TEXTURE_RESOURCE;
    }
}
