package net.sn0wix_.worldofdragonsmod.common.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.sn0wix_.worldofdragonsmod.common.item.custom.DragonEggItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {
    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Inject(method = "positionLeftArm", at = @At("RETURN"))
    private void injectPositionLeftArm(T entity, CallbackInfo ci) {
        if (entity.getMainHandStack().getItem() instanceof DragonEggItem<?>) {
            this.leftArm.yaw = (float) Math.toRadians(2.4114);
            this.leftArm.pitch = (float) Math.toRadians(-51.2829);
            this.leftArm.roll = (float) Math.toRadians(11.3003);
        }
    }

    @Inject(method = "positionRightArm", at = @At("RETURN"))
    private void injectPositionRightArm(T entity, CallbackInfo ci) {
        if (entity.getMainHandStack().getItem() instanceof DragonEggItem<?>) {
            this.rightArm.yaw = (float) Math.toRadians(2.4114);
            this.rightArm.pitch = (float) Math.toRadians(-51.2829);
            this.rightArm.roll = (float) Math.toRadians(-11.3003);
        }
    }
}
