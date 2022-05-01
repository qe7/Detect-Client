package github.qe7.detect.ui.dropdown.impl.setting;

import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.ui.dropdown.impl.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class SettingBoolean_ extends SettingComponent {

    private boolean dragging = false;
    private double x;
    private double y;
    private static int height = 12;
    private boolean hovered;
    private SettingBoolean set;

    public SettingBoolean_(SettingBoolean s, Button b) {
        super(s, b, height);
        this.set = s;
    }

    public int drawScreen(int mouseX, int mouseY, double x, double y) {
        this.hovered = this.isHovered(mouseX, mouseY);
        this.x = x;
        this.y = y;
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, new Color(33, 33, 33, 255).getRGB());
        Gui.drawRect(this.x, this.y, this.x + 1, this.y + this.height, new Color(255, 102, 102, 255).getRGB());
        String name = this.set.getName();
        font.drawString(this.set.getValue() ? this.set.getName() + " : True" : this.set.getName() + " : False", this.x + 2, y + 1, -1);

        return this.height;
    }

    public void mouseClicked(int x, int y, int button) {
        if (button == 0 && this.hovered) {
            this.set.setValue(!this.set.getValue());
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY > y && mouseY < y + height;
    }
}
