package net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.PlayerControllable;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.ServerKeyBind;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public abstract class ControllableDragonEntity extends TameableEntity implements PlayerControllable {
    private final ArrayList<ServerKeyBind> keyBinds = new ArrayList<>();

    private ServerKeyBind forward;
    private ServerKeyBind backward;
    private ServerKeyBind right;
    private ServerKeyBind left;

    public float movementSideways;
    public float movementForward;


    protected ControllableDragonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.initKeyBinds();
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {

    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.move(MovementType.SELF, getMovementInput3D());
    }

    public Vec2f getMovementInput2D() {
        return new Vec2f(this.movementSideways, this.movementForward);
    }

    public Vec3d getMovementInput3D() {
        return new Vec3d(this.movementSideways, 0, this.movementForward);
    }


    @Override
    public ArrayList<ServerKeyBind> getKeyBindsList() {
        return keyBinds;
    }

    @Override
    public void initKeyBinds() {
        forward = registerKey(GLFW.GLFW_KEY_W);
        backward = registerKey(GLFW.GLFW_KEY_S);
        right = registerKey(GLFW.GLFW_KEY_D);
        left = registerKey(GLFW.GLFW_KEY_A);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        setAllKeysPressed(false);
    }

    private static float getMovementMultiplier(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }
}