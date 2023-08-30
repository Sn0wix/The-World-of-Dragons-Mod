package net.sn0wix_.worldofdragonsmod.entity.client.orcs.archerOrc;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.ArcherOrcEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArcherOrcRenderer extends GeoEntityRenderer<ArcherOrcEntity> {
    public ArcherOrcRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArcherOrcModel());
    }

    @Override
    public Identifier getTextureLocation(ArcherOrcEntity animatable) {
        return animatable.infected ? ArcherOrcModel.INFECTED_TEXTURE_RESOURCE : ArcherOrcModel.TEXTURE_RESOURCE;
    }
}
