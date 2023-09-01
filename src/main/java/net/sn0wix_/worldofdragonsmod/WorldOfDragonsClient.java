package net.sn0wix_.worldofdragonsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sn0wix_.worldofdragonsmod.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.archerOrc.ArcherOrcRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.flyerOrc.FlyerOrcRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.goblin.GoblinRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.armoredOrc.ArmoredOrcRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.misc.ironChest.IronChestRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBoss.OrcBossRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcBrute.OrcBruteRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.orcMage.OrcMageRenderer;
import net.sn0wix_.worldofdragonsmod.entity.client.orcs.slasherOrc.SlasherOrcRenderer;

public class WorldOfDragonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.ARMORED_ORC, ArmoredOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.GOBLIN, GoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARCHER_ORC, ArcherOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLYER_ORC, FlyerOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.ORC_BOSS, OrcBossRenderer::new);
        EntityRendererRegistry.register(ModEntities.ORC_BRUTE, OrcBruteRenderer::new);
        EntityRendererRegistry.register(ModEntities.SLASHER_ORC, SlasherOrcRenderer::new);
        EntityRendererRegistry.register(ModEntities.ORC_MAGE, OrcMageRenderer::new);
        //EntityRendererRegistry.register(ModEntities.ORC_WARG, OrcWargRenderer::new);

        EntityRendererRegistry.register(ModEntities.IRON_CHEST_ENTITY, IronChestRenderer::new);
    }
}
