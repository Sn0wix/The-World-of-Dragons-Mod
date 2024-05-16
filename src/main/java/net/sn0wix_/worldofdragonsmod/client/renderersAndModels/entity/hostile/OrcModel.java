package net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.hostile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.GenericEntityModel;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs.ModOrcEntity;

@Environment(EnvType.CLIENT)
public class OrcModel<T extends ModOrcEntity> extends GenericEntityModel<T> {
    public final Identifier INFECTED_TEXTURE_RESOURCE;

    public OrcModel(String name) {
        this(name, "h_head");
    }

    public OrcModel(String name, String headBoneName) {
        super("hostile/orcs/" + name, "hostile/orcs/" + name + "/" + name, "hostile/orcs/" + name, headBoneName, true);
        INFECTED_TEXTURE_RESOURCE = new Identifier(WorldOfDragons.MOD_ID, "textures/entity/hostile/orcs/" + name + "/" + name + "_infected.png");
    }

    @Override
    public Identifier getTextureResource(T animatable) {
        return animatable.isInfected() ? INFECTED_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }
}
