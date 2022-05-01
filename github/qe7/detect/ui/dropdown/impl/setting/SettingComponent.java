package github.qe7.detect.ui.dropdown.impl.setting;

import github.qe7.detect.setting.Setting;
import github.qe7.detect.ui.dropdown.impl.Button;

public class SettingComponent {

    protected Button parent;
    public Setting setting;
    private double height;

    public SettingComponent(Setting s, Button parent, double height) {
        this.parent = parent;
        this.setting = s;
        this.height = height;
    }
    public int drawScreen(int mouseX, int mouseY, double x, double y) {
        return 0;
    }

    public void mouseClicked(int x, int y, int button) {}

    public void mouseReleased(int mouseX, int mouseY, int state) {}

    public void keyTyped(char typedChar, int keyCode) {}

    public Setting getSetting() {
        return this.setting;
    }

    public double getHeight() {
        return height;
    }

}
