package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;

public class FastPlace extends Module {

    public SettingNumber speed;

    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER);
        speed = new SettingNumber("Speed", 1, "#.", 0, 4);
        addSettings(speed);
    }

    public void onEvent(Event event) {
        setSuffix(speed.getValue().toString());
        if (event instanceof EventUpdate) {
            mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, 0);
        }
    }

}
