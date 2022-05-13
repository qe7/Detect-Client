package github.qe7.detect.module.impl.visual;

import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingNumber;

public class ChatExtras extends Module {

    public static SettingBoolean height;
    public static SettingBoolean transparencybool;
    public static SettingNumber transparency;

    public ChatExtras() {
        super("ChatExtras", 0, Category.VISUAL);
        height = new SettingBoolean("Height fix", false);
        transparencybool = new SettingBoolean("Transparency", false);
        transparency = new SettingNumber("Alpha", 0, "#.", 0, 255);
        addSettings(height);
    }



}
