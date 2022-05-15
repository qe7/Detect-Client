package github.qe7.detect.ui.dropdown.impl.setting;

import github.qe7.detect.module.impl.visual.Hud;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.ui.dropdown.impl.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static github.qe7.detect.module.Module.mc;

public class SettingNumber_ extends SettingComponent {

    private boolean dragging = false;
    private double x;
    private double y;
    private static int height = 12;
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
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        if (this.dragging) {
            float toSet = (float) ((float) mouseX - (float) (this.x - 2)) / (float) (this.parent.getWidth() - 1);
            if (toSet > 1) {
                toSet = 1;
            }
            if (toSet < 0) {
                toSet = 0;
            }
            this.set.setValue(((this.set.getMax() - this.set.getMin()) * toSet) + this.set.getMin());

            DecimalFormat decimalFormat = new DecimalFormat(this.set.getIncrement());
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH)); //makes it format with dot instead of comma

            this.set.setValue(Double.valueOf(decimalFormat.format(this.set.getValue())));
        }

        float distance = (float) ((this.set.getValue() - this.set.getMin()) / (this.set.getMax() - this.set.getMin()));
        Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, new Color(33, 33, 33, 255).getRGB());
        Gui.drawRect(this.x, this.y, this.x + 1, this.y + this.height, Hud.getColor().getRGB());
        Gui.drawRect(this.x + 1, this.y + font.FONT_HEIGHT - 9, (int) (this.x - 1+ (this.parent.getWidth() * distance)), this.y + this.height - 0, 0x77ffffff);
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
