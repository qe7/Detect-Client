package github.qe7.detect.module.impl.visual;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;

public class Cape extends Module {

    public SettingMode mode;
    public static String WhichCape = "Detect";

    public Cape() {
        super("Cape", 0, Category.VISUAL);
        mode = new SettingMode("Mode", "Detect", "Mouseware");
        addSettings(mode);
    }

    public void onEvent(Event e) {
        setSuffix(mode.getCurrentValue());
        switch (mode.getCurrentValue()) {
            case "Detect":
                WhichCape = "detect";
                break;
            case "Mouseware":
                WhichCape = "mouseware";
                break;

        }
    }
    public static String getCape() {
        return WhichCape;
    }
}
