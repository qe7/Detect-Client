package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;

public class AutoRespawn extends Module {

    public AutoRespawn() {
        super("AutoRespawn", 0, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (AutoRespawn.mc.thePlayer.isDead) {
            AutoRespawn.mc.thePlayer.respawnPlayer();
        }
    }

}
