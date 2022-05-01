package github.qe7.detect.module.impl.visual;

import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.ui.dropdown.NewClickGui;
import org.lwjgl.input.Keyboard;

public class Clickgui extends Module {

    public NewClickGui clickGUI;

    public Clickgui() {
        super("Clickgui", Keyboard.KEY_RSHIFT, Category.VISUAL);
    }

    public void onEnable() {
        if(clickGUI == null)
            clickGUI = new NewClickGui();
        mc.displayGuiScreen(clickGUI);
    }

}
