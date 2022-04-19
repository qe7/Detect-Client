package github.qe7.detect.module;

import github.qe7.detect.event.Event;
import github.qe7.detect.setting.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Setting> settings = new ArrayList<>();
    private String name, suffix = "";
    private Category category;
    private boolean toggled;
    private int key;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() { /* This is a stub */ }
    public void onDisable() { /* This is a stub */ }
    public void onEvent(Event e) { /* This is a stub */ }
    public void onSkipEvent(Event e) { /* This is a stub */ }
}
