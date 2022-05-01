package github.qe7.detect.ui.dropdown.impl;

import github.qe7.detect.Detect;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.Setting;
import github.qe7.detect.ui.dropdown.NewClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Panel {

    public NewClickGui clickgui;
    public Category category;
    public Setting setting;
    public ArrayList<Module> modules = new ArrayList<>();
    public ArrayList<Button> buttons = new ArrayList<>();
    private Panel lastGUI;

    public double x;
    public double y = 4;

    public double width = 100;
    public double height = 12;
    private double offsetX = 0;
    private double offsetY = 0;

    public boolean dragging;
    public boolean open = true;

    public Panel (Panel lastGUI, int x, Category category, NewClickGui clickgui) {
        this.x = x;
        this.lastGUI = lastGUI;
        this.clickgui = clickgui;
        this.category = category;
        int count = 0;
        for (Module m : Detect.i.moduleManager.getModules()) {
            if (m.getCategory() == category) {
                buttons.add(new Button(height + count * 12, m, this));
                count++;
            }
        }
   }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer font = mc.fontRendererObj;

        int count = 0;
        double lButHeight = 0;
        double tButHeight = 0;

        if(dragging) {
            x = mouseX - offsetX;
            y = mouseY - offsetY;
        }

        if (open) {
            for(Button b : buttons) {
                if(count > 0) {
                    lButHeight += buttons.get(count - 1).setHeight;
                    tButHeight += buttons.get(count).height;
                }
                tButHeight += b.drawScreen(mouseX, mouseY, partialTicks, lButHeight);
                count++;
            }
        }


        Gui.drawRect(x, y, x + width, y + height, new Color(47, 47, 47, 255).darker().getRGB());
        font.drawStringWithShadow(category.name, x + 4, y + ((height - mc.fontRendererObj.FONT_HEIGHT) / 2), -1);
    }

    public void keyTyped(char typedChat, int keyCode) {
        if(open)
            for(Button b : buttons) {
                b.keyTyped(typedChat, keyCode);
            }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(isHovered(mouseX,mouseY)) {
            switch(mouseButton) {
                case 0:
                    dragging = true;
                    offsetX = mouseX - x;
                    offsetY = mouseY - y;
                    break;
                case 1:
                    open = !open;
                    break;
            }
        }
        if(open)
            for(Button b : buttons) {
                b.mouseClicked(mouseX, mouseY, mouseButton);
            }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(open)
            for(Button b : buttons) {
                b.mouseReleased(mouseX, mouseY, state);
            }
        dragging = false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x + width)
                && (mouseY > y && mouseY < y + height);
    }

}
