package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

public class InvWalk extends Module {

    public InvWalk() {
        super("InventoryWalk", 0, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.currentScreen instanceof GuiChat) {
                return;
            }
            if (mc.currentScreen != null) {

                if (Keyboard.isKeyDown(200)) {
                    mc.thePlayer.rotationPitch -= 3.0f;
                }
                if (Keyboard.isKeyDown(208)) {
                    mc.thePlayer.rotationPitch += 3.0f;
                }
                if (Keyboard.isKeyDown(203)) {
                    mc.thePlayer.rotationYaw -= 5.0f;
                }
                if (Keyboard.isKeyDown(205)) {
                    mc.thePlayer.rotationYaw += 5.0f;
                }

                mc.gameSettings.keyBindForward.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
                mc.gameSettings.keyBindLeft.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
                mc.gameSettings.keyBindBack.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
                mc.gameSettings.keyBindRight.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
                mc.gameSettings.keyBindJump.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
                mc.gameSettings.keyBindSprint.pressed = org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
            }
        }
    }

}
