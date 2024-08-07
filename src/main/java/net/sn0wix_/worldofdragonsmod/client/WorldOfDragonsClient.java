package net.sn0wix_.worldofdragonsmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.client.events.ClientEvents;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.block.entity.MassiveDoorModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.GenericGeoModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.dragons.DragonEntityRenderer;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.hostile.OrcModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.BlockWaveFallingBlockEntityRenderer;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.PlaceableEntityRenderer;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.PlayerNPCRenderer;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.blocks.entity.ModBlockEntities;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileModel;
import net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileRenderer;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.ServerKeyBind;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ChestEntity;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldOfDragonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //Entities
        //Geckolib
        EntityRendererRegistry.register(ModEntities.SHELL_SMASHER, ctx -> new DragonEntityRenderer<>(ctx, "shell_smasher"));

        EntityRendererRegistry.register(ModEntities.ARMORED_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("armored_orc")));
        EntityRendererRegistry.register(ModEntities.GOBLIN, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("goblin")));
        EntityRendererRegistry.register(ModEntities.ARCHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("archer_orc")));
        EntityRendererRegistry.register(ModEntities.FLYER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("flyer_orc")));
        EntityRendererRegistry.register(ModEntities.ORC_BOSS, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_boss")));
        EntityRendererRegistry.register(ModEntities.ORC_BRUTE, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_brute")));
        EntityRendererRegistry.register(ModEntities.SLASHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("slasher_orc")));
        EntityRendererRegistry.register(ModEntities.ORC_MAGE, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_mage")));
        EntityRendererRegistry.register(ModEntities.LAVA_ELEMENTAL, ctx -> new GeoEntityRenderer<>(ctx, new GenericGeoModel<>("hostile/lava_elemental")));
        EntityRendererRegistry.register(ModEntities.ORC_WARG, ctx -> new GeoEntityRenderer<>(ctx, new OrcModel<>("orc_warg")));

        EntityRendererRegistry.register(ModEntities.SNAPPER, ctx -> new GeoEntityRenderer<>(ctx, new GenericGeoModel<>("hostile/snapper")));

        //Player npcs
        EntityRendererRegistry.register(ModEntities.SN0WIX_, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/sn0wix_.png"), true));
        EntityRendererRegistry.register(ModEntities.BUBBAGUMP7, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/bubbagump7.png"), false));
        EntityRendererRegistry.register(ModEntities.BOUQUETZ, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/misc/players/bouquetz.png"), false));

        //Chests
        EntityRendererRegistry.register(ModEntities.IRON_CHEST, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/chests/iron_chest")) {
            @Override
            public void actuallyRender(MatrixStack poseStack, ChestEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
                poseStack.scale(0.7f, 0.7f, 0.7f);
                super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            }
        });
        EntityRendererRegistry.register(ModEntities.COMMON_CHEST, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/chests/common_chest")));
        EntityRendererRegistry.register(ModEntities.GOLDEN_CHEST, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/chests/golden_chest")));

        //Eggs
        EntityRendererRegistry.register(ModEntities.SHELL_SMASHER_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/shell_smasher_egg")));
        EntityRendererRegistry.register(ModEntities.FINFLAPPER_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/finflapper_egg")));
        EntityRendererRegistry.register(ModEntities.NAGA_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/naga_egg")));
        EntityRendererRegistry.register(ModEntities.RALAK_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/ralak_egg")));
        EntityRendererRegistry.register(ModEntities.SANDSPITTER_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/sandspitter_egg")));
        EntityRendererRegistry.register(ModEntities.SKYSERPENT_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/skyserpent_egg")));
        EntityRendererRegistry.register(ModEntities.VOLCANIC_WINGWALKER_EGG, ctx -> new PlaceableEntityRenderer<>(ctx, new GenericGeoModel<>("misc/eggs/volcanic_wingwalker_egg")));


        //Mc rendering
        EntityModelLayerRegistry.registerModelLayer(ExplodingCubeProjectileModel.LAYER_LOCATION, ExplodingCubeProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.EXPLODING_MAGMA_PROJECTILE, ctx -> new ExplodingCubeProjectileRenderer(ctx, new Identifier("minecraft", "textures/block/magma.png")));
        EntityRendererRegistry.register(ModEntities.BLOCK_WAVE_FALLING_BLOCK, BlockWaveFallingBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.THE_ROCK_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.THE_LAVA_ROCK_PROJECTILE, FlyingItemEntityRenderer::new);

        //Block entities
        BlockEntityRendererFactories.register(ModBlockEntities.MASSIVE_DOOR, ctx -> new GeoBlockRenderer<>(new MassiveDoorModel()) {
            @Override
            public int getRenderDistance() {
                return MinecraftClient.getInstance().options.getViewDistance().getValue() * 16;
            }
        });

        //Networking
        ModPackets.registerS2CPackets();

        //Events
        ClientEvents.registerEvents();
    }
}
