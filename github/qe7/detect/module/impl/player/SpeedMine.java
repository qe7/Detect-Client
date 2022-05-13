package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SpeedMine extends Module {

    public SettingMode mode;

    public SpeedMine() {
        super("SpeedMine", 0, Category.PLAYER);
        mode = new SettingMode("Mode", "Vanilla");
        addSettings(mode);
    }

    public void onEvent(Event event) {

        setSuffix(mode.getCurrentValue());

        if (event instanceof EventUpdate) {
            SpeedMine.mc.playerController.blockHitDelay = 0;
            final boolean item = SpeedMine.mc.thePlayer.getCurrentEquippedItem() == null;
            SpeedMine.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, (int) (item ? 1 : 0)));
        }
    }

    public void onDisable() {
        super.onDisable();
        SpeedMine.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }

}
