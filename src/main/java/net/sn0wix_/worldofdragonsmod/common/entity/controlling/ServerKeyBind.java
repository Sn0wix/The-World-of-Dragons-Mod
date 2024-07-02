package net.sn0wix_.worldofdragonsmod.common.entity.controlling;

public class ServerKeyBind {
    private boolean isPressed;
    private final int key;

    public ServerKeyBind(boolean isPressed, int key) {
        this.isPressed = isPressed;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
