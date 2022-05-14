package github.qe7.detect.module.impl.visual;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;

public class Animations extends Module {

    public static SettingMode mode;
    public static SettingNumber scale;
    // x = 0.52F, y = -0.52F, z = -0.72F
    public static SettingNumber x, y, z;

    public Animations() {
        super("Animations", 0, Category.VISUAL);
        mode = new SettingMode("Animation", "Exhi", "Remix", "1.7");
        scale = new SettingNumber("Scale", 0.4, ".#", 0.1, 0.6);
        x = new SettingNumber("X", 0.52, ".##", -1, 1);
        y = new SettingNumber("Y", -0.52, ".##", -1, 1);
        z = new SettingNumber("Z", -0.72, ".##", -1, 1);
        addSettings(mode, scale, x, y, z);
        this.setToggled(true);
    }

    public void onEvent(Event event) {
        setSuffix(mode.getCurrentValue());
    }

}
