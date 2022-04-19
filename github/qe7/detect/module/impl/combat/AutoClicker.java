package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.util.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {

    public TimerUtil timer = new TimerUtil();

    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_P, Category.COMBAT);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            int key = mc.gameSettings.keyBindAttack.getKeyCode();
            if (Mouse.isButtonDown(0)) {
                if (timer.hasReached(1000 / ((12) + (Math.random() * 4)))) {
                    KeyBinding.setKeyBindState(key, true);
                    mc.thePlayer.setSprinting(false);
                    KeyBinding.onTick(key);
                    timer.reset();
                } else {
                    KeyBinding.setKeyBindState(key, false);
                }
            }
        }
    }
}
