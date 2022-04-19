package github.qe7.detect.ui.clickgui.impl.setting;

import github.qe7.detect.font.CFontRenderer;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.ui.clickgui.impl.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.text.DecimalFormat;

public class SettingNumber_ extends SettingComponent {

    private boolean dragging = false;
    private double x;
    private double y;
    private static int height = 15;
    private boolean hovered;
    private SettingNumber set;

    public SettingNumber_(SettingNumber s, Button parent) {
        super(s, parent, height);
        this.set = s;
    }

    public int drawScreen(int mouseX, int mouseY, double x, double y) {
        this.hovered = this.isHovered(mouseX, mouseY);
        this.x = x;
        this.y = y;
        CFontRenderer font = Minecraft.getMinecraft().tenacity;

        if (this.dragging) {
            float toSet = (float) ((float) mouseX - (float) (this.x - 2)) / (float) (this.parent.getWidth() - 1);
            if (toSet > 1) {
                toSet = 1;
            }
            if (toSet < 0) {
                toSet = 0;
            }
            this.set.setValue(((this.set.getMax() - this.set.getMin()) * toSet) + this.set.getMin());
            this.set.setValue(Double.valueOf(new DecimalFormat(this.set.getIncrement()).format(this.set.getValue())));
        }

        float distance = (float) ((this.set.getValue() - this.set.getMin()) / (this.set.getMax() - this.set.getMin()));
        Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, new Color(33, 33, 33, 255).getRGB());
        Gui.drawRect(this.x, this.y, this.x + 1, this.y + this.height, new Color(108, 61, 199, 255).getRGB());
        Gui.drawRect(this.x + 1, this.y + font.getHeight() - 9, (int) (this.x - 1+ (this.parent.getWidth() * distance)), this.y + this.height - 0, new Color(47, 47, 47, 255).getRGB());
        String name = this.set.getName();
        font.drawString(this.set.getName() + " : " + this.set.getValue(), this.x + 2, y + 1,-1);

        return this.height;
    }

    public void mouseClicked(int x, int y, int button) {
        if (button == 0 && this.hovered) {
            this.dragging = !this.dragging;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            this.dragging = false;
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY > y && mouseY < y + height;
    }

}
