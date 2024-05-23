package net.sn0wix_.worldofdragonsmod.common.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.sn0wix_.worldofdragonsmod.common.command.GPTCommand;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.LavaElementalEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.SnapperEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC.PlayerNPCEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs.*;

public class ModRegisteries {
    public static void registerModStuffs() {
        registerAttributes();
        registerCommands();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN, GoblinEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ARMORED_ORC, ArmoredOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ARCHER_ORC, ArcherOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.FLYER_ORC, FlyerOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ORC_BOSS, OrcBossEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ORC_BRUTE, OrcBruteEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SLASHER_ORC, SlasherOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ORC_MAGE, OrcMageEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LAVA_ELEMENTAL, LavaElementalEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SNAPPER, SnapperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SN0WIX_, PlayerNPCEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BUBBAGUMP7, PlayerNPCEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BOUQUETZ, PlayerNPCEntity.setAttributes());
        //FabricDefaultAttributeRegistry.register(ModEntities.ORC_WARG, OrcWargEntity.setAttributes());
    }

    private static DefaultAttributeContainer.Builder createGenericEntityAttributes() {
        return PathAwareEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.80000000298023224D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1);
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(GPTCommand::register);
    }
}
