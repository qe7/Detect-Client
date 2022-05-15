package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.util.player.Movement;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT);
        setToggled(true);
    }

    public void onEvent(Event event) {
        setSuffix("Legit");
        if (event instanceof EventUpdate) {
            mc.thePlayer.setSprinting(
                    Movement.isMovingForward() &&
                    !mc.thePlayer.isSneaking() &&
                    !mc.thePlayer.isCollidedHorizontally &&
                    !mc.thePlayer.isDead &&
                    mc.thePlayer.getFoodStats().getFoodLevel() > 6f
            );
        }
    }

    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }

}
