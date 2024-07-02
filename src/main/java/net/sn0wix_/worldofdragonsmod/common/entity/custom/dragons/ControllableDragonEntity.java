package net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.PlayerControllable;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.ServerKeyBind;

import java.util.ArrayList;

public abstract class ControllableDragonEntity extends TameableEntity implements PlayerControllable {
    private final ArrayList<ServerKeyBind> keyBinds = new ArrayList<>();
    private ServerKeyBind forward;
    private ServerKeyBind backward;
    private ServerKeyBind right;
    private ServerKeyBind left;


    protected ControllableDragonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.initKeyBinds();
    }

    @Override
    public ArrayList<ServerKeyBind> getKeyBindsList() {
        return keyBinds;
    }

    @Override
    public void initKeyBinds() {
        forward = registerKey(87);//w
        backward = registerKey(83);//s
        right = registerKey(68);//d
        left = registerKey(65);//a
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        setAllKeysPressed(false);
    }
}