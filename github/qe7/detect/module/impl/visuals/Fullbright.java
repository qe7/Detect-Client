package github.qe7.detect.module.impl.visuals;

import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", Keyboard.KEY_I, Category.VISUAL);
    }

    public void onEnable() {
        mc.gameSettings.gammaSetting = 100.f;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 0.5f;
    }
}
