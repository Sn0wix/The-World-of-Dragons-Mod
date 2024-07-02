package net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons;

import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;

public interface ShellSmasherAnimations extends DragonAnimations {
    RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);

    RawAnimation FLY = RawAnimation.begin().then("move.fly", Animation.LoopType.PLAY_ONCE);
    RawAnimation GLIDE = RawAnimation.begin().then("move.glide", Animation.LoopType.PLAY_ONCE);

    RawAnimation SLEEP = RawAnimation.begin().then("sleep.idle", Animation.LoopType.LOOP);
    RawAnimation ENTER_SLEEP = RawAnimation.begin().then("sleep.enter", Animation.LoopType.PLAY_ONCE);
    RawAnimation WAKE_UP = RawAnimation.begin().then("sleep.wake_up", Animation.LoopType.PLAY_ONCE);
}
