package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;

public class Phase extends Module {

    public SettingNumber amount = new SettingNumber("amount", -3, "#.#", -10, 10);
    public SettingMode mode;

    public Phase() {
        super("Phase", 0, Category.PLAYER);
        mode = new SettingMode("Mode", "vclip");
        addSettings(mode, amount);
    }

    public void onEvent(Event e) {
        if (amount.getValue() > 0) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + amount.getValue(), mc.thePlayer.posZ);
            this.toggle();
        } else if (mc.gameSettings.keyBindSneak.isKeyDown() && amount.getValue() < 0) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + amount.getValue(), mc.thePlayer.posZ);
            this.toggle();
        }

    }

}
