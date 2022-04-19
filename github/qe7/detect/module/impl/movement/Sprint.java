package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Keyboard.KEY_G, Category.MOVEMENT);
        setToggled(true);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            mc.thePlayer.setSprinting(
                    mc.thePlayer.moveForward > 0 &&
                    //!mc.thePlayer.isBlocking() &&
                    !mc.thePlayer.isSneaking() &&
                    !mc.thePlayer.isCollidedHorizontally &&
                    !mc.thePlayer.isPotionActive(Potion.blindness) &&
                    mc.thePlayer.getFoodStats().getFoodLevel() > 6.f
            );
        }
    }

}
