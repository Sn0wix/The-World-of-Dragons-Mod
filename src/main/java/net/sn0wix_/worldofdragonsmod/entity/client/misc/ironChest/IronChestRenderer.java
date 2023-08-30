package net.sn0wix_.worldofdragonsmod.entity.client.misc.ironChest;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.misc.IronChestEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IronChestRenderer extends GeoEntityRenderer<IronChestEntity> {
    public IronChestRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new IronChestModel());
    }

    @Override
    public Identifier getTextureLocation(IronChestEntity animatable) {
        return new Identifier(WorldOfDragonsMod.MOD_ID, "textures/entity/iron_chest/iron_chest.png");
    }
}
