package net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBoss;

import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.OrcBossEntity;
import software.bernie.geckolib.model.GeoModel;

public class OrcBossModel extends GeoModel<OrcBossEntity> {
    public static final Identifier MODEL_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "geo/orc_boss.geo.json");
    public static final Identifier TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/orc_boss/orc_boss.png");
    public static final Identifier INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "textures/entity/orc_boss/orc_boss_infected.png");
    public static final Identifier ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, "animations/orc_boss.animation.json");


    @Override
    public Identifier getModelResource(OrcBossEntity animatable) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(OrcBossEntity animatable) {
        return animatable.infected ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public Identifier getAnimationResource(OrcBossEntity animatable) {
        return ANIMATION_RESOURCE;
    }
}
