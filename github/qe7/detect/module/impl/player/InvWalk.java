package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.client.gui.GuiChat;

public class InvWalk extends Module {

    public InvWalk() {
        super("InventoryWalk", 0, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.currentScreen instanceof GuiChat) {
                return;
            }
        }
    }

}
