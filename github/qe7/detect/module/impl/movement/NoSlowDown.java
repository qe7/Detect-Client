package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventSlowDown;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import org.lwjgl.input.Keyboard;

public class NoSlowDown extends Module {

    public NoSlowDown() {
        super("NoSlowDown", Keyboard.KEY_O, Category.MOVEMENT);
    }

    public void onEvent(Event e) {
        if (e instanceof EventSlowDown) {
            e.setCancelled(true);
        }
    }

}
