package net.sn0wix_.worldofdragonsmod.common.entity.controlling;

import net.minecraft.network.PacketByteBuf;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public interface PlayerControllable {
    default void handlePacket(PacketByteBuf buf) {
        int key = buf.readInt();
        AtomicBoolean foundKey = new AtomicBoolean(false);

        getKeyBindsList().forEach(serverKeyBind -> {
            if (serverKeyBind.getKey() == key) {
                serverKeyBind.setPressed(!serverKeyBind.isPressed());
                foundKey.set(true);
            }
        });

        if (!foundKey.get()) {
            WorldOfDragons.LOGGER.warn("Could not find key " + key + " in " + this);
        }
    }


    default ServerKeyBind registerKey(int code) {
        ServerKeyBind keyBind = new ServerKeyBind(false, code);
        getKeyBindsList().add(keyBind);
        return keyBind;
    }

    default void setAllKeysPressed(boolean pressed) {
        getKeyBindsList().forEach(serverKeyBind -> serverKeyBind.setPressed(pressed));
    }


    ArrayList<ServerKeyBind> getKeyBindsList();

    void initKeyBinds();
}
