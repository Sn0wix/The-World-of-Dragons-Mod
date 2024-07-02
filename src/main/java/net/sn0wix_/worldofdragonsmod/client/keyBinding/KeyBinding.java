package net.sn0wix_.worldofdragonsmod.client.keyBinding;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.PlayerControllable;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyBinding {
    public static final HashMap<Integer, Boolean> PRESSED_KEYS = new HashMap<>();

    public static void tick(MinecraftClient client) {
        if (client.world != null && client.player != null && client.player.getVehicle() instanceof PlayerControllable playerControllable) {
            ArrayList<Integer> keys = new ArrayList<>();
            playerControllable.getKeyBindsList().forEach(serverKeyBind -> keys.add(serverKeyBind.getKey()));


            for (net.minecraft.client.option.KeyBinding key : client.options.allKeys) {
                int keyCode = key.getDefaultKey().getCode();

                if (keys.contains(keyCode)) {
                    if (key.isPressed() || key.wasPressed()) {
                        if (PRESSED_KEYS.containsKey(keyCode)) {
                            if (key.isPressed() != PRESSED_KEYS.get(keyCode)) {
                                PRESSED_KEYS.put(keyCode, !PRESSED_KEYS.get(keyCode));
                                sendKeyPacket(keyCode);
                            }
                        } else {
                            PRESSED_KEYS.put(keyCode, true);
                            sendKeyPacket(keyCode);
                        }
                    }
                }
            }
        } else if (client.world != null && client.player != null && !client.player.isRiding() && !PRESSED_KEYS.isEmpty()) {
            PRESSED_KEYS.clear();
        }
    }

    private static void sendKeyPacket(int keyCode) {
        ClientPlayNetworking.send(ModPackets.KEY, PacketByteBufs.create().writeInt(keyCode));
    }
}
