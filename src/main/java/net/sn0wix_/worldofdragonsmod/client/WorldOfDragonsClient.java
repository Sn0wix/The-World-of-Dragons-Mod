package net.sn0wix_.worldofdragonsmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.playerNPC.PlayerNPCRenderer;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.hostile.lavaElemental.LavaElementalModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.explodingCubeProjectile.ExplodingCubeProjectileRenderer;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.misc.ironChest.ModChestEntityModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.archerOrc.ArcherOrcModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.armoredOrc.ArmoredOrcModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.flyerOrc.FlyerOrcModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.goblin.GoblinModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.orcBoss.OrcBossModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.orcBrute.OrcBruteModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.orcMage.OrcMageModel;
import net.sn0wix_.worldofdragonsmod.client.renderers.entity.orcs.slasherOrc.SlasherOrcModel;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WorldOfDragonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.ARMORED_ORC, ctx -> new GeoEntityRenderer<>(ctx, new ArmoredOrcModel()));
        EntityRendererRegistry.register(ModEntities.GOBLIN, ctx -> new GeoEntityRenderer<>(ctx, new GoblinModel()));
        EntityRendererRegistry.register(ModEntities.ARCHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new ArcherOrcModel()));
        EntityRendererRegistry.register(ModEntities.FLYER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new FlyerOrcModel()));
        EntityRendererRegistry.register(ModEntities.ORC_BOSS, ctx -> new GeoEntityRenderer<>(ctx, new OrcBossModel()));
        EntityRendererRegistry.register(ModEntities.ORC_BRUTE, ctx -> new GeoEntityRenderer<>(ctx, new OrcBruteModel()));
        EntityRendererRegistry.register(ModEntities.SLASHER_ORC, ctx -> new GeoEntityRenderer<>(ctx, new SlasherOrcModel()));
        EntityRendererRegistry.register(ModEntities.ORC_MAGE, ctx -> new GeoEntityRenderer<>(ctx, new OrcMageModel()));
        EntityRendererRegistry.register(ModEntities.LAVA_ELEMENTAL, ctx -> new GeoEntityRenderer<>(ctx, new LavaElementalModel()));
        //EntityRendererRegistry.register(ModEntities.ORC_WARG, OrcWargRenderer::new);

        EntityRendererRegistry.register(ModEntities.SN0WIX_, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/players/sn0wix_.png"), true));
        EntityRendererRegistry.register(ModEntities.BUBBAGUMP7, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/players/bubbagump7.png"), false));
        EntityRendererRegistry.register(ModEntities.BOUQUETZ, ctx -> new PlayerNPCRenderer(ctx, new Identifier(WorldOfDragons.MOD_ID, "textures/entity/players/bouquetz.png"), false));


        EntityRendererRegistry.register(ModEntities.IRON_CHEST_ENTITY, ctx -> new GeoEntityRenderer<>(ctx, new ModChestEntityModel("geo/iron_chest.geo.json", "textures/entity/iron_chest/iron_chest.png",
                "animations/iron_chest.animation.json")));

        EntityModelLayerRegistry.registerModelLayer(ExplodingCubeProjectileModel.LAYER_LOCATION, ExplodingCubeProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.EXPLODING_MAGMA_PROJECTILE, ctx -> new ExplodingCubeProjectileRenderer(ctx, new Identifier("minecraft", "textures/block/magma.png")));
        ModPackets.registerS2CPackets();
    }
}
