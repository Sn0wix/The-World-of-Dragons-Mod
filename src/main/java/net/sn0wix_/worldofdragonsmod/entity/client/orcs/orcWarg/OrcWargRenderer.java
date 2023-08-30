package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcWarg;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcWargEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrcWargRenderer extends GeoEntityRenderer<OrcWargEntity> {
    public OrcWargRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OrcWargModel());
    }

    @Override
    public Identifier getTextureLocation(OrcWargEntity animatable) {
        return animatable.infected ? OrcWargModel.INFECTED_TEXTURE_RESOURCE : OrcWargModel.TEXTURE_RESOURCE;
    }
}
