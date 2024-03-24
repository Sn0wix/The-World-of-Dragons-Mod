package net.sn0wix_.worldofdragonsmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.GenericEntityModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.hostile.OrcModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.playerNPC.PlayerNPCRenderer;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileRenderer;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WorldOfDragonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //geckolib
        EntityRendererRegistry.register(ModEntities.ARMORED_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("armored_orc")));
        EntityRendererRegistry.register(ModEntities.GOBLIN, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("goblin")));
        EntityRendererRegistry.register(ModEntities.ARCHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("archer_orc")));
        EntityRendererRegistry.register(ModEntities.FLYER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("flyer_orc")));
        EntityRendererRegistry.register(ModEntities.ORC_BOSS, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_boss")));
        EntityRendererRegistry.register(ModEntities.ORC_BRUTE, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_brute")));
        EntityRendererRegistry.register(ModEntities.SLASHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("slasher_orc")));
        EntityRendererRegistry.register(ModEntities.ORC_MAGE, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_mage")));
        EntityRendererRegistry.register(ModEntities.LAVA_ELEMENTAL, ctx -> new GeoEntityRenderer<>(ctx, new GenericEntityModel<>("hostile/lava_elemental")));
        //EntityRendererRegistry.register(ModEntities.ORC_WARG, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_warg")));

        EntityRendererRegistry.register(ModEntities.SN0WIX_, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/sn0wix_.png"), true));
        EntityRendererRegistry.register(ModEntities.BUBBAGUMP7, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/bubbagump7.png"), false));
        EntityRendererRegistry.register(ModEntities.BOUQUETZ, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/bouquetz.png"), false));

        EntityRendererRegistry.register(ModEntities.IRON_CHEST_ENTITY, ctx -> new GeoEntityRenderer<>(ctx, new GenericEntityModel<>("misc/chests/iron_chest", "")));
        EntityRendererRegistry.register(ModEntities.COMMON_CHEST_ENTITY, ctx -> new GeoEntityRenderer<>(ctx, new GenericEntityModel<>("misc/chests/common_chest", "")));


        //mc rendering
        EntityModelLayerRegistry.registerModelLayer(ExplodingCubeProjectileModel.LAYER_LOCATION, ExplodingCubeProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.EXPLODING_MAGMA_PROJECTILE, ctx -> new ExplodingCubeProjectileRenderer(ctx, new Identifier("minecraft", "textures/block/magma.png")));
        ModPackets.registerS2CPackets();
    }
}
