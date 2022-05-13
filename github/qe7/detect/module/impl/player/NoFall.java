package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;

public class NoFall extends Module {

    public SettingMode mode;

    public NoFall() {
        super("NoFall", 0, Category.PLAYER);
        mode = new SettingMode("Mode", "Vanilla");
        addSettings(mode);
    }

    public void onEvent(Event event) {

        setSuffix(mode.getCurrentValue());

        if (event instanceof EventMotion) {
            EventMotion em = (EventMotion) event;
            if (mc.thePlayer.fallDistance >= 2.5f) {
                em.setOnGround(true);
            }
        }
    }
}
