package github.qe7.detect.module.impl.client;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventRender2D;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Watermark extends Module {

    public Watermark() {
        super("Watermark", 0, Category.CLIENT);
        setToggled(true);
    }

    public void onEvent(Event e) {
        if (e instanceof EventRender2D) {
            FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

            font.drawStringWithShadow(Detect.i.name, 4, 4, -1);
        }
    }
}
