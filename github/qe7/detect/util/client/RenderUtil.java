package github.qe7.detect.util.client;

import net.minecraft.client.gui.Gui;

public class RenderUtil {

    public void drawOutlinedRect(double x, double y, double x2, double y2, double scale, int rectColor, int outlineColor) {
        Gui.drawRect(x, y, x2, y2, rectColor);

        Gui.drawRect(x, y + scale, x + scale, y2, outlineColor);
        Gui.drawRect(x, y, x2, y + scale, outlineColor);
        Gui.drawRect(x2, y, x2 + scale, y2, outlineColor);
        Gui.drawRect(x, y2, x2 + scale, y2 + scale, outlineColor);
    }

}
