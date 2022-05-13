package github.qe7.detect.module;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.setting.Setting;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.util.chat.AddChatMessage;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;

public class Module {

    public static Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String suffix = "";
    public Category category;
    public boolean toggled;
    public int bind;

    public SettingBoolean drawn = new SettingBoolean("Drawn", true);

    private ArrayList<Setting> settings = new ArrayList<>();

    public Module(String name, int bind, Category category) {
        this.name = name;
        this.bind = bind;
        this.category = category;
        addSettings(drawn);
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

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
            if (Detect.i.moduleManager.getModuleByName("ToggleMessages").isToggled())
                AddChatMessage.addChatMessage("toggled " + getName() + " on.");
        } else {
            onDisable();
            if (Detect.i.moduleManager.getModuleByName("ToggleMessages").isToggled())
                AddChatMessage.addChatMessage("toggled " + getName() + " off.");
        }
    }

    public void onEnable() {

    }
    public void onDisable() {

    }
    public void onEvent(Event e) {

    }
    public void onSkipEvent(Event e) {

    }

}
