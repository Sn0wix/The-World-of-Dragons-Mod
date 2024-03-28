package net.sn0wix_.worldofdragonsmod.common.entity.ai;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

//Code is ported from forge version of Mowzie's mobs: https://github.com/BobMowzie/MowziesMobs
public class MMEntityMoveHelper extends MoveControl {
    private float maxRotate = 90;

    public MMEntityMoveHelper(MobEntity entitylivingIn, float maxRotate) {
        super(entitylivingIn);
        this.maxRotate = maxRotate;
    }

    public void tick()
    {
        if (this.state == State.STRAFE)
        {
            float f = (float)this.entity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            float f1 = (float)this.speed * f;
            float f2 = this.forwardMovement;
            float f3 = this.sidewaysMovement;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (f4 < 1.0F)
            {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.entity.getYaw() * 0.017453292F);
            float f6 = MathHelper.cos(this.entity.getYaw() * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            EntityNavigation pathnavigate = this.entity.getNavigation();

            if (pathnavigate != null)
            {
                PathNodeMaker nodeprocessor = pathnavigate.getNodeMaker();

                if (nodeprocessor != null && nodeprocessor.getDefaultNodeType(this.entity.getWorld(), MathHelper.floor(this.entity.getX() + (double)f7), MathHelper.floor(this.entity.getY()), MathHelper.floor(this.entity.getZ() + (double)f8)) != PathNodeType.WALKABLE)
                {
                    this.forwardMovement = 1.0F;
                    this.sidewaysMovement = 0.0F;
                    f1 = f;
                }
            }

            this.entity.setMovementSpeed(f1);
            this.entity.setForwardSpeed(this.forwardMovement);
            this.entity.setSidewaysSpeed(this.sidewaysMovement);
            this.state = State.WAIT;
        }
        else if (this.state == State.MOVE_TO)
        {
            this.state = State.WAIT;
            double d0 = this.targetX - this.entity.getX();
            double d1 = this.targetZ - this.entity.getZ();
            double d2 = this.targetY - this.entity.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 < 2.500000277905201E-7D)
            {
                this.entity.setForwardSpeed(0.0F);
                return;
            }

            float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), f9, maxRotate));
            this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));

            if (d2 > (double)this.entity.getStepHeight() && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.entity.getWidth()))
            {
                this.entity.getJumpControl().setActive();
                this.state = State.JUMPING;
            }
        }
        else if (this.state == State.JUMPING)
        {
            this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));

            if (this.entity.isOnGround())
            {
                this.state = State.WAIT;
            }
        }
        else
        {
            this.entity.setForwardSpeed(0.0F);
        }
    }
}
