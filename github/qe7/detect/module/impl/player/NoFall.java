package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", 0, Category.PLAYER);
	}

	public void onEvent(Event e) {
		if (e instanceof EventMotion) {
			EventMotion motion = (EventMotion) e;
			if (mc.thePlayer != null && mc.thePlayer.fallDistance > 2.5) {
				mc.thePlayer.fallDistance = 0;
				//motion.setOnGround(true);
			}
		}
	}
	
}
