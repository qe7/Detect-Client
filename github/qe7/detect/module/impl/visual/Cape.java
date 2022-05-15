package github.qe7.detect.module.impl.visual;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;

public class Cape extends Module {

    public SettingMode mode;
    public static boolean hasOverlay;
    public static String whichCape = "detect";
    public static String overlay = "detect";

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
                hasOverlay = false;
                break;
            case "Exhibition":
                whichCape = "exhicape";
                overlay = "exhioverlay";
                hasOverlay = true;
                break;
            case "Mouseware":
                whichCape = "mouseware";
                hasOverlay = false;
                break;

        }
    }

    public static String getCape() {
        return whichCape;
    }

    public static String getOverlay() {
        return overlay;
    }
}
