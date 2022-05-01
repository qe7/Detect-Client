package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import org.lwjgl.input.Mouse;

public class Autoclicker extends Module {

    public Timer timer = new Timer();
    public SettingNumber cps;

    public Autoclicker() {
        super("Autoclicker", 0, Category.COMBAT);
        cps = new SettingNumber("", 10, "#.#", 8, 20);
        addSettings(cps);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (Mouse.isButtonDown(0) && mc.currentScreen == null && !mc.thePlayer.isBlocking()) {
                if (timer.hasReached(1000 / (cps.getValue() + (Math.random() / 10)))) {
                    mc.clickMouse();
                    timer.reset();
                }
            }
        }
    }

}
