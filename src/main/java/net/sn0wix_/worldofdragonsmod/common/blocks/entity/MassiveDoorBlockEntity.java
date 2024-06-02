package net.sn0wix_.worldofdragonsmod.common.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.common.blocks.custom.MassiveDoorBlock;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class MassiveDoorBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final RawAnimation OPEN_ANIMATION = RawAnimation.begin().then("open", Animation.LoopType.HOLD_ON_LAST_FRAME);


    public MassiveDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MASSIVE_DOOR, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> {
            if (getWorld().getBlockState(getPos()).isOf(ModBlocks.MASSIVE_DOOR) && getWorld().getBlockState(getPos()).get(MassiveDoorBlock.OPEN)) {
                state.setAndContinue(OPEN_ANIMATION);
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
