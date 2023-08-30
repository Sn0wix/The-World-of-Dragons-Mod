package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBrute;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcBruteEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrcBruteRenderer extends GeoEntityRenderer<OrcBruteEntity> {
    public OrcBruteRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OrcBruteModel());
    }

    @Override
    public Identifier getTexture(OrcBruteEntity animatable) {
        return super.getTexture(animatable);
    }

    @Override
    public Identifier getTextureLocation(OrcBruteEntity animatable) {
        return animatable.infected ? OrcBruteModel.INFECTED_TEXTURE_RESOURCE : OrcBruteModel.TEXTURE_RESOURCE;
    }
}
