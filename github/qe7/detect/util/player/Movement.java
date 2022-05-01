package github.qe7.detect.util.player;

import github.qe7.detect.util.Util;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Movement {

    public static boolean isMovingForward() {
        return Util.mc.thePlayer.moveForward != 0f;
    }

    public static boolean isMovingStrafing() {
        return Util.mc.thePlayer.moveStrafing != 0f;
    }

    public static boolean isMoving() {
        if (Minecraft.getMinecraft().currentScreen != null) {
            return false;
        } else {
            return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D);
        }
    }

}
