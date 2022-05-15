package github.qe7.detect.module.impl.visual;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;

public class Cape extends Module {

    public SettingMode mode;
    public static String whichCape = "detect";
    public static String overLay = "detect";

    public Cape() {
        super("Cape", 0, Category.VISUAL);
        mode = new SettingMode("Mode", "Detect", "Exhibition", "Mouseware");
        addSettings(mode);
    }

    public void onEvent(Event e) {
        setSuffix(mode.getCurrentValue());
        switch (mode.getCurrentValue()) {
            case "Detect":
                whichCape = "detect";
                break;
            case "Exhibition":
                whichCape = "exhicape";
                overLay = "exhioverlay";
                break;
            case "Mouseware":
                whichCape = "mouseware";
                break;

        }
    }

    public static String getCape() {
        return whichCape;
    }

    public static String getOverlay() {
        return overLay;
    }
}
