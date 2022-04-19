package github.qe7.detect.ui.clickgui;

import github.qe7.detect.Detect;
import github.qe7.detect.ui.clickgui.impl.Panel;
import github.qe7.detect.module.Category;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {

    public ArrayList<github.qe7.detect.ui.clickgui.impl.Panel> panels = new ArrayList<>();

    public ClickGui() {
        Detect.i.moduleManager.sortModules();
        int count = 0;
        github.qe7.detect.ui.clickgui.impl.Panel lastPanel = null;
        for(Category category : Category.values()) {
            github.qe7.detect.ui.clickgui.impl.Panel panel = new github.qe7.detect.ui.clickgui.impl.Panel(lastPanel, 4 + (count * 124), category, this);
            panels.add(panel);
            lastPanel = panel;
            count++;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(0, 0, this.width, this.height, new Color(18, 18, 20, 150).getRGB());
        for (github.qe7.detect.ui.clickgui.impl.Panel panel : panels) {
            panel.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    protected void keyTyped(char typedChat, int keyCode) {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        } else {
            for(github.qe7.detect.ui.clickgui.impl.Panel p : panels) {
                p.keyTyped(typedChat, keyCode);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(github.qe7.detect.ui.clickgui.impl.Panel p : panels) {
            p.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Panel p : panels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void onGuiClosed() {
        Detect.i.moduleManager.getModuleByName("Clickgui").toggle();
    }

}
