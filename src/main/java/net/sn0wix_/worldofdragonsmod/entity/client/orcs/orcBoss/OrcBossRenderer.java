package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBoss;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcBossEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrcBossRenderer extends GeoEntityRenderer<OrcBossEntity> {
    public OrcBossRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OrcBossModel());
    }

    @Override
    public Identifier getTextureLocation(OrcBossEntity animatable) {
        return animatable.infected ? OrcBossModel.INFECTED_TEXTURE_RESOURCE : OrcBossModel.TEXTURE_RESOURCE;
    }
}
