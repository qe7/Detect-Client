package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

public class Autoclicker extends Module {

    public Timer timer = new Timer();
    public SettingNumber cps = new SettingNumber("Cps", 10, "#.#", 8, 20);
    public SettingNumber random = new SettingNumber("Randomization", 10, "#.", 4, 20);

    public Autoclicker() {
        super("Autoclicker", 0, Category.COMBAT);
        addSettings(cps, random);
    }

    public void onEvent(Event event) {
        setSuffix(cps.getValue().toString());
        if (event instanceof EventUpdate) {
            if (Mouse.isButtonDown(0) && mc.currentScreen == null && !mc.thePlayer.isBlocking()) {
                if (timer.hasReached(1000 / (cps.getValue() + (Math.random() / random.getValue())))) {
                    mc.clickMouse();
                    timer.reset();
                }
            }
        }
    }

}
