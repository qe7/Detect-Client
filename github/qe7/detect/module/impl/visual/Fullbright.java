package github.qe7.detect.module.impl.visual;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", 0, Category.VISUAL);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 1));
        }
    }

    public void onDisable() {
        mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
    }

}
