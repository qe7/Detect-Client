package github.qe7.detect.module.impl.client;

import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.ui.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

public class Clickgui extends Module {

    public ClickGui clickGUI;

    public Clickgui() {
        super("Clickgui", Keyboard.KEY_RSHIFT, Category.CLIENT);
    }

    public void onEnable() {
        if(clickGUI == null)
            clickGUI = new ClickGui();
        mc.displayGuiScreen(clickGUI);
    }

}
