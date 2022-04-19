package github.qe7.detect.module.impl.client;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventRender2D;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Arraylist extends Module {

    public Arraylist() {
        super("Arraylist", 0, Category.CLIENT);
        setToggled(true);
    }

    public static class sortDefaultFont implements Comparator<Module> {
        public int compare(Module arg0, Module arg1) {
            if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : ""))) {
                return -1;
            } else if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : "")))
                return 1;
            return 0;
        }
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {
            CopyOnWriteArrayList<Module> modules = Detect.i.moduleManager.getModules();
            FontRenderer font = mc.fontRendererObj;
            Collections.sort(modules, new sortDefaultFont());

            int count = 0;
            for (Module m : modules) {
                if (m.isToggled()) {
                    String text = m.getName() + (m.getSuffix().length() > 0? " \2477" + m.getSuffix() + "" : "");
                    EventRender2D e = (EventRender2D) event;

                    double x = ((EventRender2D) e).getWidth() - font.getStringWidth(text) - 8;
                    double y = 4 + ((font.FONT_HEIGHT + 1) * count);
                    double endX = ((EventRender2D) e).getWidth() - 6;
                    double endY = ((font.FONT_HEIGHT + 1) * count) + font.FONT_HEIGHT + 5;

                    Gui.drawRect(endX + 1, y, x, endY, 0x40000000);
                    font.drawStringWithShadow(text, x + 2, y + 1, -1);
                    count++;
                }
            }
        }
    }

}
