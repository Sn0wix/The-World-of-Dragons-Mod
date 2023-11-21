package net.sn0wix_.worldofdragonsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.entity.client.hostile.lavaElemental.LavaElementalModel;
import net.sn0wix_.worldofdragonsmod.entity.client.misc.explodingCubeProjectile.ExplodingCubeProjectileModel;
import net.sn0wix_.worldofdragonsmod.entity.client.misc.explodingCubeProjectile.ExplodingCubeProjectileRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.misc.ironChest.IronChestModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.archerOrc.ArcherOrcModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.armoredOrc.ArmoredOrcModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.flyerOrc.FlyerOrcModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.goblin.GoblinModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBoss.OrcBossModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBrute.OrcBruteModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcMage.OrcMageModel;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.slasherOrc.SlasherOrcModel;
import net.sn0wix_.worldofdragonsmod.networking.ModPackets;
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


        EntityRendererRegistry.register(ModEntities.IRON_CHEST_ENTITY, ctx -> new GeoEntityRenderer<>(ctx, new IronChestModel()));

        EntityModelLayerRegistry.registerModelLayer(ExplodingCubeProjectileModel.LAYER_LOCATION, ExplodingCubeProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.EXPLODING_MAGMA_PROJECTILE, ctx -> new ExplodingCubeProjectileRenderer(ctx, new Identifier("minecraft", "textures/block/magma.png")));
        ModPackets.registerS2CPackets();
    }
}
