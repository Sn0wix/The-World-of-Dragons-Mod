package net.sn0wix_.worldofdragonsmod.entity.client.orcs.slasherOrc;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.SlasherOrcEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SlasherOrcRenderer extends GeoEntityRenderer<SlasherOrcEntity> {
    public SlasherOrcRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SlasherOrcModel());
    }

    @Override
    public Identifier getTextureLocation(SlasherOrcEntity animatable) {
        return animatable.infected ? SlasherOrcModel.INFECTED_TEXTURE_RESOURCE : SlasherOrcModel.TEXTURE_RESOURCE;
    }
}
