package github.qe7.detect.ui.dropdown.impl;

import github.qe7.detect.module.Module;
import github.qe7.detect.setting.Setting;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.ui.dropdown.impl.setting.SettingBoolean_;
import github.qe7.detect.ui.dropdown.impl.setting.SettingComponent;
import github.qe7.detect.ui.dropdown.impl.setting.SettingMode_;
import github.qe7.detect.ui.dropdown.impl.setting.SettingNumber_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

public class Button {

    public Panel panel;
    public Module module;
    public Setting setting;

    public ArrayList<SettingComponent> settings = new ArrayList<>();

    public double y;
    public boolean extended = false;
    public boolean binding = false;
    public double height = 12;
    public double setHeight = 0;
    private double settingHeight = 0;

    public Button(double y, Module m, Panel panel) {
        this.y = y;
        this.panel = panel;
        this.module = m;

        for (Setting s : m.getSettings()) {
            if (s instanceof SettingBoolean) {
                this.settings.add(new SettingBoolean_((SettingBoolean) s, this));
            }
            if (s instanceof SettingMode) {
                this.settings.add(new SettingMode_((SettingMode) s, this));
            }
            if (s instanceof SettingNumber) {
                this.settings.add(new SettingNumber_((SettingNumber) s, this));
            }
        }
    }

    public double drawScreen(int mouseX, int mouseY, float partialTicks, double plusplus) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer font = mc.fontRendererObj;

        setHeight = 0;
        settingHeight = plusplus;
        if(extended) {
            int count = 0;
            for(SettingComponent sc : settings) {
                setHeight += sc.drawScreen(mouseX, mouseY, panel.x, panel.y + plusplus + y + height + (setHeight));
                count++;
            }
        }

        Gui.drawRect(panel.x,panel.y + y + plusplus, panel.x + panel.width, panel.y + y + height + plusplus, module.isToggled() ? new Color(255, 102, 102, 255).getRGB() : new Color(40, 40, 40, 255).getRGB());
        font.drawStringWithShadow(module.getName(), panel.x + 4, panel.y + y + ((height - mc.fontRendererObj.FONT_HEIGHT) / 2) + plusplus, -1);
        if (binding || module.getKey() != 0)
            font.drawStringWithShadow(binding ? "binding..." : "[" + Keyboard.getKeyName(module.getKey()) + "]", panel.x + panel.width - font.getStringWidth(binding ? "binding..." : "[" + Keyboard.getKeyName(module.getKey()) + "]") - 4, panel.y + y + ((height - mc.fontRendererObj.FONT_HEIGHT) / 2) + plusplus, -1);
        return setHeight;
    }

    public void keyTyped(char typedChat, int keyCode) {
        if (binding) {
            if (keyCode == Keyboard.KEY_BACK) {
                module.setKey(Keyboard.KEY_NONE);
            } else {
                module.setKey(keyCode);
            }
            binding = false;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(isHovered(mouseX,mouseY)) {
            switch(mouseButton) {
                case 0:
                    if (module.getName() != "ClickGui")
                        module.toggle();
                    break;
                case 1:
                    extended = !extended;
                    break;
                case 2:
                    binding = true;
                    break;
            }
        }
        for(SettingComponent sc : settings) {
            sc.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        for(SettingComponent sc : settings) {
            sc.mouseReleased(mouseX, mouseY, state);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (mouseX > panel.x && mouseX < panel.x + panel.width)
                && (mouseY > panel.y + y + settingHeight && mouseY < panel.y + y + height + settingHeight);
    }

    public double getWidth() {
        return panel.width;
    }
}
